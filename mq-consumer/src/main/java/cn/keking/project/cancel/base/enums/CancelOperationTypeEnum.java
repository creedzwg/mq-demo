package cn.keking.project.cancel.base.enums;

/**
 * @Author: zhangwengang
 * @Date: 2019/6/20
 * @Version: V1.0
 * @Description: 取消操作类型枚举
 */
public enum CancelOperationTypeEnum {
    /**
     * update
     */
    UPDATE("update"),
    /**
     * update
     */
    DELETE("delete"),
    /**
     * update
     */
    CREATE("create");


    private String name;

    CancelOperationTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
