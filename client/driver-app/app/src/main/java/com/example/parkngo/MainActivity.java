package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.parkngo.home.AvailableParkingSpacesFragment;
import com.example.parkngo.home.HomeFragment;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                replaceFragment(new ScanFragment());
            } else if (itemId == R.id.navbar_parking) {
                replaceFragment(new ParkingFragment());
            }else {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });

//        set initial fragment
        replaceFragment(new HomeFragment());
    }


    // replace main frame layout by given fragment
    private void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main_act, f);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    // .................................................................................Button Handlers...........................................................

    // Back buttons handler
    public void backBtnHandler(View view){
        onBackPressed();
    }


    // parking spaces items
    public void parking_space_frag_parking_space_item_handler(View view) {
//        TextView name = view.findViewById(R.id.name_parking_item);
//        Toast.makeText(this, "parking item clicked" + name.getText().toString(), Toast.LENGTH_LONG).show();
        replaceFragment(new ParkingSelectedFragment());
    }


    // home fragment -> view all available parking spaces viewer
    public void home_frag_view_available_spaces_btn_handler(View view) {
        replaceFragment(new AvailableParkingSpacesFragment());
    }

    // profile fragment -> edit profile details
    public void profile_frag_edit_profile_btn_handler(View view){
        replaceFragment(new EditProfileFragment());
    }

    // proceed to navigation
    public void available_parking_space_item_handler(View view){
    //        replaceFragment(new NavigateFragment()); TODO:: Google MAP SDK
        double sourceLatitude = 6.902727395785716;
        double sourceLongitude = 79.86126018417747;
        double destinationLatitude = 6.915615411846493; // Replace with the actual destination latitude
        double destinationLongitude = 79.86440085336942; // Replace with the actual destination longitude

        String uri = "https://www.google.com/maps/dir/?api=1&origin=" + sourceLatitude + "," + sourceLongitude +
                "&destination=" + destinationLatitude + "," + destinationLongitude;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // payment history
    public void profile_frag_payment_btn_handler(View view){
        replaceFragment(new PaymentHistoryFragment());
    }

    // payment failed
    public void payment_frag_pay_btn_handler(View view){
        replaceFragment(new PaymentFailedFragment());
    }

    // edit mobile number
    public void edit_profile_frag_change_mobile_number_btn_handler(View view){
        replaceFragment(new EditMobileNumberFragment());
    }

    // add a review
    public void parking_selected_frag_add_review_btn_handler(View view){
        replaceFragment(new AddReviewFragment());
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
        Intent i = new Intent(this, HeroActivity.class);
        startActivity(i);
    }
}

