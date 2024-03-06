package com.example.officertestapp.Attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.officertestapp.Helpers.DateTimeHelper;
import com.example.officertestapp.Login.LoginActivity;
import com.example.officertestapp.MainActivity;
import com.example.officertestapp.R;

public class MarkedAttendanceSuccessfulActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marked_attendance_successful);

        // Find the TextViews in the layout
        TextView recordedTimeView = findViewById(R.id.attendance_activity_success_time);
        TextView recordedDateView = findViewById(R.id.attendance_activity_success_date);

        // Retrieve the timestamp value from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("time_stamp")) {
            String timestamp = extras.getString("time_stamp");

            // Convert timestamp to formatted date and time strings
            String recordedTime = DateTimeHelper.convertTimeStampToTime(timestamp);
            String recordedDate = DateTimeHelper.convertTimeStampToDate(timestamp);

            // Set the formatted date and time strings to TextViews
            recordedTimeView.setText(recordedTime);
            recordedDateView.setText(recordedDate);
        }
    }

    public void attendance_activity_main_menu_btn_handler(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}