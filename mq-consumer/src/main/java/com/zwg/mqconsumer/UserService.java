package com.zwg.mqconsumer;

import com.zwg.mqconsumer.entity.User;
import com.zwg.mqconsumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * @Author: zwg15
 * @Date: 2019/6/19
 * @Version: V1.0
 * @Description:
 */

public class UserService {

    private EntityManager entityManager;

    public UserService(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    public User findById(Long id){
        return entityManager.find(User.class, id);
    }
   @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user){
    User save = userRepository.save(user);
    int i=1/0;
    return save ;
    }
}
