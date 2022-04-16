package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firestore.v1.Write;

import java.util.*;

public class EmployeeAttendanceDao implements EmployeeAttendanceDaoInterface {
    @Override
    public boolean confirmAttendance(String uId, String username, String databaseCollection) {
        System.out.println("confirmAttendance");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        String date = null;
        WriteResult writeResult = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            date = this.getDate();
            Firestore firestore = Dao.initialiseFirestore();
            future = firestore.collection(databaseCollection).whereEqualTo("date", date).get();
            documents = future.get().getDocuments();

            for (DocumentSnapshot document : documents) {
                System.out.println("document.getId(): " + document.getId());
                if (document.getId().equals(date)) { documentSnapshot = document; }
            }

            System.out.println();
            System.out.println("uId: " + uId);
            System.out.println("username: " + username);
            System.out.println("databaseCollection: " + databaseCollection);
            System.out.println("date: " + date);


            Map dateMap = new HashMap();

            dateMap.put("date", date);
            Map hashMapUer = new HashMap();
            hashMapUer.put(username, uId);

            System.out.println(dateMap.entrySet());;



            System.out.println("documentSnapshot: " + documentSnapshot);
            if (documentSnapshot == null) {
                System.out.println("null here ");
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).set(dateMap);
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(hashMapUer);
            }
            else {
                System.out.println("not null");
                writeResultApiFuture = firestore.collection(databaseCollection).document(date).update(hashMapUer);
            }

//            documents = firestore.collection(databaseCollection).document(date)


        }  catch (Exception ex) {
            System.out.println("An exception occurred [confirmAttendance], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public String getDate() {
        Calendar calendar = null;
        String year = null;
        String month = null;
        String date = null;
        String dateMonthYear = null;
        try {
            calendar = Calendar.getInstance();
            year = String.valueOf(calendar.get(Calendar .YEAR));
            month = String.valueOf(calendar.get(Calendar.MONTH));
            date = String.valueOf(calendar.get(Calendar.DATE));
            dateMonthYear = date + "-" + month + "-" + year;
        } catch (Exception ex) {
            System.out.println("An exception occurred [getDate], ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return dateMonthYear;
    }
}
