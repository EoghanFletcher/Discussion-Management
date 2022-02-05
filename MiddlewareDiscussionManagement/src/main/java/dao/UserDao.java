package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.util.HashMap;
import java.util.List;

public class UserDao implements UserDaoInterface {

    @Override
    public FirebaseAuth getAuthenticationInstance() {

        FirebaseAuth firebaseAuthInstance = null;

        try {
            firebaseAuthInstance = FirebaseAuth.getInstance();

        } catch (Exception ex) {
            System.out.println("An exception occurred [getAuthenticationInstance], ex:" + ex);
            ex.printStackTrace();
        }

        return firebaseAuthInstance;

    }

    @Override
    public DocumentSnapshot getUserDocument(String uid) {
        System.out.println("getUserDocument");
        DocumentSnapshot documentSnapshot = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            System.out.println("firestore: " + firestore);

            System.out.println("uid: " + uid);
            ApiFuture<QuerySnapshot> future = firestore.collection("User").whereEqualTo("uId", uid).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                System.out.println(document.getId() + " => " + document.getData());
                if (document.get("uId").equals(uid)) {
                    documentSnapshot = document;
                }
                else {
                    // Create document
                    HashMap documentData = new HashMap();
                    documentData.put("uId", uid);
                    ApiFuture<WriteResult> writeResultApiFuture = firestore.collection("User").document().set(documentData);
                }
            }

            System.out.println("DocumentSnapshot: " + documentSnapshot);

// Check if document is equal to null
        }
        catch (Exception ex) {
            System.out.println("An exception occurred, ex: " + ex.getMessage());
            ex.printStackTrace();
        }

        return documentSnapshot;
    }

    @Override
    public UserRecord getUId(String uId, FirebaseAuth firebaseAuthInstance) {
        DocumentSnapshot documentSnapshot = null;
        ApiFuture result = null;
        UserRecord userRecord = null;

        try {
            System.out.println("getByEmail: " + firebaseAuthInstance.getUser(uId));
            userRecord = firebaseAuthInstance.getUser(uId);
            System.out.println("UserRecord: " + userRecord.getEmail());

        } catch (Exception ex) {
            System.out.println("An exception occurred [getUId], ex: " + ex);
        }
        return userRecord;
    }
}

