package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firestore.v1.Write;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class EmployeeAttendanceDao implements EmployeeAttendanceDaoInterface {
    @Override
    public DocumentSnapshot confirmAttendance(String uId, String username, String databaseCollection) {
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

        try {
            date = this.getCurrentDate();
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                if (document.getId().equals(date)) { documentSnapshot = document; }
            }

            Map dateMap = new HashMap();
            dateMap.put("date", Map.of(date, date));
            Map hashMapUser = new HashMap();
            hashMapUser.put(username, Map.of(username, uId));

            if (documentSnapshot == null) {
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).set(dateMap);
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(hashMapUser);

                // Check if document exists
                documents = future.get().getDocuments();

                for (DocumentSnapshot document : documents) {
                    if (document.getId().equals(date)) { documentSnapshot = document; }
                }
            }
            else {
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(hashMapUser);

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
    public DocumentSnapshot getListOfPresentEmployees(String databaseCollection) {
        System.out.println("getListOfPresentEmployees");

        String date = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;
        DocumentSnapshot documentSnapshot = null;
        try {
            date = this.getCurrentDate();
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection(databaseCollection).get(); // .whereEqualTo("date", date).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                System.out.println("document.getId(): " + document.getId());
                if (document.getId().equals(date)) { documentSnapshot = document; }
            }
        } catch (Exception ex) {
            System.out.println("An exception occurred [getListOfPresentEmployees], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return documentSnapshot;
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
            documentSnapshot = employeeAttendanceDao.getListOfPresentEmployees(databaseCollection);
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
}
