package com.middleware.middlewarediscussionmanagement;

import business.Email;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;
import dao.CommunicationDao;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import static business.Email.APPLICATION_NAME;
import static business.Email.JSON_FACTORY;

@RestController
@RequestMapping(path = "/api/communication")
public class CommunicationController {

    CommunicationDao communicationDao = new CommunicationDao();

    @GetMapping(path = "/getDrafts")
    public List<Message> getDrafts() {
        System.out.println("getDrafts");

        List<Message> messageList = null;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            messageList = communicationDao.getDrafts(HTTP_TRANSPORT);
        }
        catch (Exception ex) {
                System.out.println("An exception occurred [getDrafts], ex: " + ex);
                ex.printStackTrace();
            }
        return messageList;
    }

    @PostMapping(path = "/credentials")
    public void getCredentials(@RequestBody HashMap data) {
        System.out.println("getCredentials");

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            System.out.println(communicationDao.getCredentials(HTTP_TRANSPORT, Email.SCOPES_LABELS));
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
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, communicationDao.getCredentials(HTTP_TRANSPORT, Email.SCOPES_LABELS))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            String to = (String) data.get("to");
            String from = (String) data.get("from");
            String subject = (String) data.get("subject");
            String body = (String) data.get("body");

            Draft draft = communicationDao.createDraft(service, from,
                    this.communicationDao.createMineMessage(data, HTTP_TRANSPORT));
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
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, communicationDao.getCredentials(HTTP_TRANSPORT, Email.SCOPES_LABELS))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

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

    @PutMapping(path = "message")
    public List<Message> getMessage(@RequestBody HashMap data) {
        System.out.println("getMessage");

        List<Message> messageList = null;

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            String messageType = (String) data.get("messageType");

            messageList = this.communicationDao.inboxSentMessages(HTTP_TRANSPORT, messageType);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getMessage], ex: " + ex);
            ex.printStackTrace();
        }
        return messageList;
    }
}
