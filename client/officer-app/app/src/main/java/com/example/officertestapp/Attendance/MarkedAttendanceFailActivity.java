package com.example.officertestapp.Attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.officertestapp.Login.LoginActivity;
import com.example.officertestapp.R;

public class MarkedAttendanceFailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked_attendance_fail);
    }

    public void attendance_activity_go_back_btn_handler(View v) {
        Intent i = new Intent(this, MarkAttendanceActivity.class);
        startActivity(i);
    }
}