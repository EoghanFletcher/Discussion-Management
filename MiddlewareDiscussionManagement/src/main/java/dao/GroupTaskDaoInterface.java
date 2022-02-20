package dao;

import java.util.List;

public interface GroupTaskDaoInterface {
    public boolean createUpdateGroup(String uId, String email, String groupName, String groupDescription, String databaseCollection);
    public List listGroups(String uId, String email, String databaseCollection);
}
