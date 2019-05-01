package com.example.nimbusbrowser;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapidbrowser.R;

public class UrlSearch extends AppCompatActivity implements View.OnClickListener, DialogWindowAdd.ExampleDialogListener {

    private Button SearchUrlButton;
    private EditText UrlInput;
    private WebView SearchWebAddress;
    String url;
    String myCurrentURL;
    DatabaseHelper db;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_search);
        db = new DatabaseHelper(this);

        SearchUrlButton = (Button) findViewById(R.id.search_url_button);
        UrlInput = (EditText) findViewById(R.id.input_search_url);
        SearchWebAddress = (WebView) findViewById(R.id.SearchWebsite);

        url = getIntent().getExtras().get("url_adress").toString();
        UrlInput.setText(url);
        WebSettings webSettings = SearchWebAddress.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);


        SearchWebAddress.loadUrl(url);
        myCurrentURL = url;
        SearchWebAddress.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                myCurrentURL = url;
                EditText editText = (EditText) findViewById(R.id.input_search_url);
                editText.setText(myCurrentURL, TextView.BufferType.EDITABLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                myCurrentURL = url;
                EditText editText = (EditText) findViewById(R.id.input_search_url);
                editText.setText(myCurrentURL, TextView.BufferType.EDITABLE);
            }
        });
        SearchUrlButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (SearchWebAddress.canGoBack()) {
            SearchWebAddress.goBack();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.super_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

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
                HomeButtonClick();
                break;

            case R.id.add_favorite: {
                openDialog();
            }
                break;

            case R.id.menu_favorites:
                OpenFavorites();
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
                break;

            case R.id.menu_share:
                ShareURL();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onForwardPressed() {
        if (SearchWebAddress.canGoForward()) {
            SearchWebAddress.goForward();
        } else {
            Toast.makeText(this, "Can't go further", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == SearchUrlButton) {
            SearchWebAddress();
        }

    }

    private void SearchWebAddress() {

        String Url_Address = UrlInput.getText().toString();

        if (TextUtils.isEmpty(Url_Address)) {
            Toast empty = Toast.makeText(UrlSearch.this, "Please enter URL or Web Address", Toast.LENGTH_LONG);
            empty.show();
        } else {
            String url_Without_https = Url_Address.replaceAll("https://", "");
            String https = "https://";


            Intent search = new Intent(UrlSearch.this, UrlSearch.class);
            search.putExtra("url_adress", https + url_Without_https);
            startActivity(search);

            UrlInput.setText("");
            UrlInput.requestFocus();
        }
    }


    private void HomeButtonClick()
    {
        finish();
        Intent homePage = new Intent(UrlSearch.this, HomeActivity.class);
        startActivity(homePage);
    }

    private void ShareURL()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,myCurrentURL);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Copied URL");
        startActivity(Intent.createChooser(shareIntent, "Share URL with your friends!"));
    }


    private void OpenFavorites()
    {
        Intent open_favor = new Intent(UrlSearch.this, ViewFavorites.class);
        startActivity(open_favor);
    }

    public void openDialog() {
       // menu.getItem(4).setIcon(getResources().getDrawable(R.drawable.ic_star_black_24dp));
        DialogWindowAdd dialogWindowAdd = new DialogWindowAdd();
        dialogWindowAdd.show(getSupportFragmentManager(), "Add new favorite");
    }

    @Override
    public void applyTextsAdd(String WebName) {

        String uName = WebName;
        String uAddress = myCurrentURL;
        if (uName.length() != 0 && uAddress.length() != 0) {
            AddData(uName, uAddress);
        } else {
            Toast.makeText(UrlSearch.this, "Something went wrong:(", Toast.LENGTH_LONG).show();
        }
    }

    public void AddData(String urlName, String urlAddress) {
        boolean insertData = db.insertData(urlName, urlAddress);

        if (insertData == true) {
            Toast.makeText(UrlSearch.this, "Successfully entered!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(UrlSearch.this, "Something went wrong :( \nMake sure you not duplicate favorite's name", Toast.LENGTH_LONG).show();
        }
    }

}