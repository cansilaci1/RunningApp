package com.example.runningapp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "step_data")
public class StepData {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String date;
    public int steps;
    public float distance;
    public float calories;  // Add this line

    public StepData(String date, int steps) {
        this.date = date;
        this.steps = steps;
        this.distance = calculateDistance(steps);
        this.calories = calculateCalories(steps);  // Add this line
    }

    public float calculateDistance(int steps) {
        float stepLength = 0.762f; // Average step length in meters
        return steps * stepLength;
    }

    public float calculateCalories(int steps) {
        float caloriesPerStep = 0.04f; // Example: average calories burned per step
        return steps * caloriesPerStep;
    }
}
