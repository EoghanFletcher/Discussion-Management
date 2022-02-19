package dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupTaskDao implements GroupTaskDaoInterface {

    @Override
    public boolean createUpdateGroup(String uId, String groupName, String groupDescription, String databaseCollection) {
        System.out.println("createUpdateGroup");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        List<QueryDocumentSnapshot> documents = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

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
                System.out.println("Yes");

                // Create Membership
                HashMap membershipData = new HashMap();
                membershipData.put("Membership", Map.of("uId", uId));
                writeResultApiFuture = firestore.collection("Group").document(groupName)
                                                .collection(groupName + "Membership").document(groupName + "Members")
                                                .set(membershipData);
            }
            else {
                // Update group
                if (groupDescription != "") {
                    documentData.put("groupDescription", groupDescription);
                    documentReference = firestore.collection("Group").document(groupName);
                    writeResultApiFuture = documentReference.update(documentData);
                }
            }



//            writeResultApiFuture = firestore.collection(databaseCollection).document(documentSnapshot.getId()).update((documentData));
//            documentReference = firestore.collection("Group").document(documentSnapshot.getId());
//            writeResultApiFuture = documentReference.update(documentData);
        } catch(Exception ex) {
            System.out.println("An exception occurred [createUpdateGroup], ex: " + ex);
        }

        if (writeResultApiFuture != null) { result = true; }
        return result;
    }
}
