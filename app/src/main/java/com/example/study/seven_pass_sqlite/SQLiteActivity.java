package com.example.study.seven_pass_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SQLiteActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db = DataBaseHelper.getInstance().openDatabase();
        //通过execSQL()方法插入数据
        db.beginTransaction();
        db.execSQL("insert into " + DBHelper.TABLE_NAME + " (Id, UserName, Age, Country) values (1, 'Arc', 30, 'China')");
        db.setTransactionSuccessful();
        DataBaseHelper.getInstance().closeDatabase();
        //通过insert()方法插入数据
        db = DataBaseHelper.getInstance().openDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("Id", 2);
        values.put("UserName", "Alice");
        values.put("Age", 25);
        values.put("Country", "America");
        db.insert(DBHelper.TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        DataBaseHelper.getInstance().closeDatabase();

        //通过delete()方法删除Id=2的数据
        db = DataBaseHelper.getInstance().openDatabase();
        db.beginTransaction();
        db.delete(DBHelper.TABLE_NAME, "Id = ?", new String[]{String.valueOf(1)});
        db.setTransactionSuccessful();
        DataBaseHelper.getInstance().closeDatabase();

        //通过update()方法修改Id=1的数据Age=22
        db = DataBaseHelper.getInstance().openDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put("Age", 22);
        db.update(DBHelper.TABLE_NAME,
                cv,
                "Id = ?",
                new String[]{String.valueOf(1)});
        db.setTransactionSuccessful();
        DataBaseHelper.getInstance().closeDatabase();

        //查询表中所有数据
        db = DataBaseHelper.getInstance().openDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null,
                null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("Id"));
                String userName = cursor.getString(cursor.getColumnIndex("UserName"));
                int age = cursor.getInt(cursor.getColumnIndex("Age"));
                String country = cursor.getString(cursor.getColumnIndex("Country"));
            }
        }
        cursor.close();
        DataBaseHelper.getInstance().closeDatabase();
    }
}
