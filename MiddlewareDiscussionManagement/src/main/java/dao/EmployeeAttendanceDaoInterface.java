package dao;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.List;

public interface EmployeeAttendanceDaoInterface {

    public DocumentSnapshot confirmAttendance(String uId, String username, String databaseCollection);

    public String getCurrentDate();

    public DocumentSnapshot getListOfPresentEmployees(String databaseCollection);

    public boolean createNode(String date, String username, String message, String databaseCollection);
}
