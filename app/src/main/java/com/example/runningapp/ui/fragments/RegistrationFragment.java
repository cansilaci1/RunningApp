package com.example.runningapp.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.runningapp.R;
import com.example.runningapp.databinding.FragmentRegistrationBinding;
import com.example.runningapp.db.entity.AppDatabase;
import com.example.runningapp.db.entity.User;
import java.util.concurrent.Executors;
//kayıt ol sayfası
public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private AppDatabase db;
    private static final String TAG = "RegistrationFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        db = AppDatabase.getDatabase(requireContext());

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        boolean isUserRegistered = sharedPreferences.getBoolean("isUserRegistered", false);

        if (isUserRegistered) {
            showLottieAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registrationFragment_to_statisticsFragment);
                }
            }, 1000);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinnerGender = view.findViewById(R.id.spinnerGender);
        String[] genderOptions = getResources().getStringArray(R.array.gender_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
    }

    private void saveUserInfo() {
        String name = binding.etName.getText().toString();
        int age = Integer.parseInt(binding.etAge.getText().toString());
        float weight = Float.parseFloat(binding.etWeight.getText().toString());
        float height = Float.parseFloat(binding.etHeight.getText().toString());
        String gender = binding.spinnerGender.getSelectedItem().toString();

        User user = new User(name, age, weight, height, gender);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().insert(user);

                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isUserRegistered", true);
                editor.apply();

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLottieAnimation();
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registrationFragment_to_statisticsFragment);
                    }
                });
            }
        });
    }

    private void showLottieAnimation() {
        binding.formLayout.setVisibility(View.GONE);
        binding.lottieAnimationView.setVisibility(View.VISIBLE);
    }

    private void hideLottieAnimation() {
        binding.lottieAnimationView.setVisibility(View.GONE);
        binding.formLayout.setVisibility(View.VISIBLE);
    }
}
