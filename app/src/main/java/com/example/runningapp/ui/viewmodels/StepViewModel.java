package com.example.runningapp.ui.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.runningapp.db.entity.StepData;
import com.example.runningapp.db.repo.StepRepository;
import java.util.List;

//fragment ve repository arasındaki katman MVVM
public class StepViewModel extends AndroidViewModel {
    private StepRepository repository;
    private LiveData<List<StepData>> allStepData;

    public StepViewModel(@NonNull Application application) {
        super(application);
        repository = new StepRepository(application);
        allStepData = repository.getAllStepData();
    }

    public LiveData<List<StepData>> getAllStepData() {
        return allStepData;
    }

    public void insertStepData(StepData stepData) {
        repository.insert(stepData);
    }

    public void insertAllStepData(StepData... stepData) {
        repository.insertAll(stepData);
    }

    public void deleteAllStepData() {
        repository.deleteAll();
    }

    public void insertOrUpdateStepData(StepData stepData) {
        repository.insertOrUpdateStepData(stepData);
    }
}
