package com.example.study.fifteen_pass_android_architecture_components;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "human")
public class Human {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    //  指示 Room 需要忽略的字段或方法
    @Ignore
    public String ignoreText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIgnoreText() {
        return ignoreText;
    }

    public void setIgnoreText(String ignoreText) {
        this.ignoreText = ignoreText;
    }
}
