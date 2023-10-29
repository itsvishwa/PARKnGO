package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parkngo.login.HeroActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navbar = findViewById(R.id.navbar);
        navbar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navbar_home){
                Toast.makeText(getApplicationContext(), "Home Icon Clicked", Toast.LENGTH_LONG).show();
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.navbar_scan) {
                Toast.makeText(getApplicationContext(), "Scan Icon Clicked", Toast.LENGTH_LONG).show();
                replaceFragment(new ScanFragment());
            } else if (itemId == R.id.navbar_parking) {
                Toast.makeText(getApplicationContext(), "Parking Icon Clicked", Toast.LENGTH_LONG).show();
                replaceFragment(new ParkingFragment());
            }else {
                Toast.makeText(getApplicationContext(), "Profile Icon Clicked", Toast.LENGTH_LONG).show();
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

//        set initial fragment
        replaceFragment(new HomeFragment());
    }

    private void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main_act, f);
        fragmentTransaction.commit();
    }


}

