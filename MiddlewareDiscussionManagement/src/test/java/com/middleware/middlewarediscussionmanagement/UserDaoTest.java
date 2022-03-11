package com.middleware.middlewarediscussionmanagement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import dao.Dao;
import dao.UserDao;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDaoTest {
    private static UserDao userDao = null;
    private static FirebaseAuth firebaseAuthInstance = null;
    private static Firestore firestore = null;
    private static HashMap documentData = null;
    private static UserRecord userRecord = null;
    private static String uId = null;
    private static boolean result;
    private static DocumentSnapshot documentSnapshot = null;
    private static ApiFuture<QuerySnapshot> future = null;
    private static List<QueryDocumentSnapshot> documents = null;
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

            collectionReference = firestore.collection(databaseCollection);
            collectionReference.document().set(documentData);
        }
        catch(Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }

    }

    @Test
    public void getAuthenticationInstance() {
        System.out.println("getAuthenticationInstance");

        Assert.assertEquals(firebaseAuthInstance, userDao.getAuthenticationInstance());
    }

    @Test
    public void getUserDocument() {
        System.out.println("getUserDocument_Valid");

//        System.out.println("uId: " + userDao.getUserDocument(userRecord.getUid(), userRecord.getEmail(), databaseCollection).getData().entrySet());

        Assert.assertEquals("Documents should be equal", userRecord.getUid(),
                userDao.getUserDocument(userRecord.getUid(), userRecord.getEmail(), databaseCollection).getData().get("uId"));

        System.out.println("getUserDocument_Invalid");

        Assert.assertNull("Documents should not be equal",
                userDao.getUserDocument("fakeUid", "fakeEmail", databaseCollection));
    }

    @Test
    public void register() {
        System.out.println("register_Valid");

        documentSnapshot = userDao.register("testRegister", "testRegister@gmail.com", "usernameTest", databaseCollection);
        Assert.assertNotNull("The value returned is null", documentSnapshot);
        Assert.assertTrue("the values are not equal", documentSnapshot.getData().get("uId").toString().equals("testRegister"));

        System.out.println("register_Invalid");

        // The method should return the user created in the valid method
        documentSnapshot = userDao.register("testRegister", "testRegister@gmail.com", "usernameTest", databaseCollection);
        Assert.assertNotNull("The value returned is null", documentSnapshot);
        Assert.assertTrue("the values are not equal", documentSnapshot.getData().get("uId").toString().equals("testRegister"));
    }

    @Test
    public void getUid() {
        System.out.println("getUid_Valid");

        Assert.assertEquals(userRecord.getUid(), userDao.getUId(userRecord.getUid(), firebaseAuthInstance).getUid());

        System.out.println("getUid_Invalid");

        Assert.assertNull("The value returned is null", userDao.getUId("test", firebaseAuthInstance));
    }

    @Test
    public void createUpdateProfileField_Valid() {
        System.out.println("createUpdateProfileField_Valid");


        try {
            System.out.println("\n CREATE_ProfileField_Valid \n");
            // Create Field

            Assert.assertEquals("failed to create field", true,
                    userDao.createUpdateProfileField(userRecord.getUid(), username, key, value, databaseCollection));
            // Get Data
            documentData = (HashMap) userDao.getUserDocument(userRecord.getUid(), userRecord.getEmail(), databaseCollection).getData();
//          future = firestore.collection("UserTest").whereEqualTo("uId", userRecord.getUid()).get();
//          documentData = (HashMap) future.get().getDocuments().get(0).getData();
            Assert.assertEquals("failed to retrieve data after create", userRecord.getUid().toString(),
                    documentData.get("uId").toString());

            Assert.assertEquals("failed to retrieve data after create (check value)", value,
                    documentData.get(key).toString());








            System.out.println("\n UPDATE_ProfileField_Valid \n");
            // Update

            String updatedValue = "newValue";

            Assert.assertEquals("failed to update field", true,
                    userDao.createUpdateProfileField(userRecord.getUid(), username, key, updatedValue, databaseCollection));


            documentData = (HashMap) userDao.getUserDocument(userRecord.getUid(), userRecord.getEmail(), databaseCollection).getData();

            System.out.println("document data: " + key + ": " + documentData.get(key));
            System.out.println("updatedValue: " + updatedValue);
            Assert.assertEquals("failed to retrieve data after update", updatedValue,
                    documentData.get(key).toString());








            System.out.println("\n REMOVE_ProfileField_Valid \n");
            // Remove

            System.out.println("username: " + username);
            System.out.println("key: " + key);

            System.out.println("database collection: " + databaseCollection);
//            Function is slightly broken
            Assert.assertTrue("failed to remove field",
                    userDao.removeProfileField(userRecord.getUid(), username, key, databaseCollection));

            // When not using multithreading the program runs on a single thread
            Thread.sleep(2000);

            future = firestore.collection(databaseCollection).whereEqualTo("uId", userRecord.getUid()).get();
          Map returnedValue = (HashMap) future.get().getDocuments().get(0).getData();
            System.out.println("check: " + returnedValue.get(key));
            Assert.assertNull("failed to retrieve data after update", returnedValue.get(key));




            System.out.println("\n CREATE_ProfileField_Invalid \n");

//        REMINDER uId is never used, remove from method
            Assert.assertEquals("failed to create field_FakeUsername", false,
                    userDao.createUpdateProfileField(userRecord.getUid(), "fakeUsername", key, value, databaseCollection));
        }
        catch(Exception ex) {
            System.out.println("An error occurred [createProfileField_Valid], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void listUsers() {
        System.out.println("listUsers");

        System.out.println("\n listUsers_Valid \n");

        listDocumentSnapshot = userDao.listUsers(databaseCollection);

        Assert.assertEquals("Position 0 returned by listUsers() was not equal to the expected value",
                "JUnit",
                listDocumentSnapshot.get(0).getData().get("username"));

        System.out.println("\n listUsers_Invalid \n");

        Assert.assertNotEquals("Position 0 returned by listUsers() was not equal to the expected value",
                "usernameFalse",
                listDocumentSnapshot.get(0).getData().get("username"));
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
