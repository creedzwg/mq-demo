package com.zwg.mqconsumer.annotation;

import com.zwg.mqconsumer.configuration.CancelConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CancelConfiguration.class})
public @interface CancelAutoConfiguration {
}
