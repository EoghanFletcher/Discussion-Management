package dao;

import business.Email;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import com.google.cloud.firestore.DocumentSnapshot;
import org.apache.commons.codec.binary.Base64;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

import static business.Email.*;

public class CommunicationDao implements CommunicationDaoInterface {

    Email email = new Email();
    String user = "me";
    NetHttpTransport HTTP_TRANSPORT = null;

    { try { HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); } catch (GeneralSecurityException e) { e.printStackTrace(); } catch (IOException e) {  e.printStackTrace(); } }
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();

    public List<Message> getDrafts(NetHttpTransport HTTP_TRANSPORT) {
        System.out.println("getDrafts");

        List<Draft> draftList = null;
        List<Message> returnList = null;
        Draft draftsResponse = null;

        try {
            ListDraftsResponse listDraftsResponse = service.users().drafts().list(user).execute();
            draftList = listDraftsResponse.getDrafts();
            if (draftList.isEmpty()) {
                System.out.println("No drafts found.");
            } else {
                System.out.println("Drafts:");
                returnList = new ArrayList<>();
                for (Draft draft : draftList) {
                    draftsResponse = service.users().drafts().get("me", draft.getId()).execute();
//                    System.out.println("draftsResponse 0: " + draftsResponse.getMessage().toPrettyString());
                    returnList.add(draftsResponse.getMessage());
                }
            }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getDrafts], ex: " + ex);
            ex.printStackTrace();
        }
        return returnList;
    }

    @Override
    public Message getDraft(List<Message> messageList, String draftId) {
        System.out.println("getDraft");

        Message retrievedDraft = null;
        Draft draftsResponse = null;

        try {

            for (Message message : messageList) {
                if (message.getId().equals(draftId)) {
                    System.out.println("Yes, current draft id : " + message.getId() + ", draftId: " + draftId);
                    retrievedDraft = message;
                }
                else {
                    System.out.println("no, current draft id : " + message.getId() + ", draftId: " + draftId);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getDrafts], ex: " + ex);
            ex.printStackTrace();
        }
        return retrievedDraft;
    }

    public Credential getCredentials(NetHttpTransport HTTP_TRANSPORT) {
        System.out.println("getCredentials");
//        https://developers.google.com/gmail/api/quickstart/java


        GoogleClientSecrets clientSecrets = null;
        GoogleAuthorizationCodeFlow flow = null;
        LocalServerReceiver receiver = null;
        Credential credential = null;
//        List<String> SCOPES = SCOPES_LABELS;
        Set<String> SCOPES = SCOPES_LABELS;

        try {
            // Load client secrets.
            System.out.println();
            Email.class.getResource(CREDENTIALS_FILE_PATH);
            InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            receiver = new LocalServerReceiver.Builder().setPort(8899).build();
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            //returns an authorized Credential object.
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getCredentials], ex: " + ex);
            ex.printStackTrace();
        }
        return credential;
    }

    @Override
    public MimeMessage createMineMessage(Map draftData, NetHttpTransport HTTP_TRANSPORT) {
        System.out.println("createMineMessage");

        List<Draft> draftList = null;
        Draft draft = new Draft();
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        try {
            email.setFrom(new InternetAddress(draftData.get("from").toString()));
            email.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(draftData.get("to").toString()));
            email.setSubject(draftData.get("subject").toString());
            email.setText(draftData.get("body").toString());


//            ListDraftsResponse listDraftsResponse = service.users().drafts().create();
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [createMineMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return email;
    }

    public Message createMessage(MimeMessage mimeMessage) {
        System.out.println("createMessage");

        Message message = new Message();
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            mimeMessage.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail =  Base64.encodeBase64URLSafeString(bytes);

            message.setRaw(encodedEmail);

        }
        catch (Exception ex) {
            System.out.println("An exception occurred [createMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return message;
    }

    public Message createDraft(Gmail service, String userId, MimeMessage mimeMessage) {
        System.out.println("sendMessage");

        Message message = null;
        Draft draft = null;

        try {
            message = this.createMessage(mimeMessage);
            draft = new Draft();
            draft.setMessage(message);
            draft = service.users().drafts().create(userId, draft).execute();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            mimeMessage.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail =  Base64.encodeBase64URLSafeString(bytes);

            message.setRaw(encodedEmail);

        }
        catch (Exception ex) {
            System.out.println("An exception occurred [sendMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return message;
    }

    @Override
    public Message updateDraft(Gmail service, String userId, MimeMessage mimeMessage, String draftId) {
        return null;
    }

    @Override
    public Message sendDraft(Gmail service, String userId, String draftId) {
        System.out.println("sendDraft");

        Message message = null;
        Draft draft = null;

        try {
            message = this.getDraft(this.getDrafts(HTTP_TRANSPORT), draftId);

//            System.out.println("message entrySet: " + message.entrySet());
//            System.out.println("message getSnippet: " + message.getSnippet());
            System.out.println("message: " + message);

            draft = new Draft();
            draft.setId(message.getId());
            draft.setMessage(message);
            System.out.println("draft id: " + draft.getId());
            System.out.println("draft message: " + draft.getMessage());
            message = service.users().drafts().send(userId, draft).execute();


        }
        catch (Exception ex) {
            System.out.println("An exception occurred [sendDraft], ex: " + ex);
            ex.printStackTrace();
        }
        return message;
    }

}
