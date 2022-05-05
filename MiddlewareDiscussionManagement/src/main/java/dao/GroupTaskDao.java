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
                writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).set(documentData);

                // Create Membership
                groupTaskDao = new GroupTaskDao();

                result = groupTaskDao.addGroupMember(username,firestore, groupName, databaseCollection);
                if (result) { groupTaskDao.assignAdminPrivileges(firestore, groupName, username, databaseCollection); }
            }
            else {
                // Update group
                if (groupDescription != "") {
                    documentData.put("groupDescription", groupDescription);
                    documentReference = firestore.collection(databaseCollection).document(groupName);
                    writeResultApiFuture = documentReference.update(documentData);
                }
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [createUpdateGroup], ex: " + ex);
        }

        if (writeResultApiFuture != null) { result = true; }
        return result;
    }

    public boolean addGroupMember(String username, Firestore firestore, String groupName, String databaseCollection) {
        System.out.println("addGroupMember");

        ApiFuture<WriteResult> writeResultApiFuture = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<DocumentSnapshot> apiFutureDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        Map dataMap = null;
        boolean result = false;

        try {
//            groupData.put("RequestToLeave", Map.of(username, username));
//            writeResultApiFuture = firestore.collection(databaseCollection").document(groupName).update(groupData);



            HashMap membershipData = new HashMap();
            membershipData.put("Membership." + username, Map.of(username, username));
//            membershipData.put("Membership." + username, username);
            writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).update(membershipData);

            Thread.sleep(2000);
            documentReference = firestore.collection(databaseCollection).document(groupName);
            apiFutureDocumentSnapshot = documentReference.get();
            documentSnapshot = apiFutureDocumentSnapshot.get();
            dataMap = documentSnapshot.getData();
            Thread.sleep(2000);

            if (((Map) dataMap.get("Membership")).containsKey(username)) {
                result = true;
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [addGroupMember], ex: " + ex);
        }
        return result;
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

            writeResultApiFuture = collectionReferenceTask.document(taskName).set(documentData);

            Thread.sleep(2000);

                future = collectionReferenceTask.get();
                documents = future.get().getDocuments();

                for (DocumentSnapshot document: documents) {
                    if (document.getData().get("taskName").equals(taskName)) {
                        result = true;
                    }
                }
        } catch(Exception ex) {
            System.out.println("An exception occurred [createTask], ex: " + ex);
        }
        return result;
    }

    @Override
    public boolean deactivateTask(String groupName, String taskName, String databaseCollection) {
        System.out.println("deactivateTask");
        DocumentSnapshot documentSnapshot = null;
//        ApiFuture<QuerySnapshot> future = null;
        DocumentReference documentReference = null;
        List<QueryDocumentSnapshot> documents = null;
        Future<DocumentSnapshot> apiFutureFuture = null;
        CollectionReference collectionReference = null;

        ApiFuture<QuerySnapshot> future = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            collectionReference = firestore.collection(databaseCollection).document(groupName).collection("Task");
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


            Thread.sleep(2000);

            future = collectionReference.get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document: documents) {
                if (document.getData().get("taskName").equals(taskName)) {
                    result = true;
                }
            }

            for (DocumentSnapshot document: documents) {
                documentSnapshot = document;
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [deactivateTask], ex: " + ex.getMessage());
            ex.printStackTrace();
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

            System.out.println("taskList size: " + documents.size());
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
        DocumentReference documentReference = null;
        ApiFuture<DocumentSnapshot> apiFutureDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;

        boolean result = false;
        Map dataMap = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            HashMap groupData = new HashMap();
            groupData.put("RequestToLeave", Map.of(username, username));
            writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).update(groupData);

            Thread.sleep(2000);
            documentReference = firestore.collection(databaseCollection).document(groupName);
            apiFutureDocumentSnapshot = documentReference.get();
            documentSnapshot = apiFutureDocumentSnapshot.get();
            dataMap = documentSnapshot.getData();
            Thread.sleep(2000);


            System.out.println("entryset: " + dataMap.entrySet());

            if (((Map) dataMap.get("RequestToLeave")).containsKey(username)) {
                result = true;
            }
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
        DocumentReference documentReference = null;
        ApiFuture<DocumentSnapshot> apiFutureDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        Map dataMap = null;

        try {
            System.out.println("1");
            removeRequest = removeRequestToLeave(groupName, username, databaseCollection);
            System.out.println("2");
            System.out.println("removeRequest2: " + removeRequest);

            System.out.println("removeRequest: " + removeRequest);

            if (removeRequest) {
                Firestore firestore = Dao.initialiseFirestore();
                HashMap groupData = new HashMap();
                // If the user is not an administrator nothing will happen
                groupData.put("Administration." + username, FieldValue.delete());
                groupData.put("Membership." + username, FieldValue.delete());
                writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).update(groupData);


                Thread.sleep(2000);
                documentReference = firestore.collection(databaseCollection).document(groupName);
                apiFutureDocumentSnapshot = documentReference.get();
                documentSnapshot = apiFutureDocumentSnapshot.get();
                dataMap = documentSnapshot.getData();
                Thread.sleep(2000);

                if (!dataMap.containsKey(username)) {
                    result = true;
                }
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
        DocumentReference documentReference = null;
        ApiFuture<DocumentSnapshot> apiFutureDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        Map dataMap = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();

            HashMap groupData = new HashMap();
            groupData.put("RequestToLeave." + username, FieldValue.delete());

            writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).update(groupData);


            Thread.sleep(2000);
            documentReference = firestore.collection(databaseCollection).document(groupName);
            apiFutureDocumentSnapshot = documentReference.get();
            documentSnapshot = apiFutureDocumentSnapshot.get();
            dataMap = documentSnapshot.getData();
            Thread.sleep(2000);

            System.out.println("entryset: " + dataMap.entrySet());

            if (!dataMap.containsKey(username)) {
                result = true;
            }

        } catch(Exception ex) {
            System.out.println("An exception occurred [removeRequestToLeave], ex: " + ex);
        }
        System.out.println("return");
        return result;
    }

    @Override
    public boolean assignAdminPrivileges(Firestore firestore, String groupName, String username, String databaseCollection) {
        System.out.println("assignAdminPrivileges");

        ApiFuture<WriteResult> writeResultApiFuture = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<DocumentSnapshot> apiFutureDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        boolean success = false;
        Map dataMap = null;

        try {
            HashMap adminData = new HashMap();
            adminData.put("Administration." + username, Map.of(username, username));
            writeResultApiFuture = firestore.collection(databaseCollection).document(groupName).update(adminData);


            Thread.sleep(2000);
            documentReference = firestore.collection(databaseCollection).document(groupName);
            apiFutureDocumentSnapshot = documentReference.get();
            documentSnapshot = apiFutureDocumentSnapshot.get();
            dataMap = documentSnapshot.getData();
            Thread.sleep(2000);

            if (((Map) dataMap.get("Administration")).containsKey(username)) {
                success = true;
            }
        } catch(Exception ex) {
            System.out.println("An exception occurred [assignAdminPrivileges], ex: " + ex);
        }
        return success;
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
