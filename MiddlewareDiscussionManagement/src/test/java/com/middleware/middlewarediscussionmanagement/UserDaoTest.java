package com.middleware.middlewarediscussionmanagement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import dao.Dao;
import dao.UserDao;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import java.util.HashMap;
import java.util.List;

public class UserDaoTest {
    private static UserDao userDao = null;
    public static FirebaseAuth firebaseAuthInstance = null;
    public static Firestore firestore = null;
    public static HashMap documentData = null;
    public static UserRecord userRecord = null;
    public static String uId = null;
    public static boolean result;
    public static DocumentSnapshot documentSnapshot = null;
    public static ApiFuture<QuerySnapshot> future = null;
    public static List<QueryDocumentSnapshot> documents = null;
    public static CollectionReference collectionReference = null;
    String collection = "UserTest";

    @BeforeClass
    public static void setupData() {
        System.out.println("setupData");
        Dao.initialiseFirebaseConnection();

        try {
            userDao = new UserDao();
            documentData = new HashMap();
            firebaseAuthInstance = FirebaseAuth.getInstance();

            firebaseAuthInstance.createUser(new CreateRequest().setEmail("JUnit@gmail.com").setPassword("JUnit@gmail.com"));

            userRecord = firebaseAuthInstance.getUserByEmail("JUnit@gmail.com");

            firestore = Dao.initialiseFirestore();


            documentData.put("uId", userRecord.getUid());
            collectionReference = firestore.collection("UserTest");
            collectionReference.document().set(documentData);
        }
        catch(Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }

    }

    @Test
    public void createUpdateProfileField_Valid() {
        System.out.println("createUpdateProfileField_Valid");

        try {
        System.out.println("Create field");
        result = userDao.createUpdateProfileField(userRecord.getUid(), "JUnit", "JUnit", "UserTest", collection);

        if (result) {
            System.out.println("Field created proceeding to the next section");

            System.out.println("get uId: " + userRecord.getUid());


//            future = firestore.collection("User").whereEqualTo("uId", uid).get();
            future = firestore.collection("UserTest").whereEqualTo("uId", userRecord.getUid()).get();
            System.out.println("here");
            documents = future.get().getDocuments();
            System.out.println("retrievedDocuments: "  + documents.size());
            System.out.println("here");

            for (DocumentSnapshot document : documents) {
                if (document.get("uId").equals(userRecord.getUid())) {
                    documentSnapshot = document;
                }
            }

            System.out.println("documentffff: " + documentSnapshot.getData());

        }
        else {
            System.out.println("Field was not created, terminating");
        }

        }
        catch(Exception ex) {
            System.out.println("An error occurred [createProfileField_Valid], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void createUpdateProfileField_Invalid() {
        System.out.println("createUpdateProfileField_Invalid");

    }

    @AfterClass
    public static void removeTestData() {
        System.out.println("removeTestData");
        try {
            documentData = new HashMap();
//            firestore.recursiveDelete(collectionReference);
//            firebaseAuthInstance.deleteUser(userRecord.getUid());
        }
        catch(Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }

}
