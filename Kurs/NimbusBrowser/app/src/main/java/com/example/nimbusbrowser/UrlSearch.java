package com.example.nimbusbrowser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rapidbrowser.R;

public class UrlSearch extends AppCompatActivity implements View.OnClickListener{

    private Button SearchUrlButton;
    private EditText UrlInput;
    private WebView SearchWebAddress;
    private ProgressDialog Loadingbar;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_search);

        SearchUrlButton = (Button) findViewById(R.id.search_url_button);
        UrlInput = (EditText) findViewById(R.id.input_search_url);
        SearchWebAddress = (WebView) findViewById(R.id.SearchWebsite);

        url = getIntent().getExtras().get("url_adress").toString();
        UrlInput.setText(url);

        Loadingbar = new ProgressDialog(this);

        WebSettings webSettings = SearchWebAddress.getSettings();
        webSettings.setJavaScriptEnabled(true);
        SearchWebAddress.loadUrl(url);
        SearchWebAddress.setWebViewClient(new WebViewClient());

        Loadingbar.setTitle("Loading...");
        Loadingbar.setMessage("Please wait while we are opening requested website.");
        Loadingbar.show();


        SearchUrlButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed()
    {
        if(SearchWebAddress.canGoBack())
        {
            SearchWebAddress.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.super_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_back:
                onBackPressed();
                break;

            case R.id.menu_forward:
                onForwardPressed();
                break;

            case R.id.menu_refresh:
                SearchWebAddress.reload();
                break;

            case R.id.menu_home:
                break;

            case R.id.menu_history:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onForwardPressed(){
        if (SearchWebAddress.canGoForward()){
            SearchWebAddress.goForward();
        } else {
            Toast.makeText(this, "Can't go further", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == SearchUrlButton)
        {
            SearchWebAddress();
        }

    }

    private void SearchWebAddress()
    {
        Loadingbar.setTitle("Loading...");
        Loadingbar.setMessage("Please wait while we are opening requested website.");
        Loadingbar.show();

        String Url_Address = UrlInput.getText().toString();

        if(TextUtils.isEmpty(Url_Address))
        {
            Toast empty = Toast.makeText(UrlSearch.this, "Please enter URL or Web Address", Toast.LENGTH_LONG);
            empty.show();
        }
        else
        {
            String url_Without_https = Url_Address.replaceAll("https://www.", "");
            String https = "https://";
            String www = "www.";

            Intent search = new Intent(UrlSearch.this, UrlSearch.class);
            search.putExtra("url_adress", https+www+url_Without_https);
            startActivity(search);

            UrlInput.setText("");
            UrlInput.requestFocus();
        }
    }
}
