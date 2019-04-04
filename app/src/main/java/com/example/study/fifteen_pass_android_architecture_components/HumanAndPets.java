package com.example.study.fifteen_pass_android_architecture_components;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class HumanAndPets {
    @Embedded
    private Human human;
    @Relation(parentColumn = "id", entityColumn = "human_id")
    public List<Pet> pets;

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
