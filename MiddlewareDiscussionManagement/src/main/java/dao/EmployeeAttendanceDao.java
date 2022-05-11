package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

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

            if (documents.size() == 0) {
                userData = new HashMap();
                userData.put(username, Map.of("username", username));

                firestore.collection(databaseCollection).document("MasterList").set(userData);
                Thread.sleep(2000);
            }

            for (DocumentSnapshot document : documents) {
                if (document.getId().equals("MasterList")) {
                    documentSnapshot = document;

                    userData = new HashMap();
                    userData.put(username, Map.of("username", username));
                    firestore.collection(databaseCollection).document("MasterList").update(userData);
                }
            }

            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();
            Thread.sleep(5000);

            for (DocumentSnapshot transientDocument : documents) { if (transientDocument.getId().equals("MasterList")) { documentSnapshot = transientDocument; } }

            return documentSnapshot;
        }  catch (Exception ex) {
            System.out.println("An exception occurred [addMasterList], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public DocumentSnapshot confirmAttendance(String uId, String username, DocumentSnapshot copy, String databaseCollection) {
        System.out.println("confirmAttendance");

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
            future = firestore.collection(databaseCollection).document("Date").collection(date).get();
            documents = future.get().getDocuments();

            documentSnapshot = this.searchAttendances(documents, date);
            Map dateMap = new HashMap();
            dateMap.put("date", Map.of(date, date));
            Map hashMapUser  = this.createMap(uId, username);
            deleteMap.put(username, FieldValue.delete());

            if (documentSnapshot == null) {
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Present").set(hashMapUser);
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Absent").set(copy.getData());
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Absent").update(deleteMap);
                Thread.sleep(10000);

                // Check if document exists
                future = firestore.collection(databaseCollection).document("Date").collection(date).get();
                documents = future.get().getDocuments();
                documentSnapshot = this.searchAttendances(documents, "Present");
            }
            else {
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(hashMapUser);
                firestore.collection(databaseCollection).document("date").collection(date).document("Present").update(hashMapUser);
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document("Absent").update(deleteMap);
            }
        }
        catch (Exception ex) {
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
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getCurrentDate], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return calendar.getTime().toString();
    }

    @Override
    public Map createMap(String uId, String username) {
        Map hashMapUser = null;

        try {
            hashMapUser = new HashMap();
            hashMapUser.put(username, Map.of("uId", uId, "username", username));
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [createMap], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return hashMapUser;
    }

    @Override
    public DocumentSnapshot searchAttendances(List<QueryDocumentSnapshot> documents, String entryName) {
        System.out.println("searchAttendances");
        DocumentSnapshot documentSnapshot = null;

        try {
            for (DocumentSnapshot document : documents) { if (document.getId().equals(entryName)) { documentSnapshot = document; } }
        } catch (Exception ex) {
            System.out.println("An exception occurred [searchAttendances], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
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
            date = this.getCurrentDate();
            collectionReference = firestore.collection(databaseCollection).document("Date").collection(date);
            future = collectionReference.get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) { if (document.getId().equals(listType)) { documentSnapshot = document; } }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getListOfPresentAbsentEmployees], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return documentSnapshot;
    }

    @Override
    public DocumentSnapshot getListOfAllEmployees(String databaseCollection) {
        System.out.println("getListOfAllEmployees");

        String date = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        CollectionReference collectionReference = null;

        try {
            Firestore firestore = Dao.initialiseFirestore();
            date = this.getCurrentDate();
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) { if (document.getId().equals("MasterList")) { documentSnapshot = document; } }
            System.out.println("documentSnapshot: " + documentSnapshot.getData().entrySet());
        } catch (Exception ex) {
            System.out.println("An exception occurred [getListOfAllEmployees], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return documentSnapshot;
    }

    @Override
    public boolean createNode(String username, String title, String message, String listType, String databaseCollection) {
        System.out.println("createNode");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        Firestore firestore = Dao.initialiseFirestore();
        String date = null;
        Map userData = null;

        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<DocumentSnapshot> apiFutureDocumentSnapshot = null;
        boolean result = false;

        try {
            EmployeeAttendanceDao employeeAttendanceDao = new EmployeeAttendanceDao();
            date = this.getCurrentDate();
            documentSnapshot = employeeAttendanceDao.getListOfPresentAbsentEmployees("Present", databaseCollection);
            if (documentSnapshot.getData().get(username) != null) {
                userData = new HashMap();
                userData.put(username, Map.of("username", username, "title", title, "message", message));
                writeResultApiFuture = firestore.collection(databaseCollection).document("Date").collection(date).document(listType).collection("Notes").document(date).set(userData);
            }
            Thread.sleep(2000);

            documentReference = firestore.collection(databaseCollection).document("Date").collection(date).document(listType).collection("Notes").document(date);
            apiFutureDocumentSnapshot = documentReference.get();
            documentSnapshot = apiFutureDocumentSnapshot.get();
            userData = documentSnapshot.getData();
            Thread.sleep(2000);

            if (userData.get(username) != null) { result = true; }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [createNode], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public DocumentSnapshot getNotes(String username, String listType, String databaseCollection) {
        System.out.println("getNotes");

        String date = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        CollectionReference collectionReference = null;

        try {
            date = this.getCurrentDate();
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).document("Date").collection(date).document(listType).collection("Notes").get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) { if (document.getId().equals(date)) { documentSnapshot = document; } }
            System.out.println("documentSnapshot: " + documentSnapshot.getData().entrySet());

            return documentSnapshot;
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getNotes], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
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
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.getId().equals(masterList)) { documentSnapshot = document; }

                if (documentSnapshot.getData().size() > 0) { return documentSnapshot; }
            }
        }
        catch (Exception ex) {
                System.out.println("An exception occurred [copyMasterList], ex: " + ex.getMessage());
                ex.printStackTrace();
            }
        return null;
    }
}
