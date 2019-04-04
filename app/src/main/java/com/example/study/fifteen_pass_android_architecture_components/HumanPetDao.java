package com.example.study.fifteen_pass_android_architecture_components;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface HumanPetDao {
    @Query("SELECT * FROM human")
    public List<HumanAndPets> loadHumanAndPets();
}
