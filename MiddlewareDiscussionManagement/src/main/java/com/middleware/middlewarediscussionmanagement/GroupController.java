package com.middleware.middlewarediscussionmanagement;

import com.google.cloud.firestore.DocumentSnapshot;
import dao.GroupTaskDao;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/groupAndTask")
public class GroupController {
    GroupTaskDao groupTaskDao = new GroupTaskDao();

    @PostMapping(path = "/createGroup")
    public void createGroup(@RequestBody HashMap data) {
        System.out.println("createGroup");

        System.out.println("data: " + data.entrySet());

        String uIdString = (String) data.get("uId");
        String usernameString = (String) data.get("username");
        String groupNameString = (String) data.get("groupName");
        String groupDescriptionString = (String) data.get("groupDescription");

        groupTaskDao.createUpdateGroup(uIdString, usernameString, groupNameString, groupDescriptionString, "Group");
    }

    @PostMapping(path = "/listGroups")
    public List<DocumentSnapshot> listGroups(@RequestBody HashMap data) {
        System.out.println("listGroups");

        List<DocumentSnapshot> listDocumentSnapshot = null;
        List documentListData = null;

        String uIdString = (String) data.get("uId");
        String username = (String) data.get("username");

        listDocumentSnapshot = groupTaskDao.listGroups(uIdString, username, "Group");

        documentListData = new ArrayList();
        for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
        return documentListData;
    }

    @PostMapping(path = "/createTask")
    public boolean createTask(@RequestBody HashMap data) {
        System.out.println("createTask_Controller");

        String usernameString = (String) data.get("username");
        String groupNameString = (String) data.get("groupName");
        String taskNameString = (String) data.get("taskName");
        String taskDescriptionString = (String) data.get("taskDescription");
        String taskTypeString = (String) data.get("taskType");
//        Date dateTimeOfEvent = (Date) data.get("dateTimeOfEvent");
        String dateTimeOfEvent = (String) data.get("dateTimeOfEvent");

        boolean response = groupTaskDao.createTask(usernameString, groupNameString, taskNameString, taskDescriptionString, taskTypeString, dateTimeOfEvent, "Group");

        System.out.println("response: " + response);

        return response;
    }

    @PostMapping(path = "deactivateTask")
    public void deactivateTask(@RequestBody HashMap data) {
        System.out.println("deactivateTask");

        boolean deleted = false;

        String groupName = (String) data.get("groupName");
        String taskName = (String) data.get("taskName");

        groupTaskDao.deactivateTask(groupName, taskName);
    }

    @PostMapping(path = "/listTasks")
    public List<DocumentSnapshot> listTasks(@RequestBody HashMap data) {
        System.out.println("listTasks");

        List<DocumentSnapshot> listDocumentSnapshot = null;
        List documentListData = null;

        String groupNameString = (String) data.get("groupName");

        listDocumentSnapshot = groupTaskDao.listTasks(groupNameString, "Group");

        documentListData = new ArrayList();
        for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
        return documentListData;
    }

    @GetMapping(path = "/listEvents")
    public Map getEvents() {
        System.out.println("listEvents");

        DocumentSnapshot documentSnapshot = null;

        documentSnapshot = groupTaskDao.listEvents("Event");

        return documentSnapshot.getData();
    }


}
