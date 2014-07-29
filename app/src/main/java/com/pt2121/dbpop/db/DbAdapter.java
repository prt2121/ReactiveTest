package com.pt2121.dbpop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import com.pt2121.dbpop.model.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pt2121 on 7/27/14.
 */
public class DbAdapter {
    private Context mContext;
    private SQLiteDatabase mDb;
    private DbHelper mHelper;
    private static final String TAG = DbAdapter.class.getSimpleName();

    public DbAdapter(Context context) {
        this.mContext = context;
        mHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        mDb = mHelper.getWritableDatabase();
    }

    public void close() {
        mDb.close();
    }

    public long insert(Program program) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_NAME, program.name);
        values.put(DbHelper.COLUMN_LATITUDE, program.lat);
        values.put(DbHelper.COLUMN_LONGITUDE, program.lng);
        values.put(DbHelper.COLUMN_EXTERNAL_ID, program.externalId);
        values.put(DbHelper.COLUMN_URL, program.url);
        values.put(DbHelper.COLUMN_API_REQUEST_FIELD, program.apiKey.first);
        values.put(DbHelper.COLUMN_API_REQUEST_VALUE, program.apiKey.second);
        return mDb.insert(DbHelper.TABLE_NAME, null, values);
    }

    public int delete(Program program) {
        long id = program.id;
        return mDb.delete(DbHelper.TABLE_NAME, DbHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Program> getAllPrograms() {
        List<Program> programs = new ArrayList<Program>();
        Cursor cursor = mDb.query(DbHelper.TABLE_NAME, DbHelper.allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Program program = cursorToProgram(cursor);
            if (program != null)
                programs.add(program);
            cursor.moveToNext();
        }
        cursor.close();
        return programs;
    }

    private Program cursorToProgram(Cursor cursor) {
        try {
            return new Program(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_NAME)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LATITUDE)),
                    cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LONGITUDE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_EXTERNAL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_URL)),
                    new Pair<String, String>(
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_API_REQUEST_FIELD)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_API_REQUEST_VALUE)))
            );
        } catch (Exception ex) {
            return null;
        }
    }

}
