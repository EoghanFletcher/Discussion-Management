package com.middleware.middlewarediscussionmanagement;

import dao.GroupTaskDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/groupAndTask")
public class GroupController {
    GroupTaskDao groupTaskDao = new GroupTaskDao();

    @PostMapping(path = "createGroup")
    public void createGroup(@RequestBody HashMap data) {
        System.out.println("createGroup");

        System.out.println("data: " + data.entrySet());

        String uIdString = (String) data.get("uId");
        String groupNameString = (String) data.get("groupName");
        String groupDescriptionString = (String) data.get("groupDescription");

        System.out.println("uId: " + uIdString);
        System.out.println("groupName: " + groupNameString);
        System.out.println("groupDescription: " + groupDescriptionString);

        groupTaskDao.createUpdateGroup(uIdString, groupNameString, groupDescriptionString, "Group");
    }
}
