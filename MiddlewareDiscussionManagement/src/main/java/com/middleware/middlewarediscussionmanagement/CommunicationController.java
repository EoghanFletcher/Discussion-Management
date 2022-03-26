package com.middleware.middlewarediscussionmanagement;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import dao.CommunicationDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static business.Email.APPLICATION_NAME;
import static business.Email.JSON_FACTORY;

@RestController
@RequestMapping(path = "/api/communication")
public class CommunicationController {

    CommunicationDao communicationDao = new CommunicationDao();
//    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//    String databaseCollection = "User";




    @PostMapping(path = "/getDrafts")
    public void getDrafts(@RequestBody HashMap data) {
        System.out.println("getEmails");

        System.out.println("data: " + data.entrySet());

        try {
            String emailString = (String) data.get("email");
            String usernameString = (String) data.get("username");
            String accessTokenString = (String) data.get("accessToken");

            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            communicationDao.getDrafts(HTTP_TRANSPORT);


//            System.out.println(communicationDao.getCredentials(HTTP_TRANSPORT));

        }
        catch (Exception ex) {
                System.out.println("An exception occurred [getEmails], ex: " + ex);
                ex.printStackTrace();
            }
    }

    @PostMapping(path = "/credentials")
    public void getCredentials(@RequestBody HashMap data) {
        System.out.println("getCredentials");

        System.out.println("data: " + data.entrySet());

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            System.out.println(communicationDao.getCredentials(HTTP_TRANSPORT));
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getCredentials], ex: " + ex);
            ex.printStackTrace();
        }
    }

    @PostMapping(path = "draft")
    public boolean createDraft(@RequestBody HashMap data) {
        System.out.println("createDraft");

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, communicationDao.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            System.out.println("data: " + data.entrySet());

            String to = (String) data.get("to");
            String from = (String) data.get("from");
            String subject = (String) data.get("subject");
            String body = (String) data.get("body");

            Draft draft = communicationDao.createDraft(service, from,
                    this.communicationDao.createMineMessage(data, HTTP_TRANSPORT));

            System.out.println("draft: id " + draft.getId());
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [createDraft], ex: " + ex);
            ex.printStackTrace();
        }
        return false;
    }

    @PostMapping(path = "message")
    public boolean sendMessage(@RequestBody HashMap data) {
        System.out.println("sendMessage");

        boolean result = false;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, communicationDao.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            System.out.println("data: " + data.entrySet());

            String to = (String) data.get("to");
            String from = (String) data.get("from");
            String subject = (String) data.get("subject");
            String body = (String) data.get("body");

            Draft draft = communicationDao.createDraft(service, from,
                    this.communicationDao.createMineMessage(data, HTTP_TRANSPORT));

            Message message = communicationDao.getDraft(communicationDao.getDrafts(HTTP_TRANSPORT),
                    communicationDao.getDrafts(HTTP_TRANSPORT).get(0).getId());

            message = communicationDao.sendDraft(service, from, message.getId());

            if (message != null && message.getId() != null) { result = true; }
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [sendMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return false;
    }

}
