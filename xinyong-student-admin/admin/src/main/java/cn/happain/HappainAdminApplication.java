package cn.happain;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author happain
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.happain.**"})
@MapperScan(value ="cn.happain",annotationClass = Mapper.class)
@EnableTransactionManagement
public class HappainAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappainAdminApplication.class, args);
    }

}
