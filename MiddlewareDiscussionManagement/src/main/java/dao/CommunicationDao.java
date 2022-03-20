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
//                    System.out.println("raw: " + message.getRaw());
//                    System.out.println("Yes, current draft id : " + message.getId() + ", draftId: " + draftId);
                    retrievedDraft = message;
                }
                else {
//                    System.out.println("no, current draft id : " + message.getId() + ", draftId: " + draftId);
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
        MimeMessage mineMessage = new MimeMessage(session);

        try {
            mineMessage.setFrom(new InternetAddress(draftData.get("from").toString()));
            mineMessage.addRecipient(javax.mail.Message.RecipientType.TO,
                    new InternetAddress(draftData.get("to").toString()));
            mineMessage.setSubject(draftData.get("subject").toString());
            mineMessage.setText(draftData.get("body").toString());


        }
        catch (Exception ex) {
            System.out.println("An exception occurred [createMineMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return mineMessage;
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

    public Draft createDraft(Gmail service, String userId, MimeMessage mimeMessage) {
        System.out.println("sendMessage");

        Message message = null;
        Draft draft = null;

        try {
            message = this.createMessage(mimeMessage);
            draft = new Draft();
            draft.setMessage(message);
            draft = service.users().drafts().create(userId, draft).execute();
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [sendMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return draft;
    }

    @Override
    public Message updateDraft(Gmail service, String userId, MimeMessage mimeMessage, String draftId) {
        return null;
    }

    @Override
    public Message sendDraft(Gmail service, String userId, String draftId) {
        System.out.println("sendDraft");
        System.out.println("||||");

        Message message = null;
        Draft draft = null;
        MimeMessage mimeMessage = null;


        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Map<String, String> draftData = null;

        try {
            message = this.getDraft(this.getDrafts(HTTP_TRANSPORT), draftId);

            System.out.println("MESSAGE: " + message.toPrettyString());

            System.out.println("MESSAGE RAW: " + message.getRaw());

            Object test = message.getPayload().get("headers");

            System.out.println("test: " + test);

            System.out.println("Header parts: " + message.getPayload().getHeaders());

            System.out.println("Header parts: " + message.getPayload().getHeaders());
            List<MessagePartHeader> messagePartHeaderList = message.getPayload().getHeaders();
            System.out.println("messagePartHeaderList.get(0): " + messagePartHeaderList.get(0));
            System.out.println("messagePartHeaderList.get(0).name: " + messagePartHeaderList.get(0).getName());
            System.out.println("messagePartHeaderList.get(0).value: " + messagePartHeaderList.get(0).getValue());
            System.out.println("messagePartHeaderList.get(1): " + messagePartHeaderList.get(1));
            System.out.println("messagePartHeaderList.get(1).name: " + messagePartHeaderList.get(1).getName());
            System.out.println("messagePartHeaderList.get(1).value: " + messagePartHeaderList.get(1).getValue());
            System.out.println("messagePartHeaderList.get(2): " + messagePartHeaderList.get(2));
            System.out.println("messagePartHeaderList.get(2).name: " + messagePartHeaderList.get(2).getName());
            System.out.println("messagePartHeaderList.get(2).value: " + messagePartHeaderList.get(2).getValue());
            System.out.println("messagePartHeaderList.get(3): " + messagePartHeaderList.get(3));
            System.out.println("messagePartHeaderList.get(3).name: " + messagePartHeaderList.get(3).getName());
            System.out.println("messagePartHeaderList.get(3).value: " + messagePartHeaderList.get(3).getValue());
            System.out.println("messagePartHeaderList.get(4): " + messagePartHeaderList.get(4));
            System.out.println("messagePartHeaderList.get(5): " + messagePartHeaderList.get(5));
            System.out.println("messagePartHeaderList.get(5).name: " + messagePartHeaderList.get(5).getName());
            System.out.println("messagePartHeaderList.get(5).value: " + messagePartHeaderList.get(5).getValue());
            System.out.println("messagePartHeaderList.get(6): " + messagePartHeaderList.get(6));
            System.out.println("messagePartHeaderList.get(7): " + messagePartHeaderList.get(7));

            draftData = new HashMap<>();
            draftData.put("to", messagePartHeaderList.get(3).getValue());
            draftData.put("from", messagePartHeaderList.get(2).getValue());
            draftData.put("subject", messagePartHeaderList.get(5).getValue());
            draftData.put("body", message.getSnippet());

            mimeMessage = this.createMineMessage(draftData, HTTP_TRANSPORT);
            message = this.createMessage(mimeMessage);
            message.setId(draftId);


            message = service.users().messages().send(userId, message).execute();

        }
        catch (Exception ex) {
            System.out.println("An exception occurred [sendDraft], ex: " + ex);
            ex.printStackTrace();
        }
        return message;
    }

}
