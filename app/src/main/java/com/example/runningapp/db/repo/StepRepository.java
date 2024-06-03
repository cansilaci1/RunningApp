package com.example.runningapp.db.repo;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.runningapp.db.entity.AppDatabase;
import com.example.runningapp.db.entity.StepData;
import com.example.runningapp.db.entity.StepDataDao;
import com.example.runningapp.db.entity.UserDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//viewModel classlarında kullanabilmek için MVVM katmanı

public class StepRepository {
    private StepDataDao stepDataDao;
    private LiveData<List<StepData>> allStepData;
    private ExecutorService executorService;

    private UserDao userDao;


    public StepRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        stepDataDao = db.stepDataDao();
        allStepData = stepDataDao.getAllStepDataLiveData();
        executorService = Executors.newSingleThreadExecutor();

        userDao = db.userDao(); // UserDao ekleyin
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<StepData>> getAllStepData() {
        return allStepData;
    }

    public void insert(StepData stepData) {
        executorService.execute(() -> stepDataDao.insert(stepData));
    }

    public void insertAll(StepData... stepData) {
        executorService.execute(() -> stepDataDao.insertAll(stepData));
    }

    public void deleteAll() {
        executorService.execute(() -> stepDataDao.deleteAll());
    }

    public void insertOrUpdateStepData(StepData stepData) {
        executorService.execute(() -> {
            StepData existingData = stepDataDao.getStepDataByDate(stepData.date);
            if (existingData != null) {
                existingData.steps += stepData.steps;
                existingData.distance = existingData.calculateDistance(existingData.steps);
                existingData.calories = existingData.calculateCalories(existingData.steps);
                stepDataDao.update(existingData);
            } else {
                stepDataDao.insert(stepData);
            }
        });
    }
}