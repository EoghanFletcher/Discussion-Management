package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } catch (Exception ex) {
            System.out.println("An exception occurred [getUserDocument], ex: " + ex.getMessage());
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

    @Override
    public boolean createUpdateProfileField(String uId, String key, String value, String databaseCollection) {
        System.out.println("createProfileField");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).whereEqualTo("uId", uId).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.get("uId").equals(uId)) { documentSnapshot = document; }
            }

            HashMap documentData = new HashMap();
            documentData.put(key, value);
            writeResultApiFuture = firestore.collection(databaseCollection).document(documentSnapshot.getId()).update((documentData));
        } catch(Exception ex) {
            System.out.println("An exception occurred [createProfileField], ex: " + ex);
        }

        if (writeResultApiFuture != null) { result = true; }
        return result;
    }

    @Override
    public boolean removeProfileField(String uId, String key) {
        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        ApiFuture<WriteResult> writeFuture = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            // Get snapshot
            future = firestore.collection("User").whereEqualTo("uId", uId).get();
            documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) { if (document.get("uId").equals(uId)) { documentSnapshot = document; }
            }

            Map documentData = new HashMap();
            documentData = documentSnapshot.getData();
            documentData.put(key, FieldValue.delete());

            documentReference = firestore.collection("User").document(documentSnapshot.getId());
            writeResultApiFuture = documentReference.update(documentData);
        } catch (Exception ex) {
            System.out.println("An exception occurred [removeProfileField], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        if (writeResultApiFuture != null) {
            result = true;
        }
        return result;
    }
}