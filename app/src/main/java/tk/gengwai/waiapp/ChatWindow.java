package tk.gengwai.waiapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import tk.gengwai.waiapp.model.Contract;
import tk.gengwai.waiapp.model.ConvertTime;

public class ChatWindow extends AppCompatActivity {
    public FirebaseDatabase database;
    static String chatRoomKey;
    static ChatMessageAdapter chatMessageAdapter;
    static TextView noMsgTextView;
    DatabaseReference msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        noMsgTextView = (TextView) findViewById(R.id.no_msg_text_view);

        // Obtain the chatRoomKey from the ChatActivity.class
        Intent intent = getIntent();
        chatRoomKey = intent.getStringExtra(Contract.EXTRA_KEY_CHATROOM);
        String title = intent.getStringExtra(Contract.EXTRA_NAME_CHATROOM);
        setTitle(title);

        // Initialize App Bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_room_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        msg = database.getReference(Contract.getFirebaseMessage(chatRoomKey));


        // Set Button Listener
        Button chatConfirmBtn = (Button) findViewById(R.id.chat_confirm_btn);
        chatConfirmBtn.setOnClickListener(new chatConfirmBtnListener());

        // Create Adapter
        chatMessageAdapter =
                new ChatMessageAdapter(this, ChatMessage.class, R.layout.list_item,
                        database.getReference(Contract.getFirebaseMessage(chatRoomKey)));
        ((ListView)findViewById(R.id.msg_list_view)).setAdapter(chatMessageAdapter);
    }

    // App Bar
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appbar_delete_chat_room:
                HashMap<String, Object> deleteChatRoom = new HashMap<>();
                deleteChatRoom.put(Contract.getFirebaseChatroom(chatRoomKey), null);
                deleteChatRoom.put(Contract.getFirebaseMessage(chatRoomKey), null);
                database.getReference().updateChildren(deleteChatRoom);
                finish();
                return true;
            case R.id.appbar_edit_chat_room:
                HashMap<String, Object> changeChatRoomName = new HashMap<>();

                return true;
            default:
                return false;
        }
    }

    // Button
    private class chatConfirmBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String key = msg.push().getKey();
            ChatMessage chatMessage = new ChatMessage("Anonymous", getUserInput());
            HashMap<String, Object> newMsg = new HashMap<>();
            newMsg.put(Contract.getFirebaseChatroomLastUpdated(chatRoomKey), ServerValue.TIMESTAMP);
            newMsg.put(Contract.getFirebaseMessage(chatRoomKey) + key, new ObjectMapper().convertValue(chatMessage, Map.class));
            database.getReference().updateChildren(newMsg);
        }
        private String getUserInput() {
            EditText chatMsgField = (EditText) findViewById(R.id.chat_msg_field);
            String msg = String.valueOf(chatMsgField.getText());
            chatMsgField.setText("");
            return msg;
        }
    }

    public static class ChatMessageAdapter extends FirebaseListAdapter<ChatMessage> {
        public ChatMessageAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref) {
            super(activity, modelClass, modelLayout, ref);
        }

        @Override
        protected void populateView(View view, ChatMessage model, int position) {
            String time = ConvertTime.convertTime(((Long) model.getTimestamp()));
            ((TextView) view.findViewById(R.id.sender)).setText(model.getSender() + " " + time);
            ((TextView) view.findViewById(R.id.message)).setText(model.getMessage());

            if (chatMessageAdapter.isEmpty()) {
                noMsgTextView.setVisibility(View.VISIBLE);
            } else {
                noMsgTextView.setVisibility(View.GONE);
            }
        }
    }

    public static class ChatMessage {
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

    @Override
    protected void onDestroy() {
        chatMessageAdapter.cleanup();
        super.onDestroy();
    }
}
