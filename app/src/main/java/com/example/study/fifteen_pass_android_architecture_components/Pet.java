package com.example.study.fifteen_pass_android_architecture_components;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Human.class
        , parentColumns = "id", childColumns = "human_id"))
public class Pet {
    @PrimaryKey
    private int petId;

    private String name;

    @ColumnInfo(name = "human_id")
    private int humanId;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHumanId() {
        return humanId;
    }

    public void setHumanId(int humanId) {
        this.humanId = humanId;
    }
}
