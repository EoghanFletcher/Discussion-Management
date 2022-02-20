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
                membershipData.put("Membership", Map.of("email", "email"));
                writeResultApiFuture = firestore.collection("Group").document(groupName).update(membershipData);

//                                                .collection("Membership").document("Members")
//                                                .set(membershipData);
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

    @Override
    public List listGroups(String uId, String email, String databaseCollection) {
        System.out.println("listGroups");

        DocumentSnapshot documentSnapshot = null;
        ApiFuture<QuerySnapshot> future = null;
        Iterable<CollectionReference> collectionReferenceIterable = null;
        List<QueryDocumentSnapshot> documents = null;
        List<DocumentSnapshot> listDocumentSnapshot = null;
        DocumentReference documentReference = null;
        ApiFuture<WriteResult> writeResultApiFuture = null;
        boolean result = false;

        try {
            Firestore firestore = Dao.initialiseFirestore();

            future = firestore.collection(databaseCollection).get();
            documents = future.get().getDocuments();

            documents = future.get().getDocuments();

//            System.out.println(future.get().getDocuments().get(1).getData());
//            System.out.println("documents: " + documents.get(1).getData());

            System.out.println("\n");

//            System.out.println("uId: " + uId);
//            System.out.println("email: " + email);
//            System.out.println("databaseCollection: " + databaseCollection);

            System.out.println("\n");

            listDocumentSnapshot = new ArrayList<>();
            for (DocumentSnapshot document: documents) {
                System.out.println("document getData: " + document.getData());
                System.out.println("document getData Membership: " + document.getData().get("Membership"));


                if (((Map) document.getData().get("Membership")).containsKey("email")) {
                    System.out.println("yes");
                    listDocumentSnapshot.add(document);

                }
                else {
                    System.out.println("no");
                }
            }

            System.out.println("Size: "  + listDocumentSnapshot.size());

                System.out.println("\n");

                for (DocumentSnapshot documentSnapshot1: listDocumentSnapshot) {
                    System.out.println("documentSnapshot1: " + documentSnapshot1.getData());
                }

                System.out.println("\n");

        } catch(Exception ex) {
            System.out.println("An exception occurred [createProfileField], ex: " + ex);
        }
//
//        if (writeResultApiFuture != null) { result = true; }

        return listDocumentSnapshot;
    }
}
