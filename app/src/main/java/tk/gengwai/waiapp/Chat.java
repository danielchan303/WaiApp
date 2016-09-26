package tk.gengwai.waiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import tk.gengwai.waiapp.model.ChatDialog;
import tk.gengwai.waiapp.model.Contract;
import tk.gengwai.waiapp.model.ConvertTime;

public class Chat extends AppCompatActivity implements ChatDialog.DialogListener {

    private DatabaseReference chatroom;
    private Context context;
    private ChatAdapter chatAdapter;
    static ProgressBar listProgressBar;
    TextView noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context = getApplicationContext();
        listProgressBar = (ProgressBar) findViewById(R.id.list_progress_bar);
        noInternet = (TextView) findViewById(R.id.no_internet);

        // Initialize App Bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean notConnected = !(activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());
        if (notConnected) {
            listProgressBar.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);

        }

        // Database
        ListView chatroomListView = (ListView) findViewById(R.id.chatroom_list_view);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        chatroom = database.getReference(Contract.FIREBASE_CHATROOM);
        chatAdapter = new ChatAdapter(this, Room.class, R.layout.list_chat_room_item, chatroom);
        chatroomListView.setAdapter(chatAdapter);
        chatroomListView.setOnItemClickListener(new ListOnItemClickListener());
    }

    // App Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appbar_add_chat_room:
                ChatDialog dialog = new ChatDialog();
                FragmentManager fm = getSupportFragmentManager();
                dialog.show(fm, "dialogAddChatRoom");
                return true;
            default:
                return false;
        }
    }

    // Dialog
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String userInput) {
        if (!userInput.equals("")) {
            Room newChatRoom = new Room(userInput, "Anomynous");
            chatroom.push().setValue(newChatRoom);
            Toast.makeText(context, userInput + " 已新增", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "名稱不能為空白", Toast.LENGTH_SHORT).show();
        }
    }

    // ChatAdapter
    public class ChatAdapter extends FirebaseListAdapter<Room> {

        public ChatAdapter(Activity activity, Class<Room> modelClass, int modelLayout, Query ref) {
            super(activity, modelClass, modelLayout, ref);
        }

        @Override
        protected void populateView(View view, Room chatRoom, int position) {
            TextView chatroomName = (TextView) view.findViewById(R.id.chatroom_name);
            TextView chatroomLastUpdated = (TextView) view.findViewById(R.id.chatroom_last_updated);
            chatroomName.setText(chatRoom.getName());
            String lastUpdated = context.getString(R.string.lastUpdated) +
                    ConvertTime.convertTime(((Long) chatRoom.getLastUpdated()));
            chatroomLastUpdated.setText(lastUpdated);

            listProgressBar.setVisibility(View.GONE);
            noInternet.setVisibility(View.GONE);
        }
    }

    public class ListOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            String key = chatAdapter.getRef(position).getKey();
            Intent chatroomIntent = new Intent(context, ChatWindow.class);
            chatroomIntent.putExtra(Contract.EXTRA_NAME_CHATROOM,
                    chatAdapter.getItem(position).getName());
            chatroomIntent.putExtra(Contract.EXTRA_KEY_CHATROOM, key);
            startActivity(chatroomIntent);
        }
    }

    public static class Room {
        private String name;
        private String owner;
        private Object lastUpdated = ServerValue.TIMESTAMP;

        public Room() {
        }

        public Room(String name, String owner) {
            this.name = name;
            this.owner = owner;
        }

        public String getOwner() {
            return owner;
        }

        public Object getLastUpdated() {
            return lastUpdated;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    protected void onDestroy() {
        chatAdapter.cleanup();
        super.onDestroy();
    }
}
