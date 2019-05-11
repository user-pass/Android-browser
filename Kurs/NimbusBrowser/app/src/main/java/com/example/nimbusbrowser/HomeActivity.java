package com.example.nimbusbrowser;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rapidbrowser.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button SearchButtonHome;
    private EditText InputURL;
    private ImageButton google_btn;
    private ImageButton yandex_btn;
    private ImageButton duckgo_btn;
    private ImageButton bing_btn;
    private TextView say_hello_txt;
    private ImageButton facebook_btn;
    private ImageButton youtube_btn;
    private ImageButton twitter_btn;
    private ImageButton vkontakte_btn;
    private ImageButton instagram_btn;
    private ImageButton amazon_btn;
    private ImageButton wikipedia_btn;
    private ImageButton yahoo_btn;

    String API_KEY = "3172179ad37a45f2b4836d89175f9039";
    String NEWS_SOURCE = "the-washington-post";
    ListView listNews;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttons();

       listNews = (ListView) findViewById(R.id.listNews);

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
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

        if (view == facebook_btn) {
            Intent Open_facebook = new Intent(HomeActivity.this, UrlSearch.class);
            Open_facebook.putExtra("url_adress", "https://web.facebook.com");
            startActivity(Open_facebook);
        }

        if (view == youtube_btn) {
            Intent Open_youtube = new Intent(HomeActivity.this, UrlSearch.class);
            Open_youtube.putExtra("url_adress", "https://www.youtube.com");
            startActivity(Open_youtube);
        }

        if (view == twitter_btn) {
            Intent Open_twitter = new Intent(HomeActivity.this, UrlSearch.class);
            Open_twitter.putExtra("url_adress", "https://twitter.com");
            startActivity(Open_twitter);
        }

        if (view == vkontakte_btn) {
            Intent Open_vk = new Intent(HomeActivity.this, UrlSearch.class);
            Open_vk.putExtra("url_adress", "https://vk.com");
            startActivity(Open_vk);
        }

        if (view == instagram_btn) {
            Intent Open_insta = new Intent(HomeActivity.this, UrlSearch.class);
            Open_insta.putExtra("url_adress", "https://www.instagram.com");
            startActivity(Open_insta);
        }
        if (view == amazon_btn) {
            Intent Open_amazon = new Intent(HomeActivity.this, UrlSearch.class);
            Open_amazon.putExtra("url_adress", "https://www.amazon.com");
            startActivity(Open_amazon);
        }
        if (view == wikipedia_btn) {
            Intent Open_wiki = new Intent(HomeActivity.this, UrlSearch.class);
            Open_wiki.putExtra("url_adress", "https://ru.wikipedia.org");
            startActivity(Open_wiki);
        }

        if (view == yahoo_btn) {
            Intent Open_yahoo = new Intent(HomeActivity.this, UrlSearch.class);
            Open_yahoo.putExtra("url_adress", "https://www.yahoo.com/");
            startActivity(Open_yahoo);
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
                overridePendingTransition(R.anim.slidein, R.anim.slideout);
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
            String url_Without_https = Url_Address.replaceAll("https://", "");
            String https = "https://";

            Intent search = new Intent(HomeActivity.this, UrlSearch.class);
            search.putExtra("url_adress", https + url_Without_https);
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

    private void buttons()
    {
        say_hello_txt = (TextView)  findViewById(R.id.say_hello);
        SearchButtonHome = (Button) findViewById(R.id.search_button_home);
        InputURL = (EditText) findViewById(R.id.search_url_home);
        google_btn = (ImageButton) findViewById(R.id.Google);
        yandex_btn = (ImageButton) findViewById(R.id.Yandex);
        duckgo_btn = (ImageButton) findViewById(R.id.Duck);
        bing_btn = (ImageButton) findViewById(R.id.bing);
        facebook_btn = (ImageButton) findViewById(R.id.facebook);
        youtube_btn = (ImageButton) findViewById(R.id.youtube);
        twitter_btn = (ImageButton) findViewById(R.id.twitter);
        vkontakte_btn = (ImageButton) findViewById(R.id.vkontakte);
        instagram_btn = (ImageButton) findViewById(R.id.instagram);
        amazon_btn = (ImageButton) findViewById(R.id.amazon);
        wikipedia_btn = (ImageButton) findViewById(R.id.wikipedia);
        yahoo_btn = (ImageButton) findViewById(R.id.yahoo);


        SearchButtonHome.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        yandex_btn.setOnClickListener(this);
        duckgo_btn.setOnClickListener(this);
        bing_btn.setOnClickListener(this);
        facebook_btn.setOnClickListener(this);
        youtube_btn.setOnClickListener(this);
        twitter_btn.setOnClickListener(this);
        vkontakte_btn.setOnClickListener(this);
        instagram_btn.setOnClickListener(this);
        amazon_btn.setOnClickListener(this);
        wikipedia_btn.setOnClickListener(this);
        yahoo_btn.setOnClickListener(this);
    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = Function.excuteGet("https://newsapi.org/v1/articles?source="+NEWS_SOURCE+"&sortBy=top&apiKey="+API_KEY, urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_AUTHOR, "");
                        //map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListNewsAdapter adapter = new ListNewsAdapter(HomeActivity.this, dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(HomeActivity.this, UrlSearch.class);
                        i.putExtra("url_adress", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }



    }
}