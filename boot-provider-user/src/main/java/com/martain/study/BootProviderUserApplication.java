package com.martain.study;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author martain
 */
@EnableDubbo
@SpringBootApplication
public class BootProviderUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootProviderUserApplication.class, args);
    }

}
