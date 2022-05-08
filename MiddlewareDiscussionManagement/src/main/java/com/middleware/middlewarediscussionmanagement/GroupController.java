package com.middleware.middlewarediscussionmanagement;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import dao.Dao;
import dao.GroupTaskDao;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/groupAndTask")
public class GroupController {
    GroupTaskDao groupTaskDao = new GroupTaskDao();
    String databaseCollection = "Group";

    @PostMapping(path = "/createGroup")
    public void createGroup(@RequestBody HashMap data) {
        System.out.println("createGroup");


        try {
        String uIdString = (String) data.get("uId");
        String usernameString = (String) data.get("username");
        String groupNameString = (String) data.get("groupName");
        String groupDescriptionString = (String) data.get("groupDescription");

        groupTaskDao.createUpdateGroup(uIdString, usernameString, groupNameString, groupDescriptionString, databaseCollection);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [deleteCredential], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @PostMapping(path = "/listGroups")
    public List<DocumentSnapshot> listGroups(@RequestBody HashMap data) {
        System.out.println("listGroups");


        List<DocumentSnapshot> listDocumentSnapshot = null;
        List documentListData = null;

        String uIdString = (String) data.get("uId");
        String username = (String) data.get("username");

        try {
            listDocumentSnapshot = groupTaskDao.listGroups(uIdString, username, databaseCollection);

            documentListData = new ArrayList();
            for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
            }
        catch (Exception ex) {
            System.out.println("An exception occurred [deleteCredential], ex: " + ex);
            ex.printStackTrace();
        }
        return documentListData;
    }

    @PostMapping(path = "/createTask")
    public boolean createTask(@RequestBody HashMap data) {
        System.out.println("createTask_Controller");
        boolean response = false;

        try {
        String usernameString = (String) data.get("username");
        String groupNameString = (String) data.get("groupName");
        String taskNameString = (String) data.get("taskName");
        String taskDescriptionString = (String) data.get("taskDescription");
        String taskTypeString = (String) data.get("taskType");
//        Date dateTimeOfEvent = (Date) data.get("dateTimeOfEvent");
        String dateTimeOfEvent = (String) data.get("dateTimeOfEvent");

        response = groupTaskDao.createTask(usernameString, groupNameString, taskNameString, taskDescriptionString, taskTypeString, dateTimeOfEvent, databaseCollection);


        }
        catch (Exception ex) {
            System.out.println("An exception occurred [deleteCredential], ex: " + ex);
            ex.printStackTrace();
        }
        return response;
    }

    @PostMapping(path = "deactivateTask")
    public void deactivateTask(@RequestBody HashMap data) {
        System.out.println("deactivateTask");

        boolean deleted = false;

        try {

        String groupName = (String) data.get("groupName");
        String taskName = (String) data.get("taskName");

        groupTaskDao.deactivateTask(groupName, taskName, databaseCollection);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [deleteCredential], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @PostMapping(path = "/listTasks")
    public List<DocumentSnapshot> listTasks(@RequestBody HashMap data) {
        System.out.println("listTasks");

        List<DocumentSnapshot> listDocumentSnapshot = null;
        List documentListData = null;

        String groupNameString = (String) data.get("groupName");

        listDocumentSnapshot = groupTaskDao.listTasks(groupNameString, databaseCollection);

        documentListData = new ArrayList();
        for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
        return documentListData;
    }

    @GetMapping(path = "/listEvents")
    public Map getEvents() {
        System.out.println("listEvents");
        String eventCollection = "Event";

        DocumentSnapshot documentSnapshot = null;

        documentSnapshot = groupTaskDao.listEvents(eventCollection);

        return documentSnapshot.getData();
    }

    @PostMapping(path = "requestToLeaveGroup")
    public boolean requestToLeaveGroup(@RequestBody HashMap data) {
        System.out.println("requestToLeaveGroup");

        boolean result = false;

        String groupNameString = (String) data.get("groupName");
        String usernameString = (String) data.get("username");

        try {

            result = groupTaskDao.requestToLeaveGroup(groupNameString, usernameString, databaseCollection);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [requestToLeaveGroup], ex: " + ex);
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping(path = "leaveGroupVerdict")
    public boolean leaveGroupVerdict(@RequestBody HashMap data) {
        System.out.println("requestToLeaveGroup");

        boolean result = false;

        try {

            System.out.println("data: " + data.entrySet());

            String usernameString = (String) data.get("username");
            String groupNameString = (String) data.get("groupName");
            String verdictString = (String) data.get("verdict");

            if (verdictString.equals("grant")) {
                result = groupTaskDao.grantRequestToLeave(groupNameString, usernameString, databaseCollection);
            }
            else {
                // Deny
                System.out.println("deny");
                result = groupTaskDao.removeRequestToLeave(groupNameString, usernameString, databaseCollection);
            }

            result = groupTaskDao.removeRequestToLeave(groupNameString, usernameString, databaseCollection);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [requestToLeaveGroup], ex: " + ex);
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping(path = "groupAddMember")
    public boolean groupAddMember(@RequestBody HashMap data) {
        System.out.println("requestToLeaveGroup");

        boolean result = false;

        try {

            Firestore firestore = Dao.initialiseFirestore();

            System.out.println("data: " + data.entrySet());

            String usernameString = (String) data.get("username");
            String groupNameString = (String) data.get("groupName");

                /* result = */ groupTaskDao.addGroupMember(usernameString, firestore, groupNameString, databaseCollection);

        }
        catch (Exception ex) {
            System.out.println("An exception occurred [groupAddMember], ex: " + ex);
            ex.printStackTrace();
        }
        return result;
    }
}
