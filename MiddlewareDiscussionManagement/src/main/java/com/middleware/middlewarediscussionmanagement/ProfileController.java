package com.middleware.middlewarediscussionmanagement;

import dao.UserDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/credentials")
public class ProfileController {
    UserDao userDao = new UserDao();

    @PostMapping(path = "create")
    public void createCredential(@RequestBody HashMap data) {
        System.out.println("data: " + data);

        String uIdString = (String) data.get("uId");
        String chosenKey = (String) data.get("chosenKey");
        String chosenValue = (String) data.get("chosenValue");

        System.out.println("uIdString: " + uIdString);
        System.out.println("chosenKey " + chosenKey);
        System.out.println("chosenValue: " + chosenValue);

        userDao.createUpdateProfileField(uIdString, chosenKey, chosenValue);
    }

    @PostMapping(path = "update")
    public void updateCredential(@RequestBody HashMap data) {
        System.out.println("data: " + data);

        String uIdString = (String) data.get("uId");
        String chosenKey = (String) data.get("chosenKey");
        String chosenValue = (String) data.get("chosenValue");

        System.out.println("uIdString: " + uIdString);
        System.out.println("chosenKey " + chosenKey);
        System.out.println("chosenValue: " + chosenValue);

        userDao.createUpdateProfileField(uIdString, chosenKey, chosenValue);
    }

    @PostMapping(path = "delete")
    public void deleteCredential(@RequestBody HashMap data) {
        System.out.println("/deleteCredential");

        System.out.println("data: " + data);

        String uIdString = (String) data.get("uId");
        String deleteKey = (String) data.get("deleteKey");

        System.out.println("uIdString: " + uIdString);
        System.out.println("deleteKey: " + deleteKey);

        userDao.removeProfileField(uIdString, deleteKey);


    }
}
