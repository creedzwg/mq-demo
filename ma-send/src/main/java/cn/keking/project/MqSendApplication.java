package cn.keking.project;

import cn.keking.project.cancel.annotation.EnableCancelAutoConfiguration;
import cn.keking.project.cancel.mq.CancelSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//开启自动配置
@EnableCancelAutoConfiguration
//@EntityScan({"com.zwg","cn.keking.project.cancel.entity"})
public class MqSendApplication implements CommandLineRunner {



    @Autowired
    private CancelSender cancelSender;

    public static void main(String[] args) {
        SpringApplication.run(MqSendApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
         String message="{\n" +
                 "\n" +
                 "  \"id\": \"56\",\n" +
                 "  \"description\":\"取消操作的描述\",\n" +
                 "  \"entityData\":[\n" +
                 "\n" +
                 "      {\n" +
                 "      \"cancelType\":\"create\",\n" +
                 "      \"className\":\"cn.keking.project.entity.User1\",\n" +
                 "      \"id\":\"1\"\n" +
                 "      },\n" +
                 "    {\n" +
                 "      \"cancelType\":\"create\",\n" +
                 "      \"className\":\"cn.keking.project.entity.User2\",\n" +
                 "      \"id\":\"2\"\n" +
                 "    },\n" +
                 "      {\n" +
                 "      \"cancelType\":\"update\",\n" +
                 "      \"className\":\"cn.keking.project.entity.User1\",\n" +
                 "      \"beforeData\":{\n" +
                 "        \"age\": \"108\",\n" +
                 "        \"id\": 4,\n" +
                 "        \"passWord\": \"普通回滚\",\n" +
                 "        \"userName\": \"普通回滚\"\n" +
                 "      }\n" +
                 "    },\n" +
                 "    {\n" +
                 "      \"cancelType\":\"update\",\n" +
                 "      \"className\":\"cn.keking.project.entity.User2\",\n" +
                 "      \"beforeData\":{\n" +
                 "        \"age\": \"14\",\n" +
                 "        \"id\": 7,\n" +
                 "        \"jpaVersion\": 2,\n" +
                 "        \"passWord\": \"回滚\",\n" +
                 "        \"userName\": \"回滚\"\n" +
                 "      }\n" +
                 "    },\n" +
                 "    {\n" +
                 "      \"cancelType\":\"delete\",\n" +
                 "      \"className\":\"cn.keking.project.entity.User1\",\n" +
                 "      \"beforeData\":{\n" +
                 "        \"age\": \"哈哈\",\n" +
                 "        \"id\": 4,\n" +
                 "        \"passWord\": \"delete 还原\",\n" +
                 "        \"userName\": \"delete 还原\"\n" +
                 "      }\n" +
                 "    },\n" +
                 "    {\n" +
                 "      \"cancelType\":\"delete\",\n" +
                 "      \"className\":\"cn.keking.project.entity.User2\",\n" +
                 "      \"beforeData\":{\n" +
                 "        \"age\": \"delete 还原\",\n" +
                 "        \"id\": 100,\n" +
                 "        \"jpaVersion\": 2,\n" +
                 "        \"passWord\": \"delete 还原\",\n" +
                 "        \"userName\": \"delete 还原\"\n" +
                 "      }\n" +
                 "    }\n" +
                 "  ]\n" +
                 "\n" +
                 "}\n" +
                 "\n";
        System.out.println(message);
       //cancelSender.send(message);


    }
}
