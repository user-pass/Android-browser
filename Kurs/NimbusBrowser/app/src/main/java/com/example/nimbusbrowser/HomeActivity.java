package com.example.nimbusbrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rapidbrowser.R;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button SearchButtonHome;
    private EditText InputURL;
    private ImageButton google_btn;
    private ImageButton yandex_btn;
    private ImageButton duckgo_btn;
    private ImageButton bing_btn;
    private TextView say_hello_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        say_hello_txt = (TextView)  findViewById(R.id.say_hello);
        SearchButtonHome = (Button) findViewById(R.id.search_button_home);
        InputURL = (EditText) findViewById(R.id.search_url_home);
        google_btn = (ImageButton) findViewById(R.id.Google);
        yandex_btn = (ImageButton) findViewById(R.id.Yandex);
        duckgo_btn = (ImageButton) findViewById(R.id.Duck);
        bing_btn = (ImageButton) findViewById(R.id.bing);

        SearchButtonHome.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        yandex_btn.setOnClickListener(this);
        duckgo_btn.setOnClickListener(this);
        bing_btn.setOnClickListener(this);
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
        if (view == duckgo_btn) {
            Intent Open_duckgo = new Intent(HomeActivity.this, UrlSearch.class);
            Open_duckgo.putExtra("url_adress", "https://duckduckgo.com");
            startActivity(Open_duckgo);
        }
        if (view == bing_btn) {
            Intent Open_bing = new Intent(HomeActivity.this, UrlSearch.class);
            Open_bing.putExtra("url_adress", "https://www.bing.com");
            startActivity(Open_bing);
        }
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.home_menu_favorites:
                OpenFavorites();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void OpenWebSite() {

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

    private void OpenFavorites()
    {
        Intent open_favor = new Intent(HomeActivity.this, ViewFavorites.class);
        startActivity(open_favor);
    }


}