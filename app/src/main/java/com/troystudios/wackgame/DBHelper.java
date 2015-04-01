package com.troystudios.wackgame;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by Wise on 11.02.2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "MainDB";
    private static final int DB_VERSION = 1;
    public static final String TABLE = "users";
    public static final String C_NICKNAME = "login";
    public static final String C_EMAIL = "email";
    public static final String C_PASSWORD = "password";
    public static final String C_RESULTS = "result";
    private Context  context;

    public DBHelper (Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE + "(" + "id integer primary key, " +C_NICKNAME+ " text, " +C_EMAIL +" text, "+C_PASSWORD+" text, " + C_RESULTS +" text)";
        db.execSQL(query);
        Log.d(TAG,"on Created sql: "+ query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty...
    }
}