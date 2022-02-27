package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.util.Date;
import java.util.List;

public interface GroupTaskDaoInterface {
    public boolean createUpdateGroup(String uId, String email, String groupName, String groupDescription, String databaseCollection);

    public List<DocumentSnapshot> listGroups(String uId, String email, String databaseCollection);

    public boolean createTask(String username, String groupName, String taskName, String taskDescription, String taskType,  /* Date */  String dateTimeOfEvent, String databaseCollection);

    public boolean deactivateTask(String groupName, String taskName);

    public ApiFuture<WriteResult> addGroupMember(String email, Firestore firestore, String groupName);

    public List<DocumentSnapshot> listTasks(String groupName, String databaseCollection);

    public ApiFuture<WriteResult> assignAdminPrivileges(Firestore firestore, String groupName, String username);

    public DocumentSnapshot listEvents(String databaseCollection);
}
