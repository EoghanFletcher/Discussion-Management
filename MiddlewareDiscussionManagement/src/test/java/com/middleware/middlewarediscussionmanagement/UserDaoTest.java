package com.middleware.middlewarediscussionmanagement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.sun.source.tree.AssertTree;
import dao.Dao;
import dao.UserDao;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.firebase.auth.UserRecord.CreateRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoTest {
    private static UserDao userDao = null;
    private static FirebaseAuth firebaseAuthInstance = null;
    private static Firestore firestore = null;
    private static HashMap documentData = null;
    private static UserRecord userRecord = null;
    private static boolean result;
    private static DocumentSnapshot documentSnapshot = null;
    private static ApiFuture<QuerySnapshot> future = null;
    private static List<DocumentSnapshot> listDocumentSnapshot = null;
    private static CollectionReference collectionReference = null;
    private static String databaseCollection = "UserTest";
    private static String username = "JUnit";
    String key = "JUnit";
    String value = "UserTest";

    @BeforeClass
    public static void setupData() {
        System.out.println("setupData");
        Dao.initialiseFirebaseConnection();

        try {
            userDao = new UserDao();
            documentData = new HashMap();
            firebaseAuthInstance = FirebaseAuth.getInstance();

            firebaseAuthInstance.createUser(new CreateRequest().setEmail("JUnit@gmail.com").setPassword("JUnit@gmail.com"));
            Thread.sleep(2000);
            userRecord = firebaseAuthInstance.getUserByEmail("JUnit@gmail.com");

            firestore = Dao.initialiseFirestore();


            documentData.put("uId", userRecord.getUid());
            documentData.put("email", userRecord.getEmail());
            documentData.put("username", username);

            System.out.println("entryset: " + documentData.entrySet());

            collectionReference = firestore.collection(databaseCollection);
//            collectionReference.document().set(documentData);
        }
        catch(Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void getAuthenticationInstance() {
        System.out.println("getAuthenticationInstance");

        System.out.println("==========");
        System.out.println("getAuthenticationInstance");
        System.out.println("==========");

        Assert.assertEquals(firebaseAuthInstance, userDao.getAuthenticationInstance());

    }

    @Test
    public void getUserDocument() {
        System.out.println("getUserDocument");

        System.out.println("==========");
        System.out.println("Valid");
        System.out.println("==========\n");

        System.out.println("==========");
        System.out.println("register");
        System.out.println("==========");


        System.out.println("uId: " + documentData.get("uId").toString());
        System.out.println("email: " + documentData.get("email").toString());
        System.out.println("username: " + documentData.get("username").toString());

        documentSnapshot = userDao.register(documentData.get("uId").toString(), documentData.get("email").toString(), documentData.get("username").toString(), databaseCollection);
        System.out.println("documentSnapshot: " + documentSnapshot.getData().entrySet());
        System.out.println("documentData: " + documentData.entrySet());
        Assert.assertNotNull("The value returned is null", documentSnapshot);
        Assert.assertTrue("the values are not equal (uId)", documentSnapshot.getData().get("uId").toString().equals(documentData.get("uId").toString()));
        Assert.assertTrue("the values are not equal (email)", documentSnapshot.getData().get("email").toString().equals(documentData.get("email").toString()));
        Assert.assertTrue("the values are not equal (username)", documentSnapshot.getData().get("username").toString().equals(documentData.get("username").toString()));

        System.out.println("here1");

        System.out.println("==========");
        System.out.println("getUserDocumentByUId");
        System.out.println("==========");

        Assert.assertEquals("Documents should be equal", userRecord.getUid(),
                userDao.getUserDocumentByUId(userRecord.getUid(), userRecord.getEmail(), databaseCollection).getData().get("uId"));

        System.out.println("here2");

        System.out.println("==========");
        System.out.println("getUserDocumentByEmail");
        System.out.println("==========");

        Assert.assertEquals("Documents should be equal", userRecord.getEmail(),
                userDao.getUserDocumentByEmail(userRecord.getEmail(), databaseCollection).getData().get("email"));

        System.out.println("==========");
        System.out.println("Invalid");
        System.out.println("==========\n");

        System.out.println("==========");
        System.out.println("register - Existing account");
        System.out.println("==========");

        documentSnapshot = userDao.register(documentData.get("uId").toString(), documentData.get("email").toString(), documentData.get("username").toString(), databaseCollection);
        Assert.assertEquals(documentData.get("uId"), documentSnapshot.get("uId"));
        Assert.assertEquals(documentData.get("email"), documentSnapshot.get("email"));
        Assert.assertEquals(documentData.get("username"), documentSnapshot.get("username"));

        System.out.println("==========");
        System.out.println("getUserDocumentByUId");
        System.out.println("==========");

        Assert.assertNull("Documents should be not equal",
                userDao.getUserDocumentByUId("invalid", "invalid", databaseCollection));

        System.out.println("==========");
        System.out.println("getUserDocumentByEmail");
        System.out.println("==========");

        Assert.assertNull("Documents should be not equal",
                userDao.getUserDocumentByEmail("invalid@emailaddress.com", databaseCollection));

        System.out.println("==========");
        System.out.println("createUpdateProfileField - Create");
        System.out.println("==========");

        Assert.assertTrue((userDao.createUpdateProfileField(documentSnapshot.getData().get("uId").toString(),
                                                            documentSnapshot.getData().get("username").toString(),
                                                        "testCredential",
                                                        "createCredentialTest",
                                                            databaseCollection)));

        System.out.println("==========");
        System.out.println("createUpdateProfileField - Update");
        System.out.println("==========");

        Assert.assertTrue((userDao.createUpdateProfileField(documentSnapshot.getData().get("uId").toString(),
                                                            documentSnapshot.getData().get("username").toString(),
                                                            "testCredential",
                                                            "updateCredentialTest",
                                                            databaseCollection)));

        System.out.println("==========");
        System.out.println("removeProfileField");
        System.out.println("==========");

        Assert.assertTrue((userDao.removeProfileField(documentSnapshot.getData().get("uId").toString(),
                                                            documentSnapshot.getData().get("username").toString(),
                                                        "testCredential",
                                                            databaseCollection)));

        System.out.println("==========");
        System.out.println("removeProfileField - Does not exist");
        System.out.println("==========");

        System.out.println("here: " + userDao.removeProfileField(documentSnapshot.getData().get("uId").toString(),
                documentSnapshot.getData().get("username").toString(),
                "DoesNotExist",
                databaseCollection));

        Assert.assertFalse(userDao.removeProfileField(documentSnapshot.getData().get("uId").toString(),
                documentSnapshot.getData().get("username").toString(),
                "DoesNotExist",
                databaseCollection));

        System.out.println("==========");
        System.out.println("listUsers");
        System.out.println("==========");

        Assert.assertEquals(1, userDao.listUsers(databaseCollection).size());

        Map collectionData = userDao.listUsers(databaseCollection).get(0).getData();

        Assert.assertEquals(collectionData.get("uId"), documentSnapshot.get("uId"));
        Assert.assertEquals(collectionData.get("email"), documentSnapshot.get("email"));
        Assert.assertEquals(collectionData.get("username"), documentSnapshot.get("username"));
    }

    @Test
    public void testForConnectivity() {
        System.out.println("testForConnectivity");

        Assert.assertEquals("Connection established", userDao.testForConnectivity());
    }



    @AfterClass
    public static void removeTestData() {
        System.out.println("removeTestData");
        try {
            documentData = new HashMap();
            firestore.recursiveDelete(collectionReference);
            firebaseAuthInstance.deleteUser(userRecord.getUid());
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

}
