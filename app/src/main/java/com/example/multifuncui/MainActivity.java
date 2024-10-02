package com.example.multifuncui;

import static com.example.multifuncui.mangment.PrefManager.FullScreen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.example.multifuncui.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public  User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreen(this,true);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        binding.homeFab.setOnClickListener(view ->{
            int currentDestinationId = Objects.requireNonNull(navController.getCurrentDestination()).getId();
            if (currentDestinationId != R.id.navigation_home) {
                navController.navigate(R.id.navigation_home);
            }
        });


        user = getIntent().getParcelableExtra(StartActivity.USER_DATA);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Use the username in MainActivity

    }

    public User getUser() {
        return user;
    }
}