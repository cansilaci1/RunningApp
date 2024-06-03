package com.example.runningapp.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runningapp.R;
import com.example.runningapp.databinding.FragmentMyAcccountBinding;
import com.example.runningapp.db.entity.AppDatabase;
import com.example.runningapp.db.entity.StepData;
import com.example.runningapp.db.entity.User;
import com.example.runningapp.ui.custom.CustomBarChartView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MyAccountFragment extends Fragment {

    private FragmentMyAcccountBinding binding;
    private AppDatabase db;
    private static final String TAG = "MyAccountFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyAcccountBinding.inflate(inflater, container, false);
        db = AppDatabase.getDatabase(requireContext());

        loadUserInfo();
        loadTotalCalories();
        loadWeeklyStepsData(); // Load weekly steps data for the bar chart

        binding.btnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUser();
            }
        });

        return binding.getRoot();
    }

    private void loadUserInfo() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                User user = db.userDao().getUser();
                if (user != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tvName.setText("Kullanıcı Adı: " + user.name);
                            binding.tvAge.setText("Yaş: " + user.age);
                            binding.tvWeight.setText("Kilo: " + user.weight + "kg");
                            binding.tvHeight.setText("Boy: " + user.height + "cm");
                            Log.d(TAG, "User information loaded.");
                        }
                    });
                } else {
                    Log.d(TAG, "No user information found.");
                }
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
                        binding.tvTotalDistance.setText("Yakılan Kalori: " + totalCalories + " kcal");
                        Log.d(TAG, "Total calories loaded: " + totalCalories);
                    }
                });
            }
        });
    }

    private void loadWeeklyStepsData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<StepData> stepDataList = db.stepDataDao().getAllStepData(); // Assuming this method exists in DAO
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CustomBarChartView customBarChartView = binding.customBarChartView;
                        customBarChartView.setStepData(stepDataList);
                        Log.d(TAG, "Weekly step data loaded.");
                    }
                });
            }
        });
    }

    private void changeUser() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserRegistered", false);
        editor.apply();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().deleteAll();
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_settingsFragment_to_registrationFragment);
                    }
                });
            }
        });
    }
}
