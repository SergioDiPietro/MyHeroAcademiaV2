package com.example.myheroacademiav2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myheroacademiav2.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText heroIdBox;
    TextView urlDisplay;
    TextView resultDisplay;
    TextView errorDisplay;
    ProgressBar loadingDisplay;

    public class HeroesQueryTask extends AsyncTask<URL, Void, String> {

        protected void onPreExecute() {
            loadingDisplay.setVisibility(View.VISIBLE);
            resultDisplay.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String heroSearchResolve = null;

            try {
                heroSearchResolve = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return heroSearchResolve;
        }

        @Override
        protected void onPostExecute(String resolve) {
            loadingDisplay.setVisibility(View.INVISIBLE);
            resultDisplay.setVisibility(View.VISIBLE);
            if (resolve != null && !resolve.equals("") && !resolve.equals(" ")) {
                showJsonData();
                resultDisplay.setText(resolve);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    private void showJsonData() {
        errorDisplay.setVisibility(View.INVISIBLE);
        resultDisplay.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        resultDisplay.setVisibility(View.INVISIBLE);
        errorDisplay.setVisibility(View.VISIBLE);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.launch) {
            URL heroesApiUrl;
            if (heroIdBox.getText().toString().isEmpty()) {
                heroesApiUrl = NetworkUtils.generateUrl();
            } else {
                heroesApiUrl = NetworkUtils.generateUrl(heroIdBox.getText().toString());
            }
            urlDisplay.setText(heroesApiUrl.toString());

            new HeroesQueryTask().execute(heroesApiUrl);
        } else if (itemId == R.id.clear) {
            heroIdBox.setText("");
            urlDisplay.setText(R.string.url_display_placeholder);
            resultDisplay.setText(R.string.result_placeholder);
            showJsonData();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        heroIdBox = (EditText)findViewById(R.id.hero_id);
        urlDisplay = (TextView) findViewById(R.id.url_display);
        resultDisplay = (TextView) findViewById(R.id.result_display);
        errorDisplay = (TextView) findViewById(R.id.error_display);
        loadingDisplay = (ProgressBar) findViewById(R.id.loading_display);
    }
}