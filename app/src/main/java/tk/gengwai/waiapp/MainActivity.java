package tk.gengwai.waiapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private class getComment extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        @Override
        protected String doInBackground(Void... params) {
            try {
                return downloadUrl();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Async Test", result);
        }
    }

    private String downloadUrl() throws IOException {
        InputStream is = null;
        URL url = new URL ("http://www.gengwai.tk/php/vid-action.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Start the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DEBUG_TAG", "The response is" + response);
            is = conn.getInputStream();

            // Convert the InputStream into a sstring
            String contentAsString = readIt(is, 45);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public boolean checkInternetConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            return true;
        } else {
            // display error
            return false;
        }
    }


    // Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Menu Item Click Actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case (R.id.aboutAuthor):
                Toast.makeText(this, "Prouldy created by Daniel Chan", Toast.LENGTH_SHORT).show();
                if (checkInternetConnected()) {
                    Log.v("Network Status", "Connected to the Internet");
                    new getComment().execute();
                } else {
                    Log.v("Network Status", "Connection Error.");
                }
                return true;
            case (R.id.people):
                Intent callPeopleActivity = new Intent(MainActivity.this, People.class);
                this.startActivity(callPeopleActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
