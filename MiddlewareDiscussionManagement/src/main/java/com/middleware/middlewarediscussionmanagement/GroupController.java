package com.middleware.middlewarediscussionmanagement;

import com.google.cloud.firestore.DocumentSnapshot;
import dao.GroupTaskDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/groupAndTask")
public class GroupController {
    GroupTaskDao groupTaskDao = new GroupTaskDao();

    @PostMapping(path = "/createGroup")
    public void createGroup(@RequestBody HashMap data) {
        System.out.println("createGroup");

        String uIdString = (String) data.get("uId");
        String emailString = (String) data.get("email");
        String groupNameString = (String) data.get("groupName");
        String groupDescriptionString = (String) data.get("groupDescription");

        groupTaskDao.createUpdateGroup(uIdString, emailString, groupNameString, groupDescriptionString, "Group");
    }

    @PostMapping(path = "/listGroups")
    public List<DocumentSnapshot> listGroups(@RequestBody HashMap data) {
        System.out.println("listGroups");

        List<DocumentSnapshot> listDocumentSnapshot = null;
        List documentListData = null;

        String uIdString = (String) data.get("uId");
        String emailString = (String) data.get("email");

        listDocumentSnapshot = groupTaskDao.listGroups(uIdString, emailString, "Group");

        documentListData = new ArrayList();
        for (DocumentSnapshot document : listDocumentSnapshot) { documentListData.add(document.getData()); }
        return documentListData;
    }

    @PostMapping(path = "/createTask")
    public boolean createTask(@RequestBody HashMap data) {
        System.out.println("createTask");

        System.out.println("data: " + data.entrySet());

        String uIdString = (String) data.get("uId");
        String groupNameString = (String) data.get("groupName");
        String taskNameString = (String) data.get("taskName");
        String taskDescriptionString = (String) data.get("taskDescription");
        String taskTypeString = (String) data.get("taskType");
        String dateTimeOfEventString = (String) data.get("dateTimeOfEvent");

        System.out.println("uIdString: " + uIdString);
        System.out.println("taskNameString: " + taskNameString);
        System.out.println("taskDescriptionString: " + taskDescriptionString);
        System.out.println("taskTypeString: " + taskTypeString);
        System.out.println("dateTimeOfEventString: " + dateTimeOfEventString);

        boolean response = groupTaskDao.createTask(uIdString, groupNameString, taskNameString, taskDescriptionString, taskTypeString, dateTimeOfEventString, "Group");

        System.out.println("response: " + response);

        return false;
    }
}
