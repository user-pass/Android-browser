package com.example.nimbusbrowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "NewFavoritesAdd.db";
    private static final String DB_TABLE = "NewFavorites_table_add";
    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String URL = "URL";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "("+
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME+ " TEXT UNIQUE," +
            URL + " TEXT " + ")";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);

        onCreate(sqLiteDatabase);
    }


    public boolean insertData(String name, String url){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(URL, url);

        long result = db.insert(DB_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
        return data;
    }

    public void deleteName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DB_TABLE + " WHERE "
                + NAME + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
