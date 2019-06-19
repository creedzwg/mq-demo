package com.zwg.masend;

import com.zwg.mqconsumer.UserService;
import com.zwg.mqconsumer.annotation.CancelAutoConfiguration;
import com.zwg.mqconsumer.entity.User;
import com.zwg.mqconsumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Optional;

@SpringBootApplication
@CancelAutoConfiguration
@EntityScan("com.zwg")
public class MaSendApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(MaSendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User byId = userService.findById(1992L);
        System.out.println(byId);
        Optional<User> byId1 = userRepository.findById(1993L);
        System.out.println("----"+byId1);
        User user = new User();
        user.setUserName("张文刚3435345");
        user.setPassWord("ahhaahha");
        User user2 = new User();
        user2.setUserName("张文刚222");
        user2.setPassWord("ahhaahha22222");
        User user1 = userService.saveUser(user);
      //  int i=1/0;
        User user4 = userService.saveUser(user2);
    }
}
