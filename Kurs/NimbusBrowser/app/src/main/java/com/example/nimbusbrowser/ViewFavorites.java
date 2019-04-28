package com.example.nimbusbrowser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rapidbrowser.R;

import java.util.ArrayList;

public class ViewFavorites extends AppCompatActivity implements DialogWindowDel.ExampleDialogListener {


    DatabaseHelper db;
    ListView listView;
    ArrayList<Favorite> favoriteList;
    Favorite favorite;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorites);

        db = new DatabaseHelper(this);

        viewData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.SiteURL);
                String text = textView.getText().toString();
                //  String share = favorite.getUrlAddress();

                Intent open_url_search = new Intent(ViewFavorites.this, UrlSearch.class);
                open_url_search.putExtra("url_adress",text);
                startActivity(open_url_search);

            }
        });

    }

    private void viewData() {
        favoriteList = new ArrayList<>();
        Cursor data = db.getListContents();
        int numRows = data.getCount();

        if (numRows == 0) {
            Toast.makeText(ViewFavorites.this, "No data to show!", Toast.LENGTH_SHORT).show();
            int i = 0;
            while (data.moveToNext()) {
                favorite = new Favorite(data.getString(1), data.getString(2));
                favoriteList.add(i, favorite);
                System.out.println(data.getString(1) + " " + data.getString(2));
                System.out.println(favoriteList.get(i).getUrlName());
                i++;
            }
            TwoColumn_ListAdapter adapter = new TwoColumn_ListAdapter(this, R.layout.adapter_view_layout, favoriteList);
            listView = (ListView) findViewById(R.id.list_favor);
            listView.setAdapter(adapter);
        } else {
            int i = 0;
            while (data.moveToNext()) {
                favorite = new Favorite(data.getString(1), data.getString(2));
                favoriteList.add(i, favorite);
                System.out.println(data.getString(1) + " " + data.getString(2));
                System.out.println(favoriteList.get(i).getUrlName());
                i++;
            }
            TwoColumn_ListAdapter adapter = new TwoColumn_ListAdapter(this, R.layout.adapter_view_layout, favoriteList);
            listView = (ListView) findViewById(R.id.list_favor);
            listView.setAdapter(adapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.favorites_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.favorites_back:
                finish();
                break;

            case R.id.favorites_delete:
                openDelDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void deleteTexts(String WebDelName) {
        String dName = WebDelName;
        if (dName.length() != 0) {
            DellData(dName);
        } else {
            Toast.makeText(ViewFavorites.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
        }
        viewData();
    }


    public void DellData(String dName) {
    db.deleteName(dName);
    Toast.makeText(ViewFavorites.this, "Successfully deleted!", Toast.LENGTH_LONG).show();
    }

    public void openDelDialog() {
        DialogWindowDel dialogWindowDel = new DialogWindowDel();
        dialogWindowDel.show(getSupportFragmentManager(), "Delete favorite");
    }

}