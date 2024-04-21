package com.example.officertestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.officertestapp.Home.AssignAVehicleAddDetailsFragment;
import com.example.officertestapp.Home.AssignAVehicleConfirmationFragment;
import com.example.officertestapp.Home.HomeMainFragment;
import com.example.officertestapp.Home.ReleaseASlotFragment;
import com.example.officertestapp.Home.ReleaseASlotConfirmationFragment;
import com.example.officertestapp.Home.PaymentDetailsFragment;
import com.example.officertestapp.Home.ReleaseASlot04Fragment;
import com.example.officertestapp.Home.ReleaseASlot06Fragment;
import com.example.officertestapp.Profile.ProfileLogoutFragment;
import com.example.officertestapp.Profile.ProfileMainFragment;
import com.example.officertestapp.Profile.ProfilePaymentHistoryFragment;
import com.example.officertestapp.ForceEnd.ForceEndMainFragment;
import com.example.officertestapp.Status.StatusMainFragment;
import com.example.officertestapp.Helpers.ParkngoStorage;
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
            }else if(item.getItemId() == R.id.bottom_nav_item_status) {
                replaceFragment(new StatusMainFragment());
            }else if(item.getItemId() == R.id.bottom_nav_item_force_end) {
                replaceFragment(new ForceEndMainFragment());
            }else if (item.getItemId() == R.id.bottom_nav_item_profile) {
                replaceFragment(new ProfileMainFragment());
            }
            return true;
        });
    }
    public void backBtnHandler(View view){
        onBackPressed();
    }

    public void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_act_frame_layout, f);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, Bundle data, View currView) {
        fragment.setArguments(data); // Set the data as arguments for the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_act_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        if (currView != null) {
            currView.setClickable(false);
            currView.setFocusable(false);
        }
    }

    public void frag_home_main_assign_vehicle_btn_handler(View v) {
        replaceFragment(new AssignAVehicleAddDetailsFragment());
    }

    public void frag_home_main_release_slot_btn_handler(View v) {
        replaceFragment(new ReleaseASlotFragment());
    }

    public void assign_vehicle_01_back_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void frag_home_assign_vehicle01_scan_QR_btn_handler(View v) {

    }

    public void frag_home_assign_vehicle01_reserve_slot_btn_handler(View v) {
        replaceFragment(new AssignAVehicleConfirmationFragment());
    }


    public void frag_home_assign_vehicle03_main_menu_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }


    public void assign_vehicle_02_back_btn_handler(View v) {
        replaceFragment(new AssignAVehicleAddDetailsFragment());
    }



    public void release_vehicle_01_back_btn_handler(View v) {
        replaceFragment(new HomeMainFragment());
    }

    public void release_vehicle_01_continue_btn_handler(View v) {
        replaceFragment(new ReleaseASlotConfirmationFragment());
    }

    public void release_vehicle_02_back_notify_btn_handler(View v) {
        replaceFragment(new ReleaseASlotFragment());
    }

    public void release_slot_02_back_btn_handler(View v) {
        replaceFragment(new ReleaseASlotFragment());
    }

    public void release_slot_02_yes_btn_handler(View v) {
        replaceFragment(new PaymentDetailsFragment());
    }

    public void release_slot_03_receive_cash_payment_btn_handler(View v) {
        replaceFragment(new ReleaseASlot04Fragment());
    }

    public void release_slot_04_payment_confirm_btn_handler(View v) {
        replaceFragment(new ReleaseASlot06Fragment());
    }

    public void release_vehicle_04_payment_cancel_btn_handler(View v) {
        replaceFragment(new PaymentDetailsFragment());
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





    public void frag_profile_logout_back_btn_handler(View v) {
        replaceFragment(new ProfileMainFragment());
    }

    public void frag_profile_logout_no_btn_handler(View v) {
        replaceFragment(new ProfileMainFragment());
    }

    public void frag_profile_logout_yes_btn_handler(View v) {
        ParkngoStorage parkngoStorage = new ParkngoStorage(getApplicationContext());
        parkngoStorage.clearData();
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }

    public void logout_immediately(){
        ParkngoStorage parkngoStorage = new ParkngoStorage(getApplicationContext());
        parkngoStorage.clearData();
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }

}
