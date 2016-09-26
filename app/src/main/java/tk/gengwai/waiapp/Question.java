package tk.gengwai.waiapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Question extends AppCompatActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        context = getApplicationContext();

        //  Set the OnClickListener to the waiImg
        ImageView waiImg = (ImageView) findViewById(R.id.waiImg);
        waiImg.setOnClickListener(new rightAns());
        // Set the OnClickListener to the tigerImg
        ImageView tigerImg = (ImageView) findViewById(R.id.tigerImg);
        tigerImg.setOnClickListener(new wrongAns());
        // Set the OnClickListener to the tortoriseImg
        ImageView tortoiseImg = (ImageView) findViewById(R.id.tortoiseImg);
        tortoiseImg.setOnClickListener(new wrongAns());
    }

    private final class wrongAns implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, R.string.wrong_ans, Toast.LENGTH_SHORT).show();
            view.setVisibility(View.INVISIBLE);
        }
    }

    private final class rightAns implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.activity_correct_answer);
        }
    }
}
