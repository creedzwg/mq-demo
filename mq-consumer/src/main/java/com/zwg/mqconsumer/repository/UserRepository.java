package com.zwg.mqconsumer.repository;

import com.zwg.mqconsumer.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description:
 */

public interface UserRepository extends CrudRepository<User,Long> {
}
