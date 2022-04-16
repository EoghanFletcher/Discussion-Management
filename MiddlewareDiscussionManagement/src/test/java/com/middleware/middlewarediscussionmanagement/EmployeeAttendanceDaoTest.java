package com.middleware.middlewarediscussionmanagement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.Dao;
import dao.EmployeeAttendanceDao;
import dao.UserDao;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAttendanceDaoTest {

    private static EmployeeAttendanceDao employeeAttendanceDao = null;
    private static FirebaseAuth firebaseAuthInstance = null;
    private static Firestore firestore = null;
    private static HashMap documentData = null;
    private static UserRecord userRecord = null;
    private static UserRecord userRecord2 = null;
    private static UserRecord userRecord3 = null;
    private static String uId = null;
    private static boolean result;
    private static DocumentSnapshot documentSnapshot = null;
    private static ApiFuture<QuerySnapshot> future = null;
    private static List<QueryDocumentSnapshot> documents = null;
    private static List<DocumentSnapshot> listDocumentSnapshot = null;
    private static CollectionReference collectionReference = null;
    private static String databaseCollection = "EmployeeAttendanceTest";
    private static String username = "JUnit";
    String key = "JUnit";
    String value = "UserTest";

    @BeforeClass
    public static void setupData() {
        System.out.println("setupData");
        Dao.initialiseFirebaseConnection();

        try {
            employeeAttendanceDao = new EmployeeAttendanceDao();

            documentData = new HashMap();
            firebaseAuthInstance = FirebaseAuth.getInstance();

            firebaseAuthInstance.createUser(new UserRecord.CreateRequest().setEmail("JUnit@gmail.com").setPassword("JUnit@gmail.com"));
            Thread.sleep(2000);
            userRecord = firebaseAuthInstance.getUserByEmail("junit@gmail.com");
            Thread.sleep(2000);
            userRecord2 = firebaseAuthInstance.getUserByEmail("eoghanfletcher1999@gmail.com");
            Thread.sleep(2000);
            userRecord3 = firebaseAuthInstance.getUserByEmail("eoghanfletcher99@gmail.com");
            Thread.sleep(2000);


            System.out.println("userRecord.getUid(): " + userRecord.getUid());
            uId = userRecord.getUid();
            System.out.println("uId: " + uId);

            firestore = Dao.initialiseFirestore();


//            documentData.put("uId", userRecord.getUid());
//            documentData.put("email", userRecord.getEmail());
//            documentData.put("username", username);

            collectionReference = firestore.collection(databaseCollection);
//            collectionReference.document().set(documentData);
        } catch (Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void getDate() {
        System.out.println("getDate");

        Assert.assertSame(employeeAttendanceDao.getCurrentDate(), LocalDate.now());
    }

    @Test
    public void confirmAttendance() {
        System.out.println("confirmAttendance");

        System.out.println("uId: " + uId);
        System.out.println("username: " + username);
        System.out.println("databaseCollection: " + databaseCollection);

        try {
            Assert.assertNotNull(employeeAttendanceDao.confirmAttendance(uId, username, databaseCollection));
            System.out.println("userRecord2.getUid(): " + userRecord2.getUid());
            Assert.assertNotNull(employeeAttendanceDao.confirmAttendance(userRecord2.getUid(), "userRecord2", databaseCollection));
            Assert.assertNotNull(employeeAttendanceDao.confirmAttendance(userRecord3.getUid(), "userRecord3", databaseCollection));
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void presentList() {
        System.out.println("presentList");

        try {
            Map hashMap = employeeAttendanceDao.getListOfPresentEmployees(databaseCollection).getData();

            Assert.assertTrue(hashMap.containsKey("date"));
            Assert.assertTrue(hashMap.containsKey("userRecord2"));
            Assert.assertTrue(hashMap.containsKey("userRecord3"));
            Assert.assertTrue(hashMap.containsKey("JUnit"));

            System.out.println("EntrySet: " + employeeAttendanceDao.getListOfPresentEmployees(databaseCollection).getData().entrySet());
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void createNode() {
        System.out.println("createNode");

        String message = null;
        try {
             message = "Test was successful";
            employeeAttendanceDao.createNode(employeeAttendanceDao.getCurrentDate(), username, message, databaseCollection);
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

//    @AfterClass
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
