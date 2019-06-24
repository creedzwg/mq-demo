package cn.keking.project.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description:
 */
@Data
@Entity
@Table(name = "user_02")
public class User2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "age")
    private String age;
    @Column(name = "jpa_version", columnDefinition = " int comment 'jpaVersion'")
    @Version
    private Integer jpaVersion;
}
