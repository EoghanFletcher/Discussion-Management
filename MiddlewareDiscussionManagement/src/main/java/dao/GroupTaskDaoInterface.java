package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import java.util.List;

public interface GroupTaskDaoInterface {
    public boolean createUpdateGroup(String uId, String email, String groupName, String groupDescription, String databaseCollection);

    public List<DocumentSnapshot> listGroups(String uId, String email, String databaseCollection);

    public boolean createTask(String uId, String groupName, String taskName, String taskDescription, String taskType, String dateTimeOfEvent, String databaseCollection);

    public ApiFuture<WriteResult> addGroupMember(String email, Firestore firestore, String groupName);

    public List<DocumentSnapshot> listTasks(String groupName, String databaseCollection);
}
