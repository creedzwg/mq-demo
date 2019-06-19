package com.zwg.mqconsumer.configuration;

import com.zwg.mqconsumer.UserService;
import com.zwg.mqconsumer.entity.User;
import com.zwg.mqconsumer.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@Configuration
@AutoConfigureOrder(Integer.MAX_VALUE)
@EnableJpaRepositories(basePackages={"com.zwg.mqconsumer.repository"}) //这个是你Repositorie所在的包
@EnableTransactionManagement //这个是事务
public class CancelConfiguration implements ApplicationContextAware {
    @Value("${spring.application.name}")
    private String name;

    private ApplicationContext applicationContext;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @PostConstruct
    public void ha(){
        log.info("haahha===="+name);
        LocalContainerEntityManagerFactoryBean bean = applicationContext.getBean(LocalContainerEntityManagerFactoryBean.class);
        bean.setPackagesToScan("com.zwg.mqconsumer.entity");
        //String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

       // System.out.println();
    }
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource") //需要导入配置
//    public DataSource dataSource(){
//        return DataSourceBuilder.create().build();
//    }
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//        vendorAdapter.setDatabase(Database.MYSQL);//这里指定的你数据库的类型
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.zwg.mqconsumer.entity");//这个是你entity所在的包
//        factory.setDataSource(dataSource());
//        return factory;
////    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory);
//        return txManager;
//    }
        @Bean
         public UserService userService(){
        return new UserService(entityManager,userRepository);

         }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
