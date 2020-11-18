package sample.com.server;

public class FormatMessages {
    private String nameAuthor;
    private String messageText;
    private final boolean messageFromAdministrator;

    public String getNameAuthor() {
        return nameAuthor;
    }

    public String getMessageText() {
        return messageText;
    }

    public boolean isMessageFromAdministrator(){
        return messageFromAdministrator;
    }

    public FormatMessages(String nameAuthor, String message, boolean messageFromAdministrator) {
        this.nameAuthor = nameAuthor;
        this.messageText = message;
        this.messageFromAdministrator = messageFromAdministrator;
    }

    public FormatMessages(String nameAuthor, String message) {
        this.nameAuthor = nameAuthor;
        this.messageText = message;
        this.messageFromAdministrator = false;
    }
}
