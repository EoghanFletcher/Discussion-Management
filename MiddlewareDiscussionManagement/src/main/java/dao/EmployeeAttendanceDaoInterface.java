package dao;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Map;

public interface EmployeeAttendanceDaoInterface {

    public DocumentSnapshot addMasterList(String username, String databaseCollection);

    public DocumentSnapshot confirmAttendance(String uId, String username, DocumentSnapshot copy, String databaseCollection);

    public String getCurrentDate();

    public Map createMap(String uId, String username);

    public DocumentSnapshot searchAttendances(List<QueryDocumentSnapshot> documents, String entryName);

    public DocumentSnapshot getListOfPresentAbsentEmployees(String listType, String databaseCollection);

    public DocumentSnapshot getListOfAllEmployees(String databaseCollection);

    public boolean createNode(String username, String title, String message, String listType, String databaseCollection);

    public DocumentSnapshot getNotes(String username, String listType, String databaseCollection);

    public DocumentSnapshot copyMasterList(String databaseCollection);
}
