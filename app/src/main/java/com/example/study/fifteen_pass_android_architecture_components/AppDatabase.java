package com.example.study.fifteen_pass_android_architecture_components;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Human.class, HumanAndPets.class, Pet.class}
        , version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)//类型转换器
public abstract class AppDatabase extends RoomDatabase {

    public abstract HumanDao getHumanDao();

    private static final String DB_NAME = "test.db";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = createDb(context);
                }
            }
        }
        return instance;
    }

    private static AppDatabase createDb(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {//数据库创建回调
                        super.onCreate(db);
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {//数据库使用回调
                        super.onOpen(db);
                    }
                })
                .addMigrations(MIGRATION_1_2)//数据库升级迁移
                .allowMainThreadQueries()//允许数据库在主线程中操作
                .build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //TODO 执行对应的SQL语句
        }
    };
}
