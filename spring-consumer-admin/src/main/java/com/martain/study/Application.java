package com.martain.study;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:50 下午
 */
public class Application {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");

        IAdminService adminService = applicationContext.getBean(IAdminService.class);
        adminService.findUserById("10086");
        System.out.println("调用完成....");
        System.in.read();
    }
}
