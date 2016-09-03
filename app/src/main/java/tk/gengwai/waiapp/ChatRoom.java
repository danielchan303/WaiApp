package tk.gengwai.waiapp;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import tk.gengwai.waiapp.model.ChatAdapter;
import tk.gengwai.waiapp.model.ChatMessage;

public class ChatRoom extends AppCompatActivity {
    public FirebaseDatabase database;
    DatabaseReference dbMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        dbMsg = database.getReference("message");

        // Set Button Listener
        Button chatConfirmBtn = (Button) findViewById(R.id.chat_confirm_btn);
        chatConfirmBtn.setOnClickListener(new chatConfirmBtnListener());

        // Create Adapter
        ChatAdapter chatAdapter = new ChatAdapter(this, ChatMessage.class, R.layout.list_item, dbMsg);
        ((ListView)findViewById(R.id.msg_list_view)).setAdapter(chatAdapter);
    }

    private class chatConfirmBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DatabaseReference key = dbMsg.push();
            ChatMessage msg = new ChatMessage("Daniel", getUserInput());
            key.setValue(msg);
        }
        private String getUserInput() {
            EditText chatMsgField = (EditText) findViewById(R.id.chat_msg_field);
            String msg = String.valueOf(chatMsgField.getText());
            chatMsgField.setText("");
            return msg;
        }
    }

}
