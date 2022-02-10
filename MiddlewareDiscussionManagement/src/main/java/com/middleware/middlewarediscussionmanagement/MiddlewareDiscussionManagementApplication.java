package com.middleware.middlewarediscussionmanagement;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class MiddlewareDiscussionManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddlewareDiscussionManagementApplication.class, args);

        System.out.println("initialiseFirebaseConnection");

        try {
            FileInputStream accountCredentials = new FileInputStream("./keys/discussionmanagement-a9065-firebase-adminsdk-eqdn6-bcec425738.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(accountCredentials))
                    .setDatabaseUrl("https://ProjectPrototype-DemoApp-DkIT.firebaseio.com/")
                    .build();

            System.out.println("here: " + FirebaseApp.initializeApp(options));
        }
        catch (Exception ex) {
            System.out.println("An exception occurred, failed to connect to database [initialiseFirebaseConnection], ex: " + ex);
            ex.printStackTrace();
        }
    }

}
