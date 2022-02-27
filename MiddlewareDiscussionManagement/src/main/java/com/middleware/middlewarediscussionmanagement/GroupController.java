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

        System.out.println("uId: " + uIdString);
        System.out.println("usernameString: " + usernameString);
        System.out.println("groupNameString: " + groupNameString);
        System.out.println("groupDescriptionString: " + groupDescriptionString);

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

        System.out.println("data: " + data.entrySet());

        String usernameString = (String) data.get("username");
        String groupNameString = (String) data.get("groupName");
        String taskNameString = (String) data.get("taskName");
        String taskDescriptionString = (String) data.get("taskDescription");
        String taskTypeString = (String) data.get("taskType");
//        Date dateTimeOfEvent = (Date) data.get("dateTimeOfEvent");
        String dateTimeOfEvent = (String) data.get("dateTimeOfEvent");

        System.out.println("usernameString: " + usernameString);
        System.out.println("groupNameString: " + groupNameString);
        System.out.println("taskNameString: " + taskNameString);
        System.out.println("taskDescriptionString: " + taskDescriptionString);
        System.out.println("taskTypeString: " + taskTypeString);
        System.out.println("dateTimeOfEvent: " + dateTimeOfEvent);

        boolean response = groupTaskDao.createTask(usernameString, groupNameString, taskNameString, taskDescriptionString, taskTypeString, dateTimeOfEvent, "Group");

        System.out.println("response: " + response);

        return response;
    }

    @PostMapping(path = "/listTasks")
    public List<DocumentSnapshot> listTasks(@RequestBody HashMap data) {
        System.out.println("listTasks");

        List<DocumentSnapshot> listDocumentSnapshot = null;
        List documentListData = null;


        System.out.println("data: " + data.entrySet());

        String groupNameString = (String) data.get("groupName");

        System.out.println("Group name: " + groupNameString);

        listDocumentSnapshot = groupTaskDao.listTasks(groupNameString, "Group");

        documentListData = new ArrayList();
        for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
        return documentListData;
    }

    @GetMapping(path = "/listEvents")
    public Map getEvents() {
        System.out.println("listEvents");

        DocumentSnapshot documentSnapshot = null;
        List documentListData = null;



        documentSnapshot = groupTaskDao.listEvents("Event");

        System.out.println("documentSnapshot: " + documentSnapshot.getData());

        return documentSnapshot.getData();
    }
}
