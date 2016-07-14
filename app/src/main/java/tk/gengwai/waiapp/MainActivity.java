package tk.gengwai.waiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getComment();

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
            Toast.makeText(getApplicationContext(), R.string.wrong_ans, Toast.LENGTH_SHORT).show();
            view.setVisibility(View.INVISIBLE);
        }
    }

    private final class rightAns implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent correctAnsActivity = new Intent(MainActivity.this, correctAns.class);
            correctAnsActivity.putExtra("comeFrom", "main");
            startActivity(correctAnsActivity);
            reset();
        }
    }

    public void reset() {
        ImageView tigerImg = (ImageView) findViewById(R.id.tigerImg);
        tigerImg.setVisibility(View.VISIBLE);
        ImageView tortoiseImg = (ImageView) findViewById(R.id.tortoiseImg);
        tortoiseImg.setVisibility(View.VISIBLE);

    }

    private void getComment() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("http://www.gengwai.tk/php/vid-action.php");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

}
