package com.middleware.middlewarediscussionmanagement;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.CoderResult;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
//@CrossOrigin
public class UserController {
    UserDao userDao = new UserDao();

    @GetMapping(path = "/test")
//    @CrossOrigin(origins = "http://localhost:8081")
    public List test() { return List.of("Test Message"); }

    @PostMapping(path = "/authenticate")
    public DocumentSnapshot authenticate(@RequestParam(value = "uId") String uId) {
//
        System.out.println("uId: " + uId);
        System.out.println("Middleware");

//        String uId="yBbdYqYqfAXpZM5HawTfVIMFoTV2";
        DocumentSnapshot documentSnapshot = null;
        UserRecord userRecord = null;

        FirebaseAuth firebaseAuth = userDao.getAuthenticationInstance();

        System.out.println("FirebaseAuth: " + firebaseAuth);

        uId = userDao.getUId(uId, firebaseAuth).getUid();

        System.out.println("uId: " + uId);

        documentSnapshot = userDao.getUserDocument(uId);

        System.out.println("DocumentSnapshot: " + documentSnapshot.getData().entrySet());

        System.out.println("return");

        return documentSnapshot;
    }
}
