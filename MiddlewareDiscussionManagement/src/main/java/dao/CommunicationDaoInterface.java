package dao;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.model.Message;

import java.util.List;

public interface CommunicationDaoInterface {

    public List<Message> getDrafts(NetHttpTransport HTTP_TRANSPORT);

    public Credential getCredentials(NetHttpTransport HTTP_TRANSPORT);
}
