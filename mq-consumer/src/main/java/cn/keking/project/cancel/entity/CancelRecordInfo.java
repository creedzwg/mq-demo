package cn.keking.project.cancel.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zhangwengang
 * @Date: 2019/6/20
 * @Version: V1.0
 * @Description: 取消记录实体
 */
@Table(name = "cancel_record_info")
@Entity
@Data
public class CancelRecordInfo implements Serializable {


    private static final long serialVersionUID = -235589872799793899L;

    @Id
    @Column(name = "id", nullable = false,columnDefinition = "int(10) ")
    private Integer id;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false,columnDefinition = " timestamp null default null comment  'create date'")
    @CreationTimestamp
    private Date createDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "update_date",columnDefinition = " timestamp null default null  comment 'update date' " )
    @UpdateTimestamp
    private Date updateDate;

    @Column(name = "jpa_version", columnDefinition = " int comment 'jpaVersion'")
    @Version
    private Integer jpaVersion;
    @Column(name = "description",columnDefinition = "varchar(255)  comment '取消描述'")
    private String description;

    @Column(name = "entity_Data",columnDefinition = "varchar(1000) comment '要取消的实体数据json集合'")
    private String  entityData;







}
