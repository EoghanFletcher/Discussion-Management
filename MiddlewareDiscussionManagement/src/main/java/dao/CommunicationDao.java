package dao;

import business.Email;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

import static business.Email.*;

public class CommunicationDao implements CommunicationDaoInterface {

    Email email = new Email();

    public void getEmails(String email, String accessToken) {
        System.out.println("getEmails");

        final String uri = "https://gmail.googleapis.com/gmail/v1/users/" + email + "/drafts";

        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            System.out.println("result: " + result);
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getEmails], ex: " + ex);
            ex.printStackTrace();
        }
    }

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) {
        System.out.println("getCredentials");
//        https://developers.google.com/gmail/api/quickstart/java


        GoogleClientSecrets clientSecrets = null;
        GoogleAuthorizationCodeFlow flow = null;
        LocalServerReceiver receiver = null;
        Credential credential = null;
        List<String> SCOPES = SCOPES_LABELS;

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
            receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
            //returns an authorized Credential object.
        }
        catch (Exception ex) {
            System.out.println("An exception occurred [getCredentials], ex: " + ex);
            ex.printStackTrace();
        }
        return credential;
    }

}
