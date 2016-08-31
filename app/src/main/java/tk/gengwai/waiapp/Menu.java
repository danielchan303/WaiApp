package tk.gengwai.waiapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button btnMenuQuestion, btnMenuFeed, btnMenuLove, btnMenuChat, btnMenuAbout;
    private Intent intentQuestion, intentFeed, intentLove,intentChat, intentAbout;

    class intentOnClick implements View.OnClickListener  {
        private Intent intent;
        public intentOnClick(Intent intent) {
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

        // Context
        Context context = getApplicationContext();

        // Button
        btnMenuQuestion = (Button) findViewById(R.id.btnMenuQuestion);
        btnMenuFeed = (Button) findViewById(R.id.btnMenuFeed);
        btnMenuLove = (Button) findViewById(R.id.btnMenuLove);
        btnMenuChat = (Button) findViewById(R.id.btnMenuChat);

        // Intent
        intentQuestion = new Intent(context, Question.class);
        intentFeed = new Intent(context, Feed.class);
        intentLove = new Intent(context, Love.class);
        intentChat = new Intent(context, Chat.class);

        // Button OnClick Listener
        btnMenuQuestion.setOnClickListener(new intentOnClick(intentQuestion));

        btnMenuFeed.setOnClickListener(new intentOnClick(intentFeed));

        btnMenuLove.setOnClickListener(new intentOnClick(intentLove));

        btnMenuChat.setOnClickListener(new intentOnClick(intentChat));
    }
}
