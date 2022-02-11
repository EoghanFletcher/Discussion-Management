package com.middleware.middlewarediscussionmanagement;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import dao.Dao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class MiddlewareDiscussionManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareDiscussionManagementApplication.class, args);

        System.out.println("initialiseFirebaseConnection");

        Dao.initialiseFirebaseConnection();
    }

}
