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
    String databaseCollection = "User";

    @PostMapping(path = "create")
    public void createCredential(@RequestBody HashMap data) {

        String uIdString = (String) data.get("uId");
        String usernameString = (String) data.get("username");
        String chosenKey = (String) data.get("chosenKey");
        String chosenValue = (String) data.get("chosenValue");

        userDao.createUpdateProfileField(uIdString, usernameString, chosenKey, chosenValue, databaseCollection);
    }

    @PostMapping(path = "update")
    public void updateCredential(@RequestBody HashMap data) {
        System.out.println("updateCredential");

        String uIdString = (String) data.get("uId");
        String usernameString = (String) data.get("username");
        String chosenKey = (String) data.get("chosenKey");
        String chosenValue = (String) data.get("chosenValue");

        userDao.createUpdateProfileField(uIdString, usernameString, chosenKey, chosenValue, databaseCollection);
    }

    @PostMapping(path = "delete")
    public void deleteCredential(@RequestBody HashMap data) {
        System.out.println("deleteCredential");

        String uIdString = (String) data.get("uId");
        String usernameString = (String) data.get("username");
        String deleteKey = (String) data.get("deleteKey");

        userDao.removeProfileField(uIdString, usernameString, deleteKey, databaseCollection);
    }
}
