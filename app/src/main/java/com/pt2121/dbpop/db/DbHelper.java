package com.pt2121.dbpop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pt2121 on 7/27/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "program.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "program";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_EXTERNAL_ID = "external_id";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_API_REQUEST_FIELD = "api_request_field";
    public static final String COLUMN_API_REQUEST_VALUE = "api_request_key_value";

    public static final String[] allColumns = {COLUMN_ID,
            COLUMN_NAME, COLUMN_LATITUDE,
            COLUMN_LONGITUDE, COLUMN_EXTERNAL_ID,
            COLUMN_URL, COLUMN_API_REQUEST_FIELD, COLUMN_API_REQUEST_VALUE};

    public static final String DATABASE_CREATE = "create table " +
            TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_LATITUDE + " real not null, " +
            COLUMN_LONGITUDE + " real not null, " +
            COLUMN_EXTERNAL_ID + " text, " +
            COLUMN_URL + " text not null, " +
            COLUMN_API_REQUEST_FIELD + " text, " +
            COLUMN_API_REQUEST_VALUE + " text " +
            ");";
    // TODO comma

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
