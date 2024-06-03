package com.example.runningapp.db.entity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StepDataDao {
    @Insert
    void insert(StepData stepData);

    @Insert
    void insertAll(StepData... stepData);

    @Query("SELECT * FROM step_data ORDER BY date DESC")
    LiveData<List<StepData>> getAllStepDataLiveData();

    @Query("SELECT * FROM step_data ORDER BY date DESC")
    List<StepData> getAllStepData();

    @Query("DELETE FROM step_data")
    void deleteAll();

    @Query("SELECT SUM(calories) FROM step_data WHERE date = :currentDate")
    float getTotalCalories(String currentDate);  // Add this line

    @Query("SELECT * FROM step_data WHERE date = :date LIMIT 1")
    StepData getStepDataByDate(String date);

    @Update
    void update(StepData stepData);
}
