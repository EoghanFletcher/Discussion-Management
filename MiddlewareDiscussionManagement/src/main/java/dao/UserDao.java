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
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;


        try {
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection("User").whereEqualTo("uId", uid).get();
            documents = future.get().getDocuments();

            if (documents.size() == 0) {
                // Create document
                HashMap documentData = new HashMap();
                documentData.put("uId", uid);
                ApiFuture<WriteResult> writeResultApiFuture = firestore.collection("User").document().set(documentData);

                future = firestore.collection("User").whereEqualTo("uId", uid).get();
                documents = future.get().getDocuments();
            }

            for (DocumentSnapshot document : documents) {
                if (document.get("uId").equals(uid)) {
                    documentSnapshot = document;
                }
            }
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
            userRecord = firebaseAuthInstance.getUser(uId);
        } catch (Exception ex) {
            System.out.println("An exception occurred [getUId], ex: " + ex);
        }
        return userRecord;
    }
}