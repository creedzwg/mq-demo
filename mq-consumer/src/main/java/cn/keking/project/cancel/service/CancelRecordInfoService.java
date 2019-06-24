package cn.keking.project.cancel.service;

import cn.keking.project.cancel.base.enums.CancelOperationTypeEnum;
import cn.keking.project.cancel.base.util.CommonResponseUtil;
import cn.keking.project.cancel.entity.CancelRecordInfo;
import cn.keking.project.cancel.repository.CancelRecordInfoRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author: zhangwengang
 * @Date: 2019/6/20
 * @Version: V1.0
 * @Description:
 */
@Service
@Slf4j
public class CancelRecordInfoService {

    private final CancelRecordInfoRepository cancelDataInfoRepository;

    public CancelRecordInfoService(CancelRecordInfoRepository cancelDataInfoRepository) {
        this.cancelDataInfoRepository = cancelDataInfoRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据id取消某次操作
     *
     * @param cancelId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject confirmCancel(String cancelId) throws Exception {
        // get  CancelRecordInfo by  id
        CancelRecordInfo cancelDataInfo = cancelDataInfoRepository.findById(Integer.valueOf(cancelId)).orElse(null);
        if (cancelDataInfo == null) {
            return CommonResponseUtil.errorJson("找不到对应的取消记录!");
        }
        String entityData = cancelDataInfo.getEntityData();
        if (StringUtils.isBlank(entityData)) {
            cancelDataInfoRepository.deleteById(Integer.valueOf(cancelId));
            return CommonResponseUtil.successJson();
        }
        //取消新增object
        List<JSONObject> jsonObjects = Lists.newArrayList();
        //取消删除jsonObject
        List<JSONObject> deleteObjects = Lists.newArrayList();
        //取消修改object
        List<JSONObject> updateObjects = Lists.newArrayList();

        JSONArray jsonArray = JSON.parseArray(entityData);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (CancelOperationTypeEnum.CREATE.getName().equalsIgnoreCase(jsonObject.getString("cancelType"))) {
                jsonObjects.add(jsonObject);
            } else if (CancelOperationTypeEnum.UPDATE.getName().equalsIgnoreCase(jsonObject.getString("cancelType"))) {
                updateObjects.add(jsonObject);
            } else if (CancelOperationTypeEnum.DELETE.getName().equalsIgnoreCase(jsonObject.getString("cancelType"))) {
                deleteObjects.add(jsonObject);
            }
        }
        //create cancel process
        createCancelProcess(jsonObjects);
        //delete  cancel process
        deleteCancelProcess(deleteObjects);
        //update  cancel process
        updateCancelProcess(updateObjects);
        //删除取消记录
        cancelDataInfoRepository.deleteById(Integer.valueOf(cancelId));

        return CommonResponseUtil.successJson("取消成功!");
    }

    /**
     * update  cancel process
     *
     * @param updateObjects
     */
    private void updateCancelProcess(List<JSONObject> updateObjects) {

        if (CollectionUtils.isEmpty(updateObjects)) {
            return;
        }
        updateObjects.forEach(jsonObject -> {

            JSONObject beforeData = jsonObject.getJSONObject("beforeData");
            //获取要操作实体的全类名
            String className = jsonObject.getString("className");
            //实体id
            int id = beforeData.getIntValue("id");
            try {
                //获取表名
                String  tableName = getTableNameByClass(className);
                //获取加@Version注解的字段对应的数据库名和java字段名(用#分隔)
                String versionColumnName = getVersionNameByClassName(className);
                Object beforeDataObj = JSON.parseObject(beforeData.toJSONString(), Class.forName(className));
                if(StringUtils.isBlank(versionColumnName)  ){
                     //没有字段加@version注解,没有乐观锁,可以直接更新
                     entityManager.merge(beforeDataObj);
                     return;
                 }
                String[] split = versionColumnName.split("#");
                //加@version注解对应的数据库字段名
                String columnName=split[0];
                //加@version注解对应的java字段名
                String filedName=split[1];
                String sql="select "+columnName+" from  "+tableName+"  where id = ?";
                //获取当前id对应数据的最新版本号
                int version = (int) entityManager.createNativeQuery(sql).setParameter(1, id).getSingleResult();
                //修改berforeData的值为最新值
                Field versionFiled = beforeDataObj.getClass().getDeclaredField(filedName);
                versionFiled.setAccessible(true);
                versionFiled.set(beforeDataObj, version);
                entityManager.merge(beforeDataObj);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                log.error("update  cancel process error -------->"+e.getMessage(),e);
                throw new RuntimeException(e);
            }

        });


    }

    /**
     *  获取加@Version注解的字段对应的数据库名和java字段名,使用#分隔
     * @param className full class name
     * @return
     * @throws ClassNotFoundException
     */
    private String getVersionNameByClassName(String className) throws ClassNotFoundException {
        //对应的数据库字段名
        String columnName;
        //对应的java字段名
        String filedName;
        Class<?> clazz = Class.forName(className);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Version[] annotationsByType = declaredField.getAnnotationsByType(Version.class);
            if (annotationsByType != null && annotationsByType.length > 0) {
                Column column = declaredField.getAnnotation(Column.class);
                columnName = column.name();
                filedName = declaredField.getName();
                return columnName + "#" + filedName;
            }

        }
        return null;
    }

    /**
     * delete  cancel process
     *
     * @param deleteObjects
     */
    private void deleteCancelProcess(List<JSONObject> deleteObjects) {

        deleteObjects.forEach(jsonObject -> {
            //获取要操作实体的全类名
            String className = jsonObject.getString("className");
            try {
                Object o = Class.forName(className).newInstance();
                JSONObject beforeData = jsonObject.getJSONObject("beforeData");
                BeanUtils.populate(o, beforeData);
                //将主键id置空
                Field id = o.getClass().getDeclaredField("id");
                id.setAccessible(true);
                id.set(o, null);
                entityManager.persist(o);
                //entityManager.flush();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | InvocationTargetException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

        });


    }

    /**
     * create cancel process
     *
     * @param createObjects
     */
    private void createCancelProcess(List<JSONObject> createObjects) {

        if (CollectionUtils.isEmpty(createObjects)) {
            return;
        }
        createObjects.forEach(jsonObject -> {
            //获取要操作实体的全类名
            String className = jsonObject.getString("className");
            //要操作实体的id
            int id = jsonObject.getIntValue("id");

            String tableName ;
            try {
                //根据实体类全名获取表名
                tableName = getTableNameByClass(className);
                Assert.hasLength(tableName, className+"实体 未加 @Table注解");
            } catch (ClassNotFoundException |IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
            String sql="delete from "+tableName+ " where id=?";
            entityManager.createNativeQuery(sql).setParameter(1, id).executeUpdate();
        });


    }

    /**
     * 根据Class获取表名
     *
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private String getTableNameByClass(String className) throws ClassNotFoundException {

        Class<?> clazz = Class.forName(className);
        Table[] annotationsByType = clazz.getDeclaredAnnotationsByType(Table.class);
        if (annotationsByType != null && annotationsByType.length > 0) {
            String name = annotationsByType[0].name();
            if (StringUtils.isBlank(name)) {
                //未设置表名.默认实体名
                name = clazz.getSimpleName();
            }
            return name;

        } else {
            //未加@Table注解
            return null;

        }


    }
}
