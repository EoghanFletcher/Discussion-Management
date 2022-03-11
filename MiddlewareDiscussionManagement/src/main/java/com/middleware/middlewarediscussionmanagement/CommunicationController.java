package com.middleware.middlewarediscussionmanagement;

import dao.UserDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/communication")
public class CommunicationController {

    UserDao userDao = new UserDao();
    String databaseCollection = "User";

    @PostMapping(path = "/emails")
    public void getEmails(@RequestBody HashMap data) {
        System.out.println("getEmails");

        System.out.println("data: " + data.entrySet());

        String groupNameString = (String) data.get("email");
        String usernameString = (String) data.get("username");

    }

}
