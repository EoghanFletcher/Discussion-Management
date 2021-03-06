package com.middleware.middlewarediscussionmanagement;

import com.google.cloud.firestore.DocumentSnapshot;
import dao.EmployeeAttendanceDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/employeeAttendance")
public class EmployeeAttendanceController {
    EmployeeAttendanceDao employeeAttendance = new EmployeeAttendanceDao();
    String databaseCollection = "EmployeeAttendance";

    @PostMapping(path = "/confirmAttendance")
    public Map confirmAttendance(@RequestBody HashMap data) {
        System.out.println("confirmAttendance");
        DocumentSnapshot documentSnapshot = null;
        try {
            System.out.println("data: " + data.entrySet());

            String uIdString = (String) data.get("uId");
            String username = (String) data.get("username");

            documentSnapshot = employeeAttendance.confirmAttendance(uIdString, username,
                                                                    this.employeeAttendance.copyMasterList(databaseCollection), databaseCollection);

            if (documentSnapshot != null) {
                return documentSnapshot.getData();
            }

        }   catch (Exception ex) {
            System.out.println("An exception occurred [getDate], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/presentAbsentList")
    public Map presentAbsentList(@RequestBody HashMap data) {
        System.out.println("presentList");
        try {
            System.out.println("data: " + data.entrySet());

            String listType = (String) data.get("listType");

            System.out.println("*******");
            return employeeAttendance.getListOfPresentAbsentEmployees(listType, databaseCollection).getData();

    }   catch (Exception ex) {
        System.out.println("An exception occurred [presentList], ex: " + ex.getMessage());
        ex.printStackTrace();
    }
        return null;
    }

    @PostMapping(path = "/listAllEmployees")
    public Map listAllEmployees(@RequestBody HashMap data) {
        System.out.println("listAllEmployees");
        try {
            System.out.println("data: " + data.entrySet());

            System.out.println("*******");
            return employeeAttendance.getListOfAllEmployees(databaseCollection).getData();

        }   catch (Exception ex) {
            System.out.println("An exception occurred [listAllEmployees], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/addMasterList")
    public Map addMasterList(@RequestBody HashMap data) {
        System.out.println("addMasterList");

        DocumentSnapshot documentSnapshot = null;

        try {
            System.out.println("data: " + data.entrySet());

            String uIdString = (String) data.get("uId");
            String username = (String) data.get("username");

            System.out.println("*******");
            documentSnapshot = employeeAttendance.addMasterList(username, databaseCollection);

            if (documentSnapshot != null) {
                return documentSnapshot.getData();
            }

        }   catch (Exception ex) {
            System.out.println("An exception occurred [presentList], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/createNote")
    public Boolean createNote(@RequestBody HashMap data) {
        System.out.println("createNote");

        DocumentSnapshot documentSnapshot = null;
        boolean result = false;

        try {
            System.out.println("data: " + data.entrySet());

            String username = (String) data.get("username");
            String title = (String) data.get("title");
            String message = (String) data.get("message");

            System.out.println("data: " + data.entrySet());

            System.out.println("*******");
            result = employeeAttendance.createNode(username, title, message, "Present", databaseCollection);

        }   catch (Exception ex) {
            System.out.println("An exception occurred [createNote], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    @PostMapping(path = "/getNotes")
    public Map getNotes(@RequestBody HashMap data) {
        System.out.println("getNotes");

        DocumentSnapshot documentSnapshot = null;

        try {
            System.out.println("data: " + data.entrySet());

            String username = (String) data.get("username");

            System.out.println("data: " + data.entrySet());

            System.out.println("*******");
            documentSnapshot = employeeAttendance.getNotes(username, "Present", databaseCollection);

            if (documentSnapshot != null) {
                return documentSnapshot.getData();
            }

        }   catch (Exception ex) {
            System.out.println("An exception occurred [getNotes], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }


}
