package com.huskya.tutorial1.service;

import com.huskya.tutorial1.DatabaseConnection;
import com.huskya.tutorial1.annotations.Component;
import com.huskya.tutorial1.annotations.Inject;
import com.huskya.tutorial1.annotations.PostConstruct;

@Component
public class UserService {
    @Inject
    private DatabaseConnection databaseConnection;

    public void register(String name) {
        System.out.println("Registering user: " + name);
    }

    @PostConstruct
    public void init() {
        System.out.println("UserService: Initializing with necessary data... ");

        databaseConnection.loadData();
    }
}
