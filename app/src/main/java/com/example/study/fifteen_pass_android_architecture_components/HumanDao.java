package com.example.study.fifteen_pass_android_architecture_components;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HumanDao {

    @Query("SELECT * FROM HUMAN")
    public List<Human> loadHuman();

    @Insert
    public void addHuman(Human human);

    @Insert
    public void addHumans(List<Human> humans);

    @Update
    public void updateHuman(Human human);

    @Update
    public void updateHumans(List<Human> humans);

    @Delete
    public void deleteHuman(Human human);

    @Delete
    public void deleteeHumans(List<Human> humans);
}
