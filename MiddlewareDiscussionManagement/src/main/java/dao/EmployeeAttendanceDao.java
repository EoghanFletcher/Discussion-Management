package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firestore.v1.Write;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class EmployeeAttendanceDao implements EmployeeAttendanceDaoInterface {
    @Override
    public DocumentSnapshot addMasterList(String username, String databaseCollection) {
        System.out.println("addMasterList");
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentSnapshot documentSnapshot = null;
        Map userData = null;
        List masterList = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();


            System.out.println("documents.size(): " + documents.size());

            for (DocumentSnapshot document : documents) {
                System.out.println("id: " + document.getId());
                System.out.println("data: " + document.getData());
                if (document.getId().equals("MasterList")) {
                    System.out.println("here");
                    documentSnapshot = document;


                    userData = new HashMap();
                    userData.put(username, Map.of("username", username));

                    firestore.collection(databaseCollection).document("MasterList").update(userData);

                    for (DocumentSnapshot document1 : documents) {
                        if (document.getId().equals(username)) { documentSnapshot = document1; }
                    }

                }
            }
        }  catch (Exception ex) {
            System.out.println("An exception occurred [addMasterList], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @Override

    public DocumentSnapshot confirmAttendance(String uId, String username, DocumentSnapshot copy, String databaseCollection) {
        System.out.println("confirmAttendance");

        System.out.println("uId" + uId);
        System.out.println("username" + username);

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        String date = null;
        WriteResult writeResult = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;
        Map deleteMap = new HashMap();

        try {
            date = this.getCurrentDate();
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            documentSnapshot = this.searchAttendances(documents, date);
            Map dateMap = new HashMap();
            dateMap.put("date", Map.of(date, date));
            Map hashMapUser  = this.createMap(uId, username);
            deleteMap.put(username, FieldValue.delete());

            if (documentSnapshot == null) {
                System.out.println("null");
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Present").set(hashMapUser);
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Absent").set(copy.getData());
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Absent").update(deleteMap);


                // Check if document exists
                documents = future.get().getDocuments();
                documentSnapshot = this.searchAttendances(documents, date);
            }
            else {
                System.out.println("not null");
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(hashMapUser);
                firestore.collection(databaseCollection).document("date").collection(date).document("Present").update(hashMapUser);
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Absent").update(deleteMap);

            }
        }  catch (Exception ex) {
            System.out.println("An exception occurred [confirmAttendance], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return documentSnapshot;
    }

    @Override
    public String getCurrentDate()  {
        System.out.println("getCurrentDate");
        Calendar calendar = null;
        String dateMonthYear = null;
        try {
            calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } catch (Exception ex) {
            System.out.println("An exception occurred [getDate], ex: " + ex.getMessage());
            ex.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(LocalDate.now());
        return calendar.getTime().toString();
    }

    @Override
    public Map createMap(String uId, String username) {
        Map hashMapUser = null;
        try {
            hashMapUser = new HashMap();
            hashMapUser.put(username, Map.of("uId", uId, "username", username));
        } catch (Exception ex) {
            System.out.println("An exception occurred [getListOfPresentEmployees], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return hashMapUser;
    }

    @Override
    public DocumentSnapshot searchAttendances(List<QueryDocumentSnapshot> documents, String entryName) {
        System.out.println("searchAttendances");
        DocumentSnapshot documentSnapshot = null;

        System.out.println("documents.size(): " + documents.size());
        try {
            for (DocumentSnapshot document : documents) {
                if (document.getId().equals(entryName)) { documentSnapshot = document; }
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [searchAttendances], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("documentSnapshot: " + documentSnapshot);
        return documentSnapshot;
    }

    @Override
    public DocumentSnapshot getListOfPresentAbsentEmployees(String listType, String databaseCollection) {
        System.out.println("getListOfPresentAbsentEmployees");

        String date = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        CollectionReference collectionReference = null;
        try {
            Firestore firestore = Dao.initialiseFirestore();
            System.out.println("databaseCollection: " + databaseCollection);
            date = this.getCurrentDate();
            collectionReference = firestore.collection(databaseCollection).document("Date").collection(date);

            future = collectionReference.get(); // .whereEqualTo("date", date).get();
            documents = future.get().getDocuments();

            System.out.println("entrySet: " + documents.size());
            System.out.println("0: " + documents.get(0));
            System.out.println("1: " + documents.get(1));

            for (DocumentSnapshot document : documents) {
                System.out.println("document.getId(): " + document.getId());
                if (document.getId().equals(listType)) { documentSnapshot = document; }
            }
            System.out.println("documentSnapshot: " + documentSnapshot.getData().entrySet());
        } catch (Exception ex) {
            System.out.println("An exception occurred [getListOfPresentEmployees], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return documentSnapshot;
    }

    @Override
    public DocumentSnapshot getListOfAllEmployees(String databaseCollection) {
        return null;
    }

    @Override
    public boolean createNode(String date, String username, String message, String databaseCollection) {
        System.out.println("createNode");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        Firestore firestore = Dao.initialiseFirestore();
        Map userData = null;
        try {

            EmployeeAttendanceDao employeeAttendanceDao = new EmployeeAttendanceDao();
            // I will need to use a different list in the final version
//            documentSnapshot = employeeAttendanceDao.getListOfPresentEmployees(databaseCollection);
            if (documentSnapshot.getData().get(username) != null) {
                userData = new HashMap();
                userData.put(username, documentSnapshot.getData().get(username));
                userData.put("Note", message);
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(userData);
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [createNode], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public DocumentSnapshot copyMasterList(String databaseCollection) {
        System.out.println("copyMasterList");
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentSnapshot documentSnapshot = null;
        Map userData = null;
        final String masterList = "MasterList";

        try {
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection(databaseCollection).get(); // .whereEqualTo("date", date).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                System.out.println("document.getId(): " + document.getId());
                if (document.getId().equals(masterList)) { documentSnapshot = document; }

                System.out.println("entrySet: " + documentSnapshot.getData().entrySet());

                if (documentSnapshot.getData().size() > 0) {
                    return documentSnapshot;
                }
            }
        } catch (Exception ex) {
                System.out.println("An exception occurred [createNode], ex: " + ex.getMessage());
                ex.printStackTrace();
            }
        return null;
    }
}
