package dao;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    public DocumentSnapshot getUserDocumentByUId(String uid, String email, String databaseCollection) {
        System.out.println("getUserDocumentByUId");
        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection(databaseCollection).whereEqualTo("uid", uid).get();
            documents = future.get().getDocuments();

            // Only one value should  be returned
            System.out.println("documents size(): " + documents.size());
            if (documents.size() == 1) {
                documentSnapshot = documents.get(0);
            }
            else {
                for (DocumentSnapshot document : documents) {
                    if (document.get("uid").equals(uid)) {
                        documentSnapshot = document;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [getUserDocument], ex: " + ex.getMessage());
            ex.printStackTrace();
        }

        return documentSnapshot;
    }

    @Override
    public DocumentSnapshot getUserDocumentByEmail(String email, String databaseCollection) {
        System.out.println("getUserDocumentByEmail");
        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection(databaseCollection).whereEqualTo("email", email).get();
            documents = future.get().getDocuments();

            // Only one value should  be returned
            System.out.println("documents size(): " + documents.size());
            if (documents.size() == 1) {
                documentSnapshot = documents.get(0);
                System.out.println("documentSnapshot: " + documentSnapshot.getData());
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [getUserDocument], ex: " + ex.getMessage());
            ex.printStackTrace();
        }

        return documentSnapshot;
    }

    @Override
    public DocumentSnapshot register(String uid, String email, String username, String databaseCollection) {
        System.out.println("register");
        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            // Create document
            HashMap documentData = new HashMap();
            documentData.put("uId", uid);
            documentData.put("email", email);
            documentData.put("username", username);


            // Check if document exists
            future = firestore.collection(databaseCollection).whereEqualTo("username", username).get();
            documents = future.get().getDocuments();

            if (documents.size() == 0) {
                ApiFuture<WriteResult> writeResultApiFuture = firestore.collection(databaseCollection).document().set(documentData);
            }

            future = firestore.collection(databaseCollection).whereEqualTo("username", username).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.getData().get("username").equals(username)) {
                    documentSnapshot = document;
                }
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [register], ex: " + ex.getMessage());
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
    public boolean createUpdateProfileField(String uId, String username, String key, String value, String databaseCollection) {
        System.out.println("createProfileField");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).whereEqualTo("username", username).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {

                if (document.get("username").equals(username)) {
                    documentSnapshot = document;
                }
            }

            HashMap documentData = new HashMap();
            documentData.put(key, value);
            documentReference = firestore.collection(databaseCollection).document(documentSnapshot.getId());

            writeResultApiFuture = documentReference.update(documentData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [createProfileField], ex: " + ex);
        }

        if (writeResultApiFuture != null) { result = true; }
        return result;
    }

    @Override
    public boolean removeProfileField(String uId, String username, String key, String databaseCollection) {
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
            future = firestore.collection(databaseCollection).whereEqualTo("uId", uId).get();
            documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) { if (document.get("uId").equals(uId)) { documentSnapshot = document; }
            }

            Map documentData = null;
            documentData = documentSnapshot.getData();
            documentData.put(key, FieldValue.delete());

            documentReference = firestore.collection(databaseCollection).document(documentSnapshot.getId());
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

    public List<DocumentSnapshot> listUsers(String databaseCollection) {
        System.out.println("listUsers");

        List<DocumentSnapshot> listDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            System.out.println("documents size(): " + documents.size());

            listDocumentSnapshot = new ArrayList<>();
            for (DocumentSnapshot document: documents) {
                System.out.println("document: " + document.getData());
                    listDocumentSnapshot.add(document);
            }

        }
        catch (Exception ex) {
            System.out.println("An exception occurred [listUsers], ex: " + ex);
            ex.printStackTrace();
        }
        return listDocumentSnapshot;
    }

    @Override
    public String testForConnectivity() {
        System.out.println("testForConnectivity");

        try {
                return "Connection established";
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [listUsers], ex: " + ex);
            ex.printStackTrace();
        }
        return null;
    }
}