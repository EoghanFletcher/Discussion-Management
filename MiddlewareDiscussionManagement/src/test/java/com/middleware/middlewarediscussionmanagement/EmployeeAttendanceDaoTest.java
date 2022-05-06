package com.middleware.middlewarediscussionmanagement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.Dao;
import dao.EmployeeAttendanceDao;
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
    String date = null;

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
            firebaseAuthInstance.createUser(new UserRecord.CreateRequest().setEmail("JUnit@gmail.com2").setPassword("JUnit@gmail.com2"));
            Thread.sleep(2000);
            firebaseAuthInstance.createUser(new UserRecord.CreateRequest().setEmail("JUnit@gmail.com3").setPassword("JUnit@gmail.com3"));
            Thread.sleep(2000);
            userRecord = firebaseAuthInstance.getUserByEmail("junit@gmail.com");
            Thread.sleep(2000);
            userRecord2 = firebaseAuthInstance.getUserByEmail("junit@gmail.com2");
            Thread.sleep(2000);
            userRecord3 = firebaseAuthInstance.getUserByEmail("junit@gmail.com3");
            Thread.sleep(2000);


            System.out.println("userRecord.getUid(): " + userRecord.getUid());
            uId = userRecord.getUid();
            System.out.println("uId: " + uId);

            firestore = Dao.initialiseFirestore();

            collectionReference = firestore.collection(databaseCollection);
        } catch (Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }

//    @Test
//    public void getDate() {
//        System.out.println("getDate");
//
//        System.out.println("here: " + LocalDate.now().toString());
//        System.out.println("here2: " + employeeAttendanceDao.getCurrentDate().toString());
//
//        Assert.assertTrue(LocalDate.now().toString().contains(employeeAttendanceDao.getCurrentDate().toString()));
//    }

    @Test
    public void attendanceFunctionality() {
        System.out.println("attendanceFunctionality");

        try {
        System.out.println("attendanceFunctionality");

        System.out.println("==========");
        System.out.println("addMasterList - None in list");
        System.out.println("==========");

        String message = null;
        String title = null;
        message = "Test was successful";
        title = "Test message";
        documentSnapshot = employeeAttendanceDao.addMasterList(username, databaseCollection);
        System.out.println("documentSnapshot: " + documentSnapshot.getData());
        Assert.assertTrue(documentSnapshot.getData().size() == 1);

        System.out.println("==========");
        System.out.println("addMasterList - One in list");
        System.out.println("==========");

        documentSnapshot = employeeAttendanceDao.addMasterList(username + "2", databaseCollection);
        System.out.println("size 3 : " + documentSnapshot.getData().entrySet());


        System.out.println("==========");
        System.out.println("copyMasterList");
        System.out.println("==========");

        System.out.println("first: " + employeeAttendanceDao.copyMasterList(databaseCollection).getData());
        System.out.println("second: " + documentSnapshot.getData());
        Thread.sleep(5000);
        Assert.assertTrue(employeeAttendanceDao.copyMasterList(databaseCollection).getData().equals(documentSnapshot.getData()));
        System.out.println("copy master list: " + employeeAttendanceDao.copyMasterList(databaseCollection).getData());

        System.out.println("==========");
        System.out.println("confirmAttendance");
        System.out.println("==========");

        Thread.sleep(5000);
        System.out.println("confirmAttendance: " + employeeAttendanceDao.confirmAttendance(userRecord.getUid(),
                                                                username,
                                                                employeeAttendanceDao.copyMasterList(databaseCollection),
                                                                databaseCollection));

            System.out.println("==========");
            System.out.println("searchAttendances");
            System.out.println("==========");

            date = employeeAttendanceDao.getCurrentDate();
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();
            documentSnapshot = employeeAttendanceDao.searchAttendances(documents, date);

            System.out.println("==========");
            System.out.println("getListOfPresentAbsentEmployees - present");
            System.out.println("==========");

            documentSnapshot = employeeAttendanceDao.getListOfPresentAbsentEmployees("Present", databaseCollection);
            Assert.assertTrue(documentSnapshot.getData().containsKey(username));

            System.out.println("==========");
            System.out.println("searchAttendances - absent");
            System.out.println("==========");

            documentSnapshot = employeeAttendanceDao.getListOfPresentAbsentEmployees("Absent", databaseCollection);
            Assert.assertTrue(documentSnapshot.getData().containsKey(username + "2"));

            System.out.println("==========");
            System.out.println("getListOfAllEmployees");
            System.out.println("==========");

            documentSnapshot = employeeAttendanceDao.getListOfAllEmployees(databaseCollection);
            Assert.assertTrue(documentSnapshot.getData().containsKey(username));
            Assert.assertTrue(documentSnapshot.getData().containsKey(username + "2"));

            System.out.println("==========");
            System.out.println("createNode - present");
            System.out.println("==========");

            Assert.assertTrue(employeeAttendanceDao.createNode(username, title, message, "Present", databaseCollection));
            Assert.assertTrue(employeeAttendanceDao.createNode(username, title, message, "Absent", databaseCollection));

            System.out.println("==========");
            System.out.println("getNotes");
            System.out.println("==========");

            documentSnapshot = employeeAttendanceDao.getNotes(username, "Present", databaseCollection);
            Assert.assertTrue(documentSnapshot.getData().containsKey(username));

            documentSnapshot = employeeAttendanceDao.getNotes(username, "Absent", databaseCollection);
            Assert.assertTrue(documentSnapshot.getData().containsKey(username + "2"));

            System.out.println("==========");
            System.out.println("createMap");
            System.out.println("==========");

            Map createdMap = null;

            createdMap = employeeAttendanceDao.createMap(userRecord.getUid(), username);
            Assert.assertTrue(createdMap.containsKey(username));
        } catch (Exception ex) {
            System.out.println("An error occurred [attendanceFunctionality], ex: " + ex);
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
            firebaseAuthInstance.deleteUser(userRecord2.getUid());
            firebaseAuthInstance.deleteUser(userRecord3.getUid());
        }
        catch(Exception ex) {
            System.out.println("An error occurred [removeTestData], ex: " + ex);
            ex.printStackTrace();
        }
    }

}
