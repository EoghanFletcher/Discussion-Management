package com.middleware.middlewarediscussionmanagement;

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
    String databaseCollection = "employeeAttendance";

    @PostMapping(path = "/confirmAttendance")
    public Map confirmAttendance(@RequestBody HashMap data) {
        System.out.println("confirmAttendance");
        try {
            System.out.println("data: " + data.entrySet());

            String uIdString = (String) data.get("uId");
            String uIdUsername = (String) data.get("username");

            return employeeAttendance.confirmAttendance(uIdString, uIdString, databaseCollection).getData();

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

            return employeeAttendance.getListOfPresentEmployees(databaseCollection).getData();

    }   catch (Exception ex) {
        System.out.println("An exception occurred [presentList], ex: " + ex.getMessage());
        ex.printStackTrace();
    }
        return null;
    }


}
