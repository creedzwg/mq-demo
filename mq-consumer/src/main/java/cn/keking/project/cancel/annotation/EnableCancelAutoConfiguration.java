package cn.keking.project.cancel.annotation;

import cn.keking.project.cancel.configuration.CancelConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CancelConfiguration.class})
@EntityScan({"cn.keking.project"})
public @interface EnableCancelAutoConfiguration {


}
