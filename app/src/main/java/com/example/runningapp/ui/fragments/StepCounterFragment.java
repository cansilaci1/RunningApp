package com.example.runningapp.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.runningapp.databinding.FragmentStepCounterBinding;
import com.example.runningapp.db.entity.AppDatabase;
import com.example.runningapp.db.entity.StepData;
import com.example.runningapp.ui.custom.CustomCircularProgressBar;
import com.example.runningapp.ui.viewmodels.StepViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class StepCounterFragment extends Fragment implements SensorEventListener {

    private FragmentStepCounterBinding binding;
    private SensorManager sensorManager;
    private boolean running = false;
    private float totalSteps = 0f;
    private float previousTotalSteps = 0f;
    private TextView tv_stepsTaken;
    private StepViewModel stepViewModel;
    private AppDatabase db;

    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStepCounterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        db = AppDatabase.getDatabase(requireContext());

        tv_stepsTaken = binding.tvStepsTaken;

        stepViewModel = new ViewModelProvider(this).get(StepViewModel.class);

        loadData();
        resetSteps();

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        checkPermission();

        loadTotalCalories();

        return view;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{android.Manifest.permission.ACTIVITY_RECOGNITION},
                        PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
            } else {
                startStepSensor();
            }
        } else {
            startStepSensor();
        }
    }

    private void startStepSensor() {
        running = true;
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            Toast.makeText(getActivity(), "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startStepSensor();
            } else {
                Toast.makeText(getActivity(), "Permission denied to access activity recognition", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (running) {
            startStepSensor();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (running) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            totalSteps = event.values[0];
            int currentSteps = (int) (totalSteps - previousTotalSteps);
            tv_stepsTaken.setText(String.valueOf(totalSteps));
            saveDailySteps(currentSteps);
        }
    }

    private void saveDailySteps(int steps) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        StepData stepData = new StepData(currentDate, steps);
        stepViewModel.insertOrUpdateStepData(stepData); // Custom method to update or insert step data
    }

    public void resetSteps() {
        tv_stepsTaken.setOnClickListener(v ->
                Toast.makeText(getActivity(), "Long tap to reset steps", Toast.LENGTH_SHORT).show());

        tv_stepsTaken.setOnLongClickListener(v -> {
            previousTotalSteps = totalSteps;
            saveData();
            return true;
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("key1", previousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        float savedNumber = sharedPreferences.getFloat("key1", 0f);
        Log.d("StepCounterFragment", String.valueOf(savedNumber));
        previousTotalSteps = savedNumber;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepGoal", 10000);
        editor.apply();

        SharedPreferences sharedPreferences1 = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        boolean isDataInserted = sharedPreferences.getBoolean("isDataInserted", false);

        if (!isDataInserted) {
            new Thread(() -> {
                AppDatabase db = AppDatabase.getDatabase(requireContext());
                db.stepDataDao().insertAll(
                        new StepData("2024-06-01", 10000),
                        new StepData("2024-06-02", 15000),
                        new StepData("2024-06-03", 20000),
                        new StepData("2024-06-04", 17000),
                        new StepData("2024-06-05", 22000),
                        new StepData("2024-06-06", 27000),
                        new StepData("2024-06-07", 8500));

                // Update SharedPreferences to indicate that data has been inserted
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor.putBoolean("isDataInserted", true);
                editor.apply();
            }).start();
        }

        stepViewModel.getAllStepData().observe(getViewLifecycleOwner(), new Observer<List<StepData>>() {
            @Override
            public void onChanged(List<StepData> stepDataList) {
            }
        });
    }


    private void loadTotalCalories() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                float totalCalories = db.stepDataDao().getTotalCalories(currentDate);

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CustomCircularProgressBar circularProgressBar = binding.circularProgressBar;
                        circularProgressBar.setMax(1000); // Örnek maksimum değer
                        circularProgressBar.setProgress(totalCalories); // Örnek şu anki değer
                    }
                });
            }
        });
    }

}
