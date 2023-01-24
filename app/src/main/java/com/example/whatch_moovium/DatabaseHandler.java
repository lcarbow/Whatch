package com.example.whatch_moovium;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "whatch.db";

    private static final String TABLE_WATCHLIST_NAME = "watchlist";
    private static final String TABLE_SEENLIST_NAME = "seenlist";
    private static final String TABLE_IMAGEBUTTON_1 = "imagebutton1";
    private static final String TABLE_IMAGEBUTTON_2 = "imagebutton2";
    private static final String TABLE_IMAGEBUTTON_3 = "imagebutton3";
    private static final String TABLE_IMAGEBUTTON_4 = "imagebutton4";
    private static final String TABLE_IMAGEBUTTON_5 = "imagebutton5";
    private static final String TABLE_IMAGEBUTTON_6 = "imagebutton6";

    private static final String COLUMN_WATCHLIST_ID = "ID";
    private static final String COLUMN_SEENLIST_ID = "ID";
    private static final String COLUMN_GENERAL = "INTI";

    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_WATCHLIST_NAME+" ( "+COLUMN_WATCHLIST_ID+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_SEENLIST_NAME+" ( "+COLUMN_SEENLIST_ID+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_IMAGEBUTTON_1+" ( "+COLUMN_GENERAL+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_IMAGEBUTTON_2+" ( "+COLUMN_GENERAL+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_IMAGEBUTTON_3+" ( "+COLUMN_GENERAL+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_IMAGEBUTTON_4+" ( "+COLUMN_GENERAL+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_IMAGEBUTTON_5+" ( "+COLUMN_GENERAL+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_IMAGEBUTTON_6+" ( "+COLUMN_GENERAL+" INTEGER PRIMARY KEY)");
    }


    public void addWatchlistMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_WATCHLIST_ID, id);

        db.insert(TABLE_WATCHLIST_NAME, null, values);

        db.close();
    }
    public void addSeenlistMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_SEENLIST_ID, id);

        db.insert(TABLE_SEENLIST_NAME, null, values);

        db.close();
    }
    public boolean CheckIfExist(String TableName, int fieldValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TableName + " where ID = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void delWatchlistMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WATCHLIST_NAME, "ID ="+id, null);
        db.close();
    }

    public void delSeenlistMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SEENLIST_NAME, "ID ="+id, null);
        db.close();
    }


    public List<Integer> getWatchlist(){
        List<Integer> watchList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from watchlist";
        Cursor cursor = db.rawQuery(Query, null);
        int fieldToAdd;
        while (cursor.moveToNext()){
            fieldToAdd = cursor.getInt(0);
            watchList.add(fieldToAdd);
        }
        cursor.close();
        return watchList;
    }

    public void addTableIMG(String tablename, int i){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_GENERAL, i);

        db.insert(tablename, null, values);

        db.close();
    }

    public List<Integer> getMoodlist(String tablename){
        List<Integer> moodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " + tablename;
        Cursor cursor = db.rawQuery(Query, null);
        int fieldToAdd;
        while (cursor.moveToNext()){
            fieldToAdd = cursor.getInt(0);
            moodList.add(fieldToAdd);
        }
        cursor.close();
        return moodList;
    }

    public int moodSize(String tableName){
        String countQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int i = cursor.getCount();
        cursor.close();
        return i;
    }

    public boolean checkIfThree(String tableName){
        String countQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.getCount() < 3){cursor.close(); return false;}
        else{cursor.close(); return true;}
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_WATCHLIST_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_SEENLIST_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PROVIDER_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGEBUTTON_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGEBUTTON_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGEBUTTON_3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGEBUTTON_4);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGEBUTTON_5);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IMAGEBUTTON_6);
        onCreate(sqLiteDatabase);

    }
}
