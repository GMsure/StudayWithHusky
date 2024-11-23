package com.huskya.tutorial2;

import com.huskya.tutorial2.service.UserService;
import com.huskya.tutorial2.service.UserServiceImpl;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        LoggingHandler handler = new LoggingHandler(userService);


//        UserService proxy = new UserServiceProxy(userService);

        UserService proxy = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                handler
        );

        proxy.registerUser("Alice");
        proxy.login("Alice");
        System.out.println(proxy.getClass().getName());
    }
}
