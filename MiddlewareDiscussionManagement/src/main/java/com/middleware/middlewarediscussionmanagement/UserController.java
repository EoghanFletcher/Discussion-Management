package com.middleware.middlewarediscussionmanagement;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.UserDao;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    UserDao userDao = new UserDao();
    String databaseCollection = "User";

    @PostMapping(path = "/authenticateEmailPassword")
    public Map authenticate(@RequestBody HashMap data) {

            DocumentSnapshot documentSnapshot = null;
            UserRecord userRecord = null;

        try {
            String uIdString = (String) data.get("uId");
            String emailString = (String) data.get("email");
            String usernameString = (String) data.get("username");

            FirebaseAuth firebaseAuth = userDao.getAuthenticationInstance();
            UserRecord userRecordUId = userDao.getUId(uIdString, firebaseAuth);

            if (userRecordUId != null) {
                if (usernameString == null) {
                    System.out.println("login");
                    documentSnapshot = userDao.getUserDocumentByEmail(emailString, databaseCollection); }
                else {
                    System.out.println("register");
                    documentSnapshot = userDao.register(uIdString, emailString, usernameString, databaseCollection); }
            }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [authenticate], ex: " + ex);
            ex.printStackTrace();
        }
        return  documentSnapshot.getData();
    }

    @PostMapping(path = "getAllUsers")
    public List<DocumentSnapshot> getAllUsers (@RequestBody HashMap data) {
        System.out.println("getAllUsers");

        List documentListData = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;

        try {
            FirebaseAuth firebaseAuth = userDao.getAuthenticationInstance();

            listDocumentSnapshot = userDao.listUsers(databaseCollection);

            documentListData = new ArrayList();
            for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getAllUsers], ex: " + ex);
            ex.printStackTrace();
        }
        return documentListData;
        }

    @GetMapping(path = "testForConnectivity")
    public String testForConnectivity () {
        System.out.println("testForConnectivity");

        try {
            return userDao.testForConnectivity();
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [testForConnectivity], ex: " + ex);
            ex.printStackTrace();
        }
        return null;
    }
}
