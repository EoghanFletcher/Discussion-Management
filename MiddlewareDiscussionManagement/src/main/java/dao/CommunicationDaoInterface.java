package dao;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;

public interface CommunicationDaoInterface {

    public void getEmails(String email, String accessToken);

    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT);
}
