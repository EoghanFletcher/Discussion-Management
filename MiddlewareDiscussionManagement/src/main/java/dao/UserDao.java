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
    public boolean createProfileField(String uId, String key, String value) {
        System.out.println("createProfileField");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection("User").whereEqualTo("uId", uId).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.get("uId").equals(uId)) {
                    documentSnapshot = document;
                }
            }

            HashMap documentData = new HashMap();
            documentData.put(key, value);
            ApiFuture<WriteResult> writeResultApiFuture = firestore.collection("User").document(documentSnapshot.getId()).update((documentData));
        } catch(Exception ex) {
            System.out.println("An exception occurred [createProfileField], ex: " + ex);
        }

        return false;

    }

    @Override
    public boolean removeProfileField(String uId, String key) {
        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        boolean remove = false;
        ApiFuture<WriteResult> writeFuture = null;
        DocumentReference documentReference = null;


        try {
            Firestore firestore = Dao.initialiseFirestore();
// Get snapshot
            future = firestore.collection("User").whereEqualTo("uId", uId).get();
            documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                if (document.get("uId").equals(uId)) { documentSnapshot = document; }
            }

            System.out.println("documentSnapshot: " + documentSnapshot.getData().entrySet());

            // Create document reference
            documentReference = firestore.collection("User").document(documentSnapshot.getId());
            System.out.println("documentReference: " + documentReference.get());

            // Update
            HashMap documentData = new HashMap();
            System.out.println("entryset: " + documentData.entrySet());

            documentData.remove(key);
            System.out.println("entryset: " + documentData.entrySet());
            ApiFuture<WriteResult> writeResultApiFuture = firestore.collection("User").document(documentSnapshot.getId()).update((documentData));
            System.out.println("updates: " + documentData.entrySet());
            writeFuture = documentReference.update(documentData);


            System.out.println("documentSnapshot: " + documentSnapshot.getId());
            writeFuture = firestore.collection("User").document(documentSnapshot.getId()).delete();
//            return true;
            System.out.println("writeFuture: " + writeFuture.get());

        } catch (Exception ex) {
            System.out.println("An exception occurred [removeProfileField], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return remove;
    }
}