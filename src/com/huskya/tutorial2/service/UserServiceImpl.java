package com.huskya.tutorial2.service;

import com.huskya.tutorial2.annotations.Loggable;

public class UserServiceImpl implements UserService {
    @Loggable
    @Override
    public void registerUser(String user) {
        System.out.println("Registering user:" + user);
    }

    @Override
    public void login(String user) {
        System.out.println("Logged in");
    }
}
