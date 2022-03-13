package business;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.GmailScopes;


import java.util.Collections;
import java.util.List;

public class Email {
    /** Application name. */
    public static final String APPLICATION_NAME = "DiscussionManagement";
    /** Global instance of the JSON factory. */
    public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    public static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    // Add more scopes
    public static final List<String> SCOPES_LABELS = Collections.singletonList(GmailScopes.GMAIL_LABELS);
    public static final String CREDENTIALS_FILE_PATH = "./keys/client_secret_691585599545-g0hg3r59jemdgse06f7q8p9skdnlt4uj.apps.googleusercontent.com.json";
}
