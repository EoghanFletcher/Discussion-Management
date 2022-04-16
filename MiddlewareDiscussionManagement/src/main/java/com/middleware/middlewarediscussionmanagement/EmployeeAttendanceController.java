package com.middleware.middlewarediscussionmanagement;

import dao.EmployeeAttendanceDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/employeeAttendance")
public class EmployeeAttendanceController {
    EmployeeAttendanceDao employeeAttendance = new EmployeeAttendanceDao();


}
