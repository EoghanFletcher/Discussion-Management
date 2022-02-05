package dao;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class Dao {
    public static Firestore initialiseFirestore() {
        System.out.println("initialiseFirestore");
        Firestore firestore = null;
        try {
            firestore = FirestoreClient.getFirestore();
        }
        catch(Exception ex) {
            System.out.println("An exception occurred, failed to connect to Firestore [initialiseFirestore], ex: " + ex);
        }
        return firestore;
    }
}
