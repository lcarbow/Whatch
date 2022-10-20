package com.example.whatch_moovium;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "test.db";

    private static final String TABLE_WATCHLIST_NAME = "watchlist";
    private static final String TABLE_SEENLIST_NAME = "seenlist";
    private static final String TABLE_PROVIDER_NAME = "provider";

    private static final String COLUMN_WATCHLIST_ID = "ID";
    private static final String COLUMN_SEENLIST_ID = "ID";
    private static final String COLUMN_PROVIDER = "activeproviders";

    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_WATCHLIST_NAME+" ( "+COLUMN_WATCHLIST_ID+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_SEENLIST_NAME+" ( "+COLUMN_SEENLIST_ID+" INTEGER PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PROVIDER_NAME+" ( "+COLUMN_PROVIDER+" TEXT)");
    }


    public void addWatchlistMovie(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_WATCHLIST_ID, ID);

        db.insert(TABLE_WATCHLIST_NAME, null, values);

        db.close();
    }
    public void addSeenlistMovie(int ID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_SEENLIST_ID, ID);

        db.insert(TABLE_SEENLIST_NAME, null, values);

        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_WATCHLIST_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_SEENLIST_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_PROVIDER_NAME);
        onCreate(sqLiteDatabase);

    }
}
