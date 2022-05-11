package dao;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.util.List;

public interface UserDaoInterface {
    public FirebaseAuth getAuthenticationInstance();

    public DocumentSnapshot getUserDocumentByUId(String uid, String email, String databaseCollection);

    public DocumentSnapshot getUserDocumentByEmail(String email, String databaseCollection);

    public DocumentSnapshot register(String uid, String email, String username, String databaseCollection);

    public UserRecord getUId(String username, FirebaseAuth firebaseAuthInstance);

    public boolean createUpdateProfileField(String uId, String username, String key, String value, String databaseCollection);

    public boolean removeProfileField(String uId, String username, String key, String databaseCollection);

    public List<DocumentSnapshot> listUsers(String databaseCollection);

    public String testForConnectivity();
}
