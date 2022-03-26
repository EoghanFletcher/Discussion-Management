package business;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.GmailScopes;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
//    public static final List<String> SCOPES_LABELS = Collections.singletonList(GmailScopes.GMAIL_LABELS);
      public static final Set<String> SCOPES_LABELS = getScope();
    public static final String CREDENTIALS_FILE_PATH = "./keys/client_secret_691585599545-g0hg3r59jemdgse06f7q8p9skdnlt4uj.apps.googleusercontent.com.json";

    public static Set<String> getScope() {
        Set<String> scopeSet = new HashSet<>();
        scopeSet.add(GmailScopes.GMAIL_READONLY);
        scopeSet.add(GmailScopes.GMAIL_SEND);
        scopeSet.add(GmailScopes.MAIL_GOOGLE_COM);
        scopeSet.add(GmailScopes.GMAIL_MODIFY);
        scopeSet.add(GmailScopes.GMAIL_INSERT);
        scopeSet.add(GmailScopes.GMAIL_SETTINGS_SHARING);
        scopeSet.add(GmailScopes.GMAIL_LABELS);
        scopeSet.add(GmailScopes.GMAIL_SETTINGS_BASIC);
        scopeSet.add(GmailScopes.GMAIL_COMPOSE);



        return scopeSet;
    }
}
