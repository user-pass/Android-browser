package com.example.nimbusbrowser;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rapidbrowser.R;

import java.util.ArrayList;

public class ViewFavorites extends AppCompatActivity implements DialogWindow.ExampleDialogListener, DialogWindowDel.ExampleDialogListener {


    DatabaseHelper db;
    ListView listView;
    ArrayList<Favorite> favoriteList;
    Favorite favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorites);

        db = new DatabaseHelper(this);
        viewData();


    }

    private void viewData() {
        favoriteList = new ArrayList<>();
        Cursor data = db.getListContents();
        int numRows = data.getCount();

        if (numRows == 0) {
            Toast.makeText(ViewFavorites.this, "No data to show!", Toast.LENGTH_SHORT).show();
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

    public void openDialog() {
        DialogWindow dialogWindow = new DialogWindow();
        dialogWindow.show(getSupportFragmentManager(), "Add favorite");
    }

    @Override
    public void applyTexts(String WebName, String WebAddress) {

        String uName = WebName;
        String uAddress = WebAddress;
        if (uName.length() != 0 && uAddress.length() != 0) {
            AddData(uName, uAddress);
        } else {
            Toast.makeText(ViewFavorites.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
        }
        viewData();
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

            case R.id.favorites_add:
                openDialog();
                break;


            case R.id.favorites_delete:
                openDelDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AddData(String urlName, String urlAddress) {
        boolean insertData = db.insertData(urlName, urlAddress);

        if (insertData == true) {
            Toast.makeText(ViewFavorites.this, "Successfully entered!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ViewFavorites.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
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