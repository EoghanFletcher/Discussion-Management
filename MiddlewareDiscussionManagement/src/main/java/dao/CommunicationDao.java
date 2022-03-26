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
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
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
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, SCOPES_LABELS))
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
    public List<Message> inboxMessages(NetHttpTransport HTTP_TRANSPORT) {
        System.out.println("inboxMessages");


        List<Message> messagesList = null;
        Message messagesResponse = null;
        List<Message> returnList = null;
        Set<String> scopeSet = SCOPES_LABELS;

//        scopeSet.add(GmailScopes)

        try {

            { try { HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); } catch (GeneralSecurityException e) { e.printStackTrace(); } catch (IOException e) {  e.printStackTrace(); } }
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, scopeSet))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            ListMessagesResponse listMessagesResponse = service.users().messages().list(user).execute();
            messagesList = listMessagesResponse.getMessages();

            System.out.println("size: " + messagesList.size());

            if (!messagesList.isEmpty()) {
                returnList = new ArrayList<>();
            for (Message msg : messagesList) {
                    messagesResponse = service.users().messages().get("me", msg.getId()).execute();
//                System.out.println("\n");
//                    System.out.println("message: " + messagesResponse);
//                System.out.println("\n");

                    returnList.add(messagesResponse);
                }

                System.out.println("\n\n\n\n\n");
                System.out.println(returnList.get(0));
                System.out.println("\n\n\n\n\n");
//                System.out.println("\n\n0: " + returnList.get(0).getPayload().getHeaders().get(0));
//                System.out.println("\n\n1: " + returnList.get(0).getPayload().getHeaders().get(1));
//                System.out.println("\n\n2: " + returnList.get(0).getPayload().getHeaders().get(2));
//                System.out.println("\n\n3: " + returnList.get(0).getPayload().getHeaders().get(3));
//                System.out.println("\n\n4: " + returnList.get(0).getPayload().getHeaders().get(4));
//                System.out.println("\n\n5: " + returnList.get(0).getPayload().getHeaders().get(5));
//                System.out.println("\n\n6: " + returnList.get(0).getPayload().getHeaders().get(6));
//                System.out.println("\n\n7: " + returnList.get(0).getPayload().getHeaders().get(7));
//                System.out.println("\n\n8: " + returnList.get(0).getPayload().getHeaders().get(8));
//                System.out.println("\n\n9: " + returnList.get(0).getPayload().getHeaders().get(9));
//                System.out.println("\n\n10: " + returnList.get(0).getPayload().getHeaders().get(10));
            }
            System.out.println("returnList size: " + returnList.size());
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [inboxMessages], ex: " + ex);
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
                System.out.println("draftId: " + draftId + ", message Id: " + message.getId()); if (message.getId().equals(draftId)) { retrievedDraft = message; } }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getDrafts], ex: " + ex);
            ex.printStackTrace();
        }
        return retrievedDraft;
    }

    public Credential getCredentials(NetHttpTransport HTTP_TRANSPORT, Set<String> scopes) {
        System.out.println("getCredentials");
//        https://developers.google.com/gmail/api/quickstart/java


        GoogleClientSecrets clientSecrets = null;
        GoogleAuthorizationCodeFlow flow = null;
        LocalServerReceiver receiver = null;
        Credential credential = null;
        Set<String> SCOPES = scopes;
//        scopes.remove(GmailScopes.GMAIL_METADATA);

        System.out.println("scopes: " + scopes);
        try {
            // Load client secrets.
            System.out.println();
            Email.class.getResource(CREDENTIALS_FILE_PATH);
            InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
            if (in == null) { throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH); }
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

        Message message = null;
        Draft draft = null;
        MimeMessage mimeMessage = null;

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Map<String, String> draftData = null;

        try {
            message = this.getDraft(this.getDrafts(HTTP_TRANSPORT), draftId);
            Object test = message.getPayload().get("headers");
            List<MessagePartHeader> messagePartHeaderList = message.getPayload().getHeaders();

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
