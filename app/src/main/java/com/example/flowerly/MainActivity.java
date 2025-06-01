package com.example.flowerly;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        setupNavigation();

        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("IS_LOGGED_IN", false);

        if (!isLoggedIn) {
            loadFragment(new EnterFragment());
            bottomNav.setVisibility(View.GONE); // Скрываем панель на экране входа
        } else {
            navigateToHome();
            bottomNav.setVisibility(View.VISIBLE); // Показываем панель
        }
    }

    private void setupNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    selectedFragment = new HomeFragment();
//                    break;
//                case R.id.nav_catalog:
//                    // selectedFragment = new CatalogFragment();
//                    break;
//                case R.id.nav_cart:
//                    // selectedFragment = new CartFragment();
//                    break;
//                case R.id.nav_profile:
//                    // selectedFragment = new ProfileFragment();
//                    break;
//            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
    }

    public void navigateToHome() {
        bottomNav.setVisibility(View.VISIBLE);
        loadFragment(new HomeFragment());
        bottomNav.setSelectedItemId(R.id.nav_home); // Подсветим иконку "Главная"
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}