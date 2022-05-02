package dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;

import static business.Email.CREDENTIALS_FILE_PATH;

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

    public static void initialiseFirebaseConnection() {
        System.out.println("initialiseFirebaseConnection");

        try {

            InputStream accountCredentials =  Dao.class.getResourceAsStream("/discussionmanagement-a9065-firebase-adminsdk-eqdn6-bcec425738.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(accountCredentials))
                    .setDatabaseUrl("https://ProjectPrototype-DemoApp-DkIT.firebaseio.com/")
                    .build();

            System.out.println(FirebaseApp.initializeApp(options));
            System.out.println("\nExecuting...\n");
        }
        catch (Exception ex) {
            System.out.println("An exception occurred, failed to connect to database [initialiseFirebaseConnection], ex: " + ex);
            ex.printStackTrace();
        }
    }

}
