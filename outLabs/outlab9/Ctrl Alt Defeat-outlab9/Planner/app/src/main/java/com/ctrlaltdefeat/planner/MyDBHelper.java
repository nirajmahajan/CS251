package com.ctrlaltdefeat.planner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ctrlaltdefeat.planner.TaskClass.*;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "seed.db";
    public static final int DATABASE_VERSION = 1;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NODES = "CREATE TABLE " +
                TaskEntry.TABLE_NAME + " (" +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_DESCR + " TEXT NOT NULL, " +
                TaskEntry.COLUMN_SCHEDULED + " TEXT, " +
                TaskEntry.COLUMN_PARENT + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_NODES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
