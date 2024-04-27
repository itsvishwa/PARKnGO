package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.parkngo.helpers.ParkngoStorage;
import com.example.parkngo.home.AvailableParkingSpacesFragment;
import com.example.parkngo.home.HomeFragment;
import com.example.parkngo.home.ViewMapFragment;
import com.example.parkngo.logout.LogoutFragment;
import com.example.parkngo.parking.AddReviewFragment;
import com.example.parkngo.parking.ParkingFragment;
import com.example.parkngo.parking.ParkingSelectedFragment;
import com.example.parkngo.profile.EditMobileNumberFragment;
import com.example.parkngo.profile.EditProfileFragment;
import com.example.parkngo.profile.PaymentHistoryFragment;
import com.example.parkngo.profile.ProfileFragment;
import com.example.parkngo.scan.PaymentFailedFragment;
import com.example.parkngo.scan.PaymentFragment;
import com.example.parkngo.scan.ScanFragment;
import com.example.parkngo.session.SessionMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom navigation bar
        navbar = findViewById(R.id.navbar);
        navbar.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navbar_home){
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.navbar_scan) {
                replaceFragment(new SessionMainFragment());
            } else if (itemId == R.id.navbar_parking) {
                replaceFragment(new ParkingFragment());
            }else {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

//      set initial fragment
        replaceFragment(new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();

        for (Fragment ft : fragments) {
            Log.d("FragmentVisibility", ft.getClass().getSimpleName());
        }
    }


    // replace main frame layout by given fragment
    public void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main_act, f);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, Bundle data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main_act, fragment);
        fragmentTransaction.addToBackStack(null);
        fragment.setArguments(data);
        fragmentTransaction.commit();
    }


    // .................................................................................Button Handlers...........................................................
    // Back buttons handler
    public void backBtnHandler(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    // profile fragment -> edit profile details
    public void profile_frag_edit_profile_btn_handler(View view){
        replaceFragment(new EditProfileFragment());
    }


    // payment history
    public void profile_frag_payment_btn_handler(View view){
        replaceFragment(new PaymentHistoryFragment());
    }


    // scan qr
    public void scan_frag_qr_btn_handler(View view){
        replaceFragment(new PaymentFragment());
    }

    // logout btn handlers
    public void profile_frag_logout_btn_handler(View view){
        replaceFragment(new LogoutFragment());
    }

    // logout confirm btn handler
    public void logout_frag_yes_btn_handler(View view){
        ParkngoStorage parkngoStorage = new ParkngoStorage(getApplicationContext());
        parkngoStorage.clearData();
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }
}

