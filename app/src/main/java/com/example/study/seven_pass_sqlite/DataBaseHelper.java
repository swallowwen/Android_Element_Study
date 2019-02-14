package com.example.study.seven_pass_sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseHelper {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DataBaseHelper instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private static SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DataBaseHelper();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DataBaseHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DataBaseHelper.class.getSimpleName() +
                    " is not initialized, call initialize(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }
}
