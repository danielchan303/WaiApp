package tk.gengwai.waiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class correctAns extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_ans);
        Button nextStage = (Button) findViewById(R.id.next_stage);
        nextStage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comeFrom = getIntent().getExtras().getString("comeFrom");
                if (comeFrom.equals("main")) {
                    Intent feedWaiActivity = new Intent (correctAns.this, feed_wai.class);
                    startActivity(feedWaiActivity);
                }
            }
        });
    }
}
