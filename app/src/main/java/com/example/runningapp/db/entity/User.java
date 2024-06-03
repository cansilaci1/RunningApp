package com.example.runningapp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


//kullanıcı bilgilerinin tutulduğu data class
@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public int age;
    public float weight;

    public float height;
    public String gender;

    public User(String name, int age, float weight, float height, String gender) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }
}
