package com.huskya.tutorial2.service;

import com.huskya.tutorial2.annotations.Loggable;

public interface UserService {
//    @Loggable
    void registerUser(String user);
    void login(String user);
}
