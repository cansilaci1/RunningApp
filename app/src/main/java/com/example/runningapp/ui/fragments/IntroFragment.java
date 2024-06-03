package com.example.runningapp.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.runningapp.R;
import com.example.runningapp.databinding.ActivityMainBinding;
import com.example.runningapp.databinding.FragmentIntroBinding;

//intro sayfası
public class IntroFragment extends Fragment {

    private FragmentIntroBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIntroBinding.inflate(inflater, container, false);

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_introFragment_to_registrationFragment);
                showLottieAnimation();
            }
        });
        hideLottieAnimation();


        return binding.getRoot();
    }

    private void showLottieAnimation() {
        binding.constraint.setVisibility(View.GONE);
        binding.lottieAnimationView.setVisibility(View.VISIBLE);
    }

    private void hideLottieAnimation() {
        binding.lottieAnimationView.setVisibility(View.GONE);
        binding.constraint.setVisibility(View.VISIBLE);
    }

}