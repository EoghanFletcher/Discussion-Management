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

import java.util.HashMap;
import java.util.List;

public class EmployeeAttendanceDaoTest {

    private static EmployeeAttendanceDao employeeAttendanceDao = null;
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
            userRecord = firebaseAuthInstance.getUserByEmail("JUnit@gmail.com");

            System.out.println("userRecord: " + userRecord.getUid());

            uId = userRecord.getUid();

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

        System.out.println(employeeAttendanceDao.getDate());
    }

    @Test
    public void confirmAttendance() {
        System.out.println("confirmAttendance");

        System.out.println("uId: " + uId);
        System.out.println("username: " + username);
        System.out.println("databaseCollection: " + databaseCollection);

        try {
//        System.out.println("employeeAttendanceDao.confirmAttendance(username, databaseCollection): " + employeeAttendanceDao.confirmAttendance(uId, username, databaseCollection));
            employeeAttendanceDao.confirmAttendance(uId, username, databaseCollection);
            employeeAttendanceDao.confirmAttendance("1234", "test", databaseCollection);
//            wait(2000);
//            employeeAttendanceDao.confirmAttendance("1234", "test", databaseCollection);
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @AfterClass
    public static void removeTestData() {
        System.out.println("removeTestData");
        try {
            documentData = new HashMap();
//            firestore.recursiveDelete(collectionReference);
            firebaseAuthInstance.deleteUser(userRecord.getUid());
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

}
