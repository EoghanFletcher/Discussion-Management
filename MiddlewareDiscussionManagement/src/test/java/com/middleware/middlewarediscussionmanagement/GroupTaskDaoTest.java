package com.middleware.middlewarediscussionmanagement;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import dao.Dao;
import dao.GroupTaskDao;
import dao.UserDao;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

public class GroupTaskDaoTest {
    private static GroupTaskDao groupTaskDao = null;
    private static FirebaseAuth firebaseAuthInstance = null;
    private static Firestore firestore = null;
    private static HashMap documentData = null;
    private static UserRecord userRecord = null;
    private static DocumentSnapshot documentSnapshot = null;
    private static ApiFuture<QuerySnapshot> future = null;
    private static CollectionReference collectionReference = null;
    private static String databaseCollection = "GroupTest";
    private static String username = "JUnit";
    String groupName = "testGroup";

    @BeforeClass
    public static void setupData() {
        System.out.println("setupData");
        Dao.initialiseFirebaseConnection();

        try {
            groupTaskDao = new GroupTaskDao();
            documentData = new HashMap();
            firebaseAuthInstance = FirebaseAuth.getInstance();

            firebaseAuthInstance.createUser(new UserRecord.CreateRequest().setEmail("JUnit@gmail.com").setPassword("JUnit@gmail.com"));
            Thread.sleep(2000);
            userRecord = firebaseAuthInstance.getUserByEmail("JUnit@gmail.com");

            firestore = Dao.initialiseFirestore();

            documentData.put("uId", userRecord.getUid());
            documentData.put("email", userRecord.getEmail());
            documentData.put("username", username);

            System.out.println("entryset: " + documentData.entrySet());

            collectionReference = firestore.collection(databaseCollection);
        }
        catch(Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @Test
    public void groupFunctionality() {
        System.out.println("groupFunctionality");

        System.out.println("==========");
        System.out.println("createUpdateGroup - Create");
        System.out.println("==========");

        Assert.assertTrue(groupTaskDao.createUpdateGroup(documentData.get("uId").toString(),
                                                        username,
                                                        groupName,
                                            "testGroupDescription",
                                                        databaseCollection));

        System.out.println("==========");
        System.out.println("createUpdateGroup - Update");
        System.out.println("==========");

        Assert.assertTrue(groupTaskDao.createUpdateGroup(documentData.get("uId").toString(),
                username,
                groupName,
                "testGroupDescriptionUpdated",
                databaseCollection));

        System.out.println("==========");
        System.out.println("createTask");
        System.out.println("==========");

        Assert.assertTrue(groupTaskDao.createTask(documentData.get("username").toString(),
                                                groupName,
                                        "testTask",
                                    "Meeting for all IT department staff members tomorrow at 12:00",
                                        "Meeting",
                                        "2022-07-30T14:59:00+01:00",
                                                databaseCollection));

        System.out.println("==========");
        System.out.println("addGroupMember");
        System.out.println("==========");

        firestore = Dao.initialiseFirestore();

        Assert.assertTrue(groupTaskDao.addGroupMember("addGroupMember",
                                                    firestore,
                                                    groupName,
                                                    databaseCollection));

        System.out.println("==========");
        System.out.println("assignAdminPrivileges");
        System.out.println("==========");


        Assert.assertTrue((groupTaskDao.assignAdminPrivileges(firestore, groupName, "addGroupMember", databaseCollection)));

        System.out.println("==========");
        System.out.println("listTasks");
        System.out.println("==========");

        Assert.assertEquals(groupTaskDao.listTasks(groupName, databaseCollection).get(0).getData().get("createdBy"), username);
        Assert.assertEquals(groupTaskDao.listTasks(groupName, databaseCollection).get(0).getData().get("taskName"), "testTask");
        Assert.assertEquals(groupTaskDao.listTasks(groupName, databaseCollection).get(0).getData().get("taskDescription"), "Meeting for all IT department staff members tomorrow at 12:00");

        System.out.println("==========");
        System.out.println("requestToLeaveGroup - refused");
        System.out.println("==========");

        String request = "addGroupMember";

        Assert.assertTrue(groupTaskDao.requestToLeaveGroup(groupName, request, databaseCollection));
        Assert.assertTrue(groupTaskDao.removeRequestToLeave(groupName, request, databaseCollection));

        System.out.println("==========");
        System.out.println("requestToLeaveGroup - granted");
        System.out.println("==========");

        Assert.assertTrue(groupTaskDao.requestToLeaveGroup(groupName, request, databaseCollection));
        Assert.assertTrue(groupTaskDao.grantRequestToLeave(groupName, request, databaseCollection));

        System.out.println("==========");
        System.out.println("deactivateTask");
        System.out.println("==========");

        Assert.assertTrue(groupTaskDao.deactivateTask(groupName, "testTask", databaseCollection));
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
