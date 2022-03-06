package dao;

import com.google.api.client.util.DateTime;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

public class GroupTaskDao implements GroupTaskDaoInterface {

    @Override
    public boolean createUpdateGroup(String uId, String username, String groupName, String groupDescription, String databaseCollection) {
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

                writeResultApiFuture = groupTaskDao.addGroupMember(username,firestore, groupName, "Group");
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

    public ApiFuture<WriteResult> addGroupMember(String username, Firestore firestore, String groupName, String databaseCollection) {
        System.out.println("addGroupMember");

        ApiFuture<WriteResult> writeResultApiFuture = null;

        try {
//            groupData.put("RequestToLeave", Map.of(username, username));
//            writeResultApiFuture = firestore.collection("Group").document(groupName).update(groupData);



            HashMap membershipData = new HashMap();
            membershipData.put("Membership." + username, Map.of(username, username));
//            membershipData.put("Membership." + username, username);
            writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).update(membershipData);
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
    public boolean deactivateTask(String groupName, String taskName) {
        System.out.println("deactivateTask");
        DocumentSnapshot documentSnapshot = null;
//        ApiFuture<QuerySnapshot> future = null;
        DocumentReference documentReference = null;
        List<QueryDocumentSnapshot> documents = null;
        Future<DocumentSnapshot> apiFutureFutre = null;
        CollectionReference collectionReference = null;

        ApiFuture<QuerySnapshot> future = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;


        try {
            Firestore firestore = Dao.initialiseFirestore();
            collectionReference = firestore.collection("Group").document(groupName).collection("Task");
            future = collectionReference.get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.get("taskName").equals(taskName)) { documentSnapshot = document; }
            }

            Map documentData = new HashMap();
            documentData = documentSnapshot.getData();
            documentData.put("status", "deactivated");


            documentReference = collectionReference.document((documentSnapshot.getId()));
            writeResultApiFuture = documentReference.update(documentData);

            for (DocumentSnapshot document: documents) {
                documentSnapshot = document;
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [deactivateTask], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
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

            listDocumentSnapshot = new ArrayList<>();
            for (DocumentSnapshot document: documents) {

//                SimpleDateFormat format = new SimpleDateFormat(("YYYY-MM-DDTHH:mmZ"));
//
//                Date currentDate = new Date();
//                Date dateTimeOfEvent = new Date();


//                Date simpleDateFormat = new SimpleDateFormat("YYYY-MM-DDTHH:mmZ").parse((String) document.get("dateTimeOfEvent"));

//                System.out.println("simpleDateFormat: " + simpleDateFormat);

            if (document.get("status").equals("active") /* && currentDate.after(dateTimeOfEvent) */) {
                listDocumentSnapshot.add(document);
            }
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [listTasks], ex: " + ex);
        }
        return listDocumentSnapshot;
    }

    @Override
    public boolean requestToLeaveGroup(String groupName, String username, String databaseCollection) {
        System.out.println("requestToLeaveGroup");

        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            HashMap groupData = new HashMap();
            groupData.put("RequestToLeave", Map.of(username, username));
            writeResultApiFuture = firestore.collection("Group").document(groupName).update(groupData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [requestToLeaveGroup], ex: " + ex);
        }
        return result;
    }

    @Override
    public boolean grantRequestToLeave(String groupName, String username, String databaseCollection) {
        System.out.println("grantRequestToLeave");

        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;
        boolean removeRequest = false;

        try {
            System.out.println("1");
            removeRequest = removeRequestToLeave(groupName, username, "Group");
            System.out.println("2");
            System.out.println("removeRequest2: " + removeRequest);

            System.out.println("removeRequest: " + removeRequest);

            if (removeRequest) {
                Firestore firestore = Dao.initialiseFirestore();
                HashMap groupData = new HashMap();
                // If the user is not an administrator nothing will happen
                groupData.put("Administration." + username, FieldValue.delete());
                groupData.put("Membership." + username, FieldValue.delete());
                writeResultApiFuture = firestore.collection("Group").document(groupName).update(groupData);
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [grantRequestToLeave], ex: " + ex);
        }

        return result;
    }

    @Override
    public boolean removeRequestToLeave(String groupName, String username, String databaseCollection) {
        System.out.println("removeRequestToLeave");

        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();

            HashMap groupData = new HashMap();
            groupData.put("RequestToLeave." + username, FieldValue.delete());

            writeResultApiFuture = firestore.collection("Group").document(groupName).update(groupData);

//            while (writeResultApiFuture.isDone() == false) {
//                System.out.println("not complete");
//            }
//
//            // This probably returns true if the operation is finished and not if the operation completed successfully
//            System.out.println("is Done: " + writeResultApiFuture.isDone());
//            if (writeResultApiFuture.isDone()) {
//                result = true;
//                System.out.println("true");
//            }
            result = true;

        } catch(Exception ex) {
            System.out.println("An exception occurred [removeRequestToLeave], ex: " + ex);
        }
        System.out.println("return");
        return result;
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
