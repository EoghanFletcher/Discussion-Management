package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import javax.swing.text.Document;

public interface UserDaoInterface {
    public FirebaseAuth getAuthenticationInstance();

    public DocumentSnapshot getUserDocument(String uid, String email);

    public DocumentSnapshot register(String uid, String email, String username);

    public UserRecord getUId(String username, FirebaseAuth firebaseAuthInstance);

    public boolean createUpdateProfileField(String uId, String key, String value, String databaseCollection);

    public boolean removeProfileField(String uId, String key);
}
