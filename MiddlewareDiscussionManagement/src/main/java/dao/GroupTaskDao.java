package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.*;
import java.util.concurrent.Future;

public class GroupTaskDao implements GroupTaskDaoInterface {

    @Override
    public boolean createUpdateGroup(String uId, String username, String groupName, String groupDescription, String databaseCollection) {
        System.out.println("createUpdateGroup");

        System.out.println("username: " + username);

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;
        GroupTaskDao groupTaskDao = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).whereEqualTo("username", username).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.get("username").equals(username)) { documentSnapshot = document; }
            }

            HashMap documentData = new HashMap();
            if (documentSnapshot == null) {
                // Create group
                documentData.put("groupName", groupName);
                documentData.put("groupDescription", groupDescription);
                writeResultApiFuture = firestore.collection("Group").document(groupName).set(documentData);

                // Create Membership
                groupTaskDao = new GroupTaskDao();

                System.out.println("username: " + username);

                writeResultApiFuture = groupTaskDao.addGroupMember(username,firestore, groupName);
                writeResultApiFuture = groupTaskDao.assignAdminPrivileges(firestore, groupName, username);
            }
            else {
                // Update group
                if (groupDescription != "") {
                    documentData.put("groupDescription", groupDescription);
                    documentReference = firestore.collection("Group").document(groupName);
                    writeResultApiFuture = documentReference.update(documentData);
                }
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [createUpdateGroup], ex: " + ex);
        }

        if (writeResultApiFuture != null) { result = true; }
        return result;
    }

    public ApiFuture<WriteResult> addGroupMember(String username, Firestore firestore, String groupName) {
        System.out.println("addGroupMember");

        ApiFuture<WriteResult> writeResultApiFuture = null;

        try {
            HashMap membershipData = new HashMap();
            membershipData.put("Membership", Map.of(username, username));
            writeResultApiFuture = firestore.collection("Group").document(groupName).update(membershipData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [addGroupMember], ex: " + ex);
        }
        return writeResultApiFuture;
    }

    @Override
    public boolean createTask(String username, String groupName, String taskName, String taskDescription, String taskType, /* Date */ String dateTimeOfEvent, String databaseCollection) {
        System.out.println("createTask");

        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;
        GroupTaskDao groupTaskDao = null;
        CollectionReference collectionReferenceTask = null;
        Map documentData;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            collectionReferenceTask = firestore.collection(databaseCollection).document(groupName).collection("Task");

            documentData = new HashMap();
            documentData.put("taskName", taskName);
            documentData.put("taskDescription", taskDescription);
            documentData.put("taskType", taskType);
            documentData.put("dateTimeOfEvent", dateTimeOfEvent);
            documentData.put("createdBy", username);
            documentData.put("status", "active");

            writeResultApiFuture = collectionReferenceTask.document().set(documentData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [createTask], ex: " + ex);
        }
        return result;
    }

    @Override
    public List<DocumentSnapshot> listGroups(String uId, String username, String databaseCollection) {
        System.out.println("listGroups");

        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            listDocumentSnapshot = new ArrayList<>();
            for (DocumentSnapshot document: documents) {
                if (((Map) document.getData().get("Membership")).containsKey(username)) { listDocumentSnapshot.add(document); }
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [listGroup], ex: " + ex);
        }
        return listDocumentSnapshot;
    }

    @Override
    public List<DocumentSnapshot> listTasks(String groupName, String databaseCollection) {
        System.out.println("listTasks");

        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;
        CollectionReference collectionReferenceTask = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            collectionReferenceTask = firestore.collection(databaseCollection).document(groupName).collection("Task");
            future = collectionReferenceTask.get();
            documents = future.get().getDocuments();

//            for (DocumentSnapshot document: documents) {
//                System.out.println("document: " + document.getData());
//            }

            listDocumentSnapshot = new ArrayList<>();
            for (DocumentSnapshot document: documents) {
//            if (document.get("status").equals("active") && document.get("dateTimeOfEvent") >= DateTime.) {
                listDocumentSnapshot.add(document);
//            }
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [listTasks], ex: " + ex);
        }
        return listDocumentSnapshot;
    }

    @Override
    public ApiFuture<WriteResult> assignAdminPrivileges(Firestore firestore, String groupName, String username) {
        System.out.println("assignAdminPrivileges");

        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean success = false;

        try {
            HashMap adminData = new HashMap();
            adminData.put("Administration", Map.of(username, username));
            writeResultApiFuture = firestore.collection("Group").document(groupName).update(adminData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [assignAdminPrivileges], ex: " + ex);
        }
        return writeResultApiFuture;
    }

    @Override
    public DocumentSnapshot listEvents(String databaseCollection) {
        System.out.println("listEvents");
        DocumentSnapshot documentSnapshot = null;
//        ApiFuture<QuerySnapshot> future = null;
        DocumentReference documentReference = null;
        List<QueryDocumentSnapshot> documents = null;
        Future<DocumentSnapshot> apiFutureFutre = null;
        CollectionReference collectionReference = null;

        ApiFuture<QuerySnapshot> future = null;


        try {
            Firestore firestore = Dao.initialiseFirestore();
            collectionReference = firestore.collection(databaseCollection);
            future = collectionReference.get();
            documents = future.get().getDocuments();

//            for (DocumentSnapshot document: documents) {
//                System.out.println("document: " + document.getData());
//            }

            for (DocumentSnapshot document: documents) {
            documentSnapshot = document;
            }


        } catch (Exception ex) {
            System.out.println("An exception occurred [listEvents], ex: " + ex.getMessage());
            ex.printStackTrace();
        }

        return documentSnapshot;
    }
}
