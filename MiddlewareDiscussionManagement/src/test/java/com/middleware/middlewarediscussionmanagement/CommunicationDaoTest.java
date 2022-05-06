package com.middleware.middlewarediscussionmanagement;

import business.Email;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import dao.CommunicationDao;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static business.Email.APPLICATION_NAME;
import static business.Email.JSON_FACTORY;

public class CommunicationDaoTest {

    private static NetHttpTransport HTTP_TRANSPORT = null;
    private static CommunicationDao communicationDao  = null;
    private List<Message> messagesList = null;
    MimeMessage mimeMessage = null;
    Message message = null;
    Map<String, String> draftData =  null;
    Draft draft = null;

    @BeforeClass
    public static void setupData() {
        System.out.println("setupData");

        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            communicationDao = new CommunicationDao();
        } catch (Exception ex) {
            System.out.println("An error occurred [setupData], ex: " + ex);
            ex.printStackTrace();
        }
    }

        @Test
        public void getCredential() {
            System.out.println("getCredential");

            try {
                Assert.assertNotNull(communicationDao.getCredentials(HTTP_TRANSPORT, Email.SCOPES_LABELS));
            }
            catch(Exception ex) {
                System.out.println("An error occurred [getCredential[Test]], ex: " + ex);
                ex.printStackTrace();
            }
        }

        @Test
        public void getDrafts() {
            System.out.println("getDrafts");

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, communicationDao.getCredentials(HTTP_TRANSPORT, Email.SCOPES_LABELS))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            try {
                System.out.println("==========");
                System.out.println("populate");
                System.out.println("==========");


                draftData = new HashMap<>();
                draftData.put("to", Email.emailAddress);
                draftData.put("from", Email.emailAddress);
                draftData.put("subject", "gmail API draft");
                draftData.put("body", "testDataffffff");

                mimeMessage = communicationDao.createMineMessage(draftData, HTTP_TRANSPORT);
                message = communicationDao.createMessage(mimeMessage);
                draft = communicationDao.createDraft(service, "me", mimeMessage);

                System.out.println("==========");
                System.out.println("getDrafts");
                System.out.println("==========");

                messagesList = communicationDao.getDrafts(HTTP_TRANSPORT);
                Assert.assertNotNull(messagesList);
                Assert.assertEquals("testDataffffff", messagesList.get(0).get("snippet"));

                System.out.println("==========");
                System.out.println("getDraft");
                System.out.println("==========");

                message = communicationDao.getDraft(messagesList, messagesList.get(0).getId());

                System.out.println("message: " + message);
                System.out.println("message id: " + message.getId());

                Assert.assertNotNull(message);
                Assert.assertEquals(messagesList.get(0).getId(), message.getId());
            }
            catch(Exception ex) {
                System.out.println("An error occurred [getDrafts[Test]], ex: " + ex);
                ex.printStackTrace();
            }
        }

        @Test
        public void sendMessage() {
            System.out.println("sendMessage");

            draftData = new HashMap<>();

            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, communicationDao.getCredentials(HTTP_TRANSPORT, Email.SCOPES_LABELS))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            try {
                System.out.println("==========");
                System.out.println("createMineMessage");
                System.out.println("==========");

                draftData = new HashMap<>();
                draftData.put("to", Email.emailAddress);
                draftData.put("from", Email.emailAddress);
                draftData.put("subject", "Gmail API Test");
                draftData.put("body", "Test Data \n I hope this goes to my email address");

                mimeMessage = communicationDao.createMineMessage(draftData, HTTP_TRANSPORT);

                Assert.assertEquals(Email.emailAddress, mimeMessage.getFrom()[0].toString());
                Assert.assertEquals(Email.emailAddress, mimeMessage.getAllRecipients()[0].toString());
                Assert.assertEquals("Gmail API Test", mimeMessage.getSubject());
                Assert.assertEquals("Test Data \n I hope this goes to my email address", mimeMessage.getContent());

                System.out.println("==========");
                System.out.println("createMessage");
                System.out.println("==========");

                message = communicationDao.createMessage(mimeMessage);

                Assert.assertNotNull(message);
                Assert.assertTrue(message.getRaw() instanceof String);

                System.out.println("==========");
                System.out.println("createDraft");
                System.out.println("==========");

                draft = communicationDao.createDraft(service, "me", mimeMessage);

                Assert.assertNotNull(message);
                Assert.assertTrue(message.getRaw() instanceof String);

                System.out.println("==========");
                System.out.println("sendDraft");
                System.out.println("==========");

                messagesList = communicationDao.getDrafts(HTTP_TRANSPORT);
                message = communicationDao.getDraft(messagesList, messagesList.get(0).getId());

                message = communicationDao.sendDraft(service, "me", message.getId());
            } catch (Exception ex) {
                System.out.println("An error occurred [sendMessage[Test]], ex: " + ex);
                ex.printStackTrace();
            }
        }
}
