package dao;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Map;

public interface EmployeeAttendanceDaoInterface {

    public DocumentSnapshot addMasterList(String username, String databaseCollection);

    public DocumentSnapshot confirmAttendance(String uId, String username, String databaseCollection);

    public String getCurrentDate();

    public Map createMap(String uId, String username);

    public DocumentSnapshot searchAttendances(List<QueryDocumentSnapshot> documents, String entryName);

    public DocumentSnapshot getListOfPresentEmployees(String databaseCollection);

    public boolean createNode(String date, String username, String message, String databaseCollection);
}
