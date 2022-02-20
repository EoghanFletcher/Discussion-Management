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

        System.out.println("data: " + data.entrySet());

        String uIdString = (String) data.get("uId");
        String emailString = (String) data.get("email");
        String groupNameString = (String) data.get("groupName");
        String groupDescriptionString = (String) data.get("groupDescription");

        System.out.println("uId: " + uIdString);
        System.out.println("email: " + emailString);
        System.out.println("groupName: " + groupNameString);
        System.out.println("groupDescription: " + groupDescriptionString);

        groupTaskDao.createUpdateGroup(uIdString, emailString, groupNameString, groupDescriptionString, "Group");
    }

    @PostMapping(path = "/listGroups")
    public List<DocumentSnapshot> listGroups(@RequestBody HashMap data) {
        System.out.println("listGroups");

        List<DocumentSnapshot> listDocumentSnapshot = null;

        System.out.println("data: " + data.entrySet());

        String uIdString = (String) data.get("uId");
        String emailString = (String) data.get("email");

        listDocumentSnapshot = groupTaskDao.listGroups(uIdString, emailString, "Group");

        List documentListData = new ArrayList();

        for (DocumentSnapshot document : listDocumentSnapshot) {

            documentListData.add(document.getData());

        }
        return documentListData;
    }
}
