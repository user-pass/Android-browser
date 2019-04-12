package com.example.nimbusbrowser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rapidbrowser.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button SearchButtonHome;
    private EditText InputURL;
    private ImageButton google_btn;
    private ImageButton yandex_btn;
    private ProgressDialog Loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Loadingbar = new ProgressDialog(this);

        SearchButtonHome = (Button) findViewById(R.id.search_button_home);
        InputURL = (EditText) findViewById(R.id.search_url_home);
        google_btn = (ImageButton) findViewById(R.id.Google);
        yandex_btn = (ImageButton) findViewById(R.id.Yandex);

        Loadingbar.setTitle("Loading...");
        Loadingbar.setMessage("Welcome to Nimbus Browser!");
        Loadingbar.show();


        SearchButtonHome.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        yandex_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == SearchButtonHome) {
            OpenWebSite();
        }

        if (view == google_btn) {
            Intent Open_google = new Intent(HomeActivity.this, UrlSearch.class);
            Open_google.putExtra("url_adress", "https://www.google.com");
            startActivity(Open_google);
        }

        if (view == yandex_btn) {
            Intent Open_yandex = new Intent(HomeActivity.this, UrlSearch.class);
            Open_yandex.putExtra("url_adress", "https://www.yandex.com");
            startActivity(Open_yandex);
        }

    }

    private void OpenWebSite() {
        Loadingbar.setTitle("Loading...");
        Loadingbar.setMessage("Please wait while we are opening requested website.");
        Loadingbar.show();

        String Url_Address = InputURL.getText().toString();

        if (TextUtils.isEmpty(Url_Address)) {
            Toast empty = Toast.makeText(HomeActivity.this, "Please enter URL or Web Address", Toast.LENGTH_LONG);
            empty.show();
        } else {
            String url_Without_https = Url_Address.replaceAll("https://www.", "");
            String https = "https://";
            String www = "www.";

            Intent search = new Intent(HomeActivity.this, UrlSearch.class);
            search.putExtra("url_adress", https + www + url_Without_https);
            startActivity(search);

            InputURL.setText("");
            InputURL.requestFocus();
        }
    }


}