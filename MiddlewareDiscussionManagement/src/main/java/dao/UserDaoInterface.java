package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import javax.swing.text.Document;

public interface UserDaoInterface {
    public FirebaseAuth getAuthenticationInstance();

    public DocumentSnapshot getUserDocument(String uid);

    public UserRecord getUId(String username, FirebaseAuth firebaseAuthInstance);

    public boolean createProfileField(String uId, String key, String value);

    public boolean removeProfileField(String uId, String key);
}
