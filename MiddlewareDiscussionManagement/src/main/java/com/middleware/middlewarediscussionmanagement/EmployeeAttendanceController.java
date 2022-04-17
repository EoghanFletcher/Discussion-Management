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

            documentSnapshot = employeeAttendance.confirmAttendance(uIdString, username, databaseCollection);

            if (documentSnapshot != null) {
                return documentSnapshot.getData();
            }

        }   catch (Exception ex) {
            System.out.println("An exception occurred [getDate], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/presentList")
    public Map presentList(@RequestBody HashMap data) {
        System.out.println("presentList");
        try {
            System.out.println("data: " + data.entrySet());

            System.out.println("*******");
            return employeeAttendance.getListOfPresentEmployees(databaseCollection).getData();

    }   catch (Exception ex) {
        System.out.println("An exception occurred [presentList], ex: " + ex.getMessage());
        ex.printStackTrace();
    }
        return null;
    }

    @PostMapping(path = "/addMasterList")
    public Map addMasterList(@RequestBody HashMap data) {
        System.out.println("addMasterList");
        try {
            System.out.println("data: " + data.entrySet());

            String uIdString = (String) data.get("uId");
            String username = (String) data.get("username");

            System.out.println("*******");
            return employeeAttendance.addMasterList(username, databaseCollection).getData();

        }   catch (Exception ex) {
            System.out.println("An exception occurred [presentList], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }


}
