package com.middleware.middlewarediscussionmanagement;

import business.User;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.CoderResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/user")
//@CrossOrigin
public class UserController {
    UserDao userDao = new UserDao();

    @PostMapping(path = "/authenticate")
    public Map authenticate(@RequestBody HashMap uId) {

        DocumentSnapshot documentSnapshot = null;
        UserRecord userRecord = null;

        String uIdString = (String) uId.get("uId").toString();
        FirebaseAuth firebaseAuth = userDao.getAuthenticationInstance();
        UserRecord userRecordUId = userDao.getUId(uIdString, firebaseAuth);

        if (userRecordUId != null) {
            documentSnapshot = userDao.getUserDocument(uIdString);
        }

        return  documentSnapshot.getData();
    }
}
