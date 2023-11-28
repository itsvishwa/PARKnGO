package com.example.officertestapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.officertestapp.Home.AssignVehicle01Fragment;
import com.example.officertestapp.Home.AssignVehicle02Fragment;
import com.example.officertestapp.Home.AssignVehicle03Fragment;
import com.example.officertestapp.Home.HomeMainFragment;
import com.example.officertestapp.Home.ReleaseASlot01Fragment;
import com.example.officertestapp.Home.ReleaseASlot02Fragment;
import com.example.officertestapp.Home.ReleaseASlot03Fragment;
import com.example.officertestapp.Home.ReleaseASlot04Fragment;
import com.example.officertestapp.Home.ReleaseASlot06Fragment;
import com.example.officertestapp.Profile.ProfileLogoutFragment;
import com.example.officertestapp.Profile.ProfileMainFragment;
import com.example.officertestapp.Profile.ProfilePaymentHistoryFragment;
import com.example.officertestapp.Status.StatusMainFragment;
import com.example.officertestapp.Status.StatusView01Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
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

    public void frag_home_main_assign_vehicle_btn_handler(View v) {
        replaceFragment(new AssignVehicle01Fragment());
    }

    public void frag_home_main_release_slot_btn_handler(View v) {
        replaceFragment(new ReleaseASlot01Fragment());
    }

    public void assign_vehicle_01_back_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void frag_home_assign_vehicle01_reserve_slot_btn_handler(View v) {
        replaceFragment(new AssignVehicle02Fragment());
    }

    public void release_vehicle_02_back_btn_handler(View v) {
        replaceFragment(new AssignVehicle01Fragment());
    }

    public void frag_home_assign_vehicle03_main_menu_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void assign_vehicle_02_yes_btn_handler(View v) {
        replaceFragment(new AssignVehicle03Fragment());
    }

    public void assign_vehicle_02_back_btn_handler(View v) {
        replaceFragment(new AssignVehicle01Fragment());
    }



    public void release_vehicle_01_back_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void release_vehicle_01_continue_btn_handler(View v) {
        replaceFragment(new ReleaseASlot02Fragment());
    }

    public void release_vehicle_02_back_notify_btn_handler(View v) {
        replaceFragment(new ReleaseASlot01Fragment());
    }

    public void release_slot_02_back_btn_handler(View v) {
        replaceFragment(new ReleaseASlot01Fragment());
    }

    public void release_slot_02_yes_btn_handler(View v) {
        replaceFragment(new ReleaseASlot03Fragment());
    }

    public void release_slot_03_receive_cash_payment_btn_handler(View v) {
        replaceFragment(new ReleaseASlot04Fragment());
    }

    public void release_slot_04_payment_confirm_btn_handler(View v) {
        replaceFragment(new ReleaseASlot06Fragment());
    }

    public void release_vehicle_04_payment_cancel_btn_handler(View v) {
        replaceFragment(new ReleaseASlot03Fragment());
    }

    public void frag_home_release_vehicle05_main_menu_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void frag_home_release_vehicle06_main_menu_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void frag_profile_main_payment_history_btn_handler(View v) {
        replaceFragment(new ProfilePaymentHistoryFragment());
    }

    public void frag_profile_payment_history_back_btn_handler(View v) {
        replaceFragment(new ProfileMainFragment());
    }



    public void frag_profile_main_logout_btn_handler(View v) {
        replaceFragment(new ProfileLogoutFragment());
    }

    public void parking_status_btn_handler(View v) {
        replaceFragment(new StatusView01Fragment());
    }

    public void status_01_back_btn_handler(View v) {
        replaceFragment(new StatusMainFragment());
    }



    public void frag_profile_logout_back_btn_handler(View v) {
        replaceFragment(new ProfileMainFragment());
    }

    public void frag_profile_logout_no_btn_handler(View v) {
        replaceFragment(new ProfileMainFragment());
    }

    /*public void frag_profile_logout_yes_btn_handler(View v) {
        replaceFragment(new );
    }*/

}
