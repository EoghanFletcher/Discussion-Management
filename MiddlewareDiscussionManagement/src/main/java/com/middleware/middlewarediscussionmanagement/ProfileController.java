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

    @PostMapping(path = "/create")
    public void createCredential(@RequestBody HashMap data) {

        try {
            String uIdString = (String) data.get("uId");
            String usernameString = (String) data.get("username");
            String chosenKey = (String) data.get("chosenKey");
            String chosenValue = (String) data.get("chosenValue");

            userDao.createUpdateProfileField(uIdString, usernameString, chosenKey, chosenValue, databaseCollection);
        }
        catch (Exception ex) {
                System.out.println("An exception occurred [createCredential], ex: " + ex);
                ex.printStackTrace();
            }
    }

    @PostMapping(path = "/update")
    public void updateCredential(@RequestBody HashMap data) {
        System.out.println("updateCredential");

        try {
            String uIdString = (String) data.get("uId");
            String usernameString = (String) data.get("username");
            String chosenKey = (String) data.get("chosenKey");
            String chosenValue = (String) data.get("chosenValue");

            userDao.createUpdateProfileField(uIdString, usernameString, chosenKey, chosenValue, databaseCollection);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [updateCredential], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @PostMapping(path = "/delete")
    public void deleteCredential(@RequestBody HashMap data) {
        System.out.println("deleteCredential");

        try {
            String uIdString = (String) data.get("uId");
            String usernameString = (String) data.get("username");
            String deleteKey = (String) data.get("deleteKey");

            userDao.removeProfileField(uIdString, usernameString, deleteKey, databaseCollection);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [deleteCredential], ex: " + ex);
            ex.printStackTrace();
        }
    }
}
