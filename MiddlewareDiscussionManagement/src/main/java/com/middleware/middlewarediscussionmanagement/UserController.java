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

    @GetMapping(path = "/test")
//    @CrossOrigin(origins = "http://localhost:8081")
    public List test() { return List.of("Test Message"); }

    @PostMapping(path = "/authenticate")
    public Map authenticate(@RequestBody HashMap uId) {
//
        System.out.println("uId: " + uId.entrySet());
        System.out.println("uId: " + uId.get("uId"));
        System.out.println("Middleware");

//        String uId="yBbdYqYqfAXpZM5HawTfVIMFoTV2";
        DocumentSnapshot documentSnapshot = null;
        UserRecord userRecord = null;

        String uIdString = (String) uId.get("uId").toString();

        System.out.println("uIdString: " + uIdString);

        FirebaseAuth firebaseAuth = userDao.getAuthenticationInstance();

        System.out.println("FirebaseAuth: " + firebaseAuth);

        UserRecord userRecordUId = userDao.getUId(uIdString, firebaseAuth);
        System.out.println("UserRecordUId: " + userRecordUId.getUid());

//        System.out.println("uId: " + uId);

        if (userRecordUId != null) {
            documentSnapshot = userDao.getUserDocument(uIdString);

            System.out.println("DocumentSnapshot: " + documentSnapshot.getData().entrySet());
        }

        System.out.println("return");

//        User user = new User((String) documentSnapshot.get("uId"));


        return  documentSnapshot.getData();
    }
}
