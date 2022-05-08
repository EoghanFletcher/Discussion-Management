package dao;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CommunicationDaoInterface {

    public List<Message> getDrafts(NetHttpTransport HTTP_TRANSPORT);
    public Message getDraft(List<Message> messageList, String draftId);

    public Credential getCredentials(NetHttpTransport HTTP_TRANSPORT, Set<String> scopes);

    public MimeMessage createMineMessage(Map draftData, NetHttpTransport HTTP_TRANSPORT);

    public Message createMessage(MimeMessage mimeMessage);

    public Draft createDraft(Gmail service, String userId, MimeMessage mimeMessage);

    public Message sendDraft(Gmail service, String userId, String draftId);

    public List<Message> inboxSentMessages(NetHttpTransport HTTP_TRANSPORT, String messageLabel);

}
