package tk.gengwai.waiapp.model;

import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by danielchan303 on 31/8/2016.
 */
public class ChatMessage {
    private String sender;
    private String message;
    private Object timestamp = ServerValue.TIMESTAMP;

    public ChatMessage() {
    }

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Object getTimestamp() {
        return timestamp;
    }
}
