package com.middleware.middlewarediscussionmanagement;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import dao.CommunicationDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

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

}
