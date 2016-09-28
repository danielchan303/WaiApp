package tk.gengwai.waiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    class intentOnClick implements View.OnClickListener  {
        private Intent intent;
        intentOnClick(Intent intent) {
            this.intent = intent;
        }

        @Override
        public void onClick(View view) {
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnMenuQuestion, btnMenuFeed, btnMenuLove, btnMenuChat;
        final Intent intentQuestion, intentFeed, intentLove,intentChat, intentLogin;
        final FirebaseUser user;

        // Context
        Context context = getApplicationContext();

        // Button
        btnMenuQuestion = (Button) findViewById(R.id.btnMenuQuestion);
        btnMenuFeed = (Button) findViewById(R.id.btnMenuFeed);
        btnMenuLove = (Button) findViewById(R.id.btnMenuLove);
        btnMenuChat = (Button) findViewById(R.id.btnMenuChat);

        // Intent
        intentQuestion = new Intent(context, QuestionActivity.class);
        intentFeed = new Intent(context, FeedActivity.class);
        intentLove = new Intent(context, LoveActivity.class);
        intentChat = new Intent(context, ChatActivity.class);
        intentLogin = new Intent(context, LoginActivity.class);

        // Button OnClick Listener
        btnMenuQuestion.setOnClickListener(new intentOnClick(intentQuestion));
        btnMenuFeed.setOnClickListener(new intentOnClick(intentFeed));
        btnMenuLove.setOnClickListener(new intentOnClick(intentLove));
        btnMenuChat.setOnClickListener(new intentOnClick(intentLogin));
    }
}
