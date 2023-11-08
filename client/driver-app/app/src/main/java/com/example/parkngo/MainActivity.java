package com.example.parkngo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parkngo.home.AvailableParkingSpacesFragment;
import com.example.parkngo.home.HomeFragment;
import com.example.parkngo.home.NavigateFragment;
import com.example.parkngo.parking.ParkingFragment;
import com.example.parkngo.parking.ParkingSelectedFragment;
import com.example.parkngo.profile.ProfileFragment;
import com.example.parkngo.scan.ScanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URI;

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
//                Toast.makeText(getApplicationContext(), "Home Icon Clicked", Toast.LENGTH_LONG).show();
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

    private void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout_main_act, f);
        fragmentTransaction.commit();
    }

    public void parking_space_item_handler(View view) {
//        TextView name = view.findViewById(R.id.name_parking_item);
//        Toast.makeText(this, "parking item clicked" + name.getText().toString(), Toast.LENGTH_LONG).show();
        replaceFragment(new ParkingSelectedFragment());
    }

    public void back_btn_parking_selected_handler(View view){
        replaceFragment(new ParkingFragment());
    }

    // home fragment -> view all available parking spaces viewer
    public void home_fragment_view_available_spaces_btn_handler(View view) {
        replaceFragment(new AvailableParkingSpacesFragment());
    }

    // proceed to navigation
    public void available_parking_space_item_handler(View view){
//        replaceFragment(new NavigateFragment());
        Uri uri = Uri.parse("https://www.google.com/maps/dir/" + "university of colombo" + "/" + "Cmc - car park");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // back buttons
    public void navigate_frag_back_btn_handler(View view){
        replaceFragment(new AvailableParkingSpacesFragment());
    }

    public void ava_frag_back_btn_handler(View view){
        replaceFragment(new HomeFragment());
    }

}

