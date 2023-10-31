package com.example.officertestapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeMainFragment());

        // Listen for the bottom navigation bar button clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_act_bottom_nav_menu);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.bottom_nav_item_home){
                replaceFragment(new HomeMainFragment());
            }else if(item.getItemId() == R.id.bottom_nav_item_status){
                replaceFragment(new StatusMainFragment());
            } else if (item.getItemId() == R.id.bottom_nav_item_profile) {
                replaceFragment(new ProfileMainFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_act_frame_layout, f);
        fragmentTransaction.commit();
    }
}
