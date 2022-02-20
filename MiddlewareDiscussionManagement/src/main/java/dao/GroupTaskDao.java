package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.*;

public class GroupTaskDao implements GroupTaskDaoInterface {

    @Override
    public boolean createUpdateGroup(String uId, String email, String groupName, String groupDescription, String databaseCollection) {
        System.out.println("createUpdateGroup");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;
        GroupTaskDao groupTaskDao = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).whereEqualTo("uId", uId).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.get("uId").equals(uId)) { documentSnapshot = document; }
            }

            HashMap documentData = new HashMap();
            if (documentSnapshot == null) {
                // Create group
                documentData.put("groupName", groupName);
                documentData.put("groupDescription", groupDescription);
                writeResultApiFuture = firestore.collection("Group").document(groupName).set(documentData);

                // Create Membership
                groupTaskDao = new GroupTaskDao();

                writeResultApiFuture = groupTaskDao.addGroupMember(email,firestore, groupName);
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

    public ApiFuture<WriteResult> addGroupMember(String email, Firestore firestore, String groupName) {
        System.out.println("addGroupMember");

        ApiFuture<WriteResult> writeResultApiFuture = null;

        try {
            HashMap membershipData = new HashMap();
            membershipData.put("Membership", Map.of("email", "email"));
            writeResultApiFuture = firestore.collection("Group").document(groupName).update(membershipData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [addGroupMember], ex: " + ex);
        }
        return writeResultApiFuture;
    }



    @Override
    public boolean createTask(String uId, String groupName, String taskName, String taskDescription, String taskType, String dateTimeOfEvent, String databaseCollection) {
        System.out.println("createTask");

        DocumentSnapshot documentSnapshot = null;
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

            writeResultApiFuture = collectionReferenceTask.document().set(documentData);

        } catch(Exception ex) {
            System.out.println("An exception occurred [createTask], ex: " + ex);
        }
        return result;
    }

    @Override
    public List<DocumentSnapshot> listGroups(String uId, String email, String databaseCollection) {
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
                if (((Map) document.getData().get("Membership")).containsKey("email")) { listDocumentSnapshot.add(document); }
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [createProfileField], ex: " + ex);
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

            for (DocumentSnapshot document: documents) {
                System.out.println("document: " + document.getData());
            }

            listDocumentSnapshot = new ArrayList<>();
            for (DocumentSnapshot document: documents) {
                listDocumentSnapshot.add(document);
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [createProfileField], ex: " + ex);
        }

        return listDocumentSnapshot;
    }
}
