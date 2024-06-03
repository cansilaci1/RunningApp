package com.example.runningapp.others;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.runningapp.R;
import com.example.runningapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    //ana aktivitemiz, tüm fragmentlara hostluk yapıyor
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        //menü geçişleri
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            boolean isBottomNavVisible = false;
            if (destination.getId() == R.id.statisticsFragment ||
                    destination.getId() == R.id.stepListFragment ||
                    destination.getId() == R.id.mapFragment ||
                    destination.getId() == R.id.settingsFragment) {
                isBottomNavVisible = true;
                showLottieAnimation();
            }
            bottomNavigationView.setVisibility(isBottomNavVisible ? View.VISIBLE : View.GONE);
            hideLottieAnimation();
        });
    }
        private void showLottieAnimation() {
            binding.fragmentContainerView.setVisibility(View.GONE);
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
        }

        private void hideLottieAnimation() {
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.fragmentContainerView.setVisibility(View.VISIBLE);
        }
    }

