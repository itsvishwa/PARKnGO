package com.example.officertestapp.Helpers;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeHelper {
    private Handler handler;
    private TextView currentTimeView;
    private TextView currentDateView;

    public DateTimeHelper(TextView currentTimeView, TextView currentDateView) {
        this.currentTimeView = currentTimeView;
        this.currentDateView = currentDateView;
        handler = new Handler();
    }

    private Runnable dateTimeUpdater = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            handler.postDelayed(this, 1000); // Schedule the next update after 1 second
        }
    };

    private void updateDateTime() {
        // Update current time
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());
        currentTimeView.setText(currentTime);

        // Update current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        currentDateView.setText(currentDate);
    }

    public void startUpdatingDateTime() {
        // Start updating date and time
        handler.post(dateTimeUpdater);
    }

    public void stopUpdatingDateTime() {
        // Stop updating date and time
        handler.removeCallbacks(dateTimeUpdater);
    }


    // Method to convert date and time strings to timestamp
    public static long getTimestampFromDateTimeString(String dateString, String timeString) {
        String dateTimeString = dateString + " " + timeString;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd hh:mm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateTimeString);
            long timestampInSeconds = date.getTime() / 1000; // Get timestamp in seconds
            return timestampInSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Return -1 if parsing fails
        }
    }

    public static String convertTimeStampToTime(String timestamp) {
        long timestampLong = Long.parseLong(timestamp);

        // Create a Date object from the timestamp
        Date startDate = new Date(timestampLong * 1000); // Multiply by 1000 to convert seconds to milliseconds

        // Create a SimpleDateFormat object with your desired format
        SimpleDateFormat sdf = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);

        // Set the timezone to the device's local timezone
        sdf.setTimeZone(TimeZone.getDefault());

        // Format the date object to a string
        String formattedTime = sdf.format(startDate);

        return formattedTime;
    }
    public static String convertTimeStampToDate(String timestamp) {
        long timestampLong = Long.parseLong(timestamp);

        // Create a Date object from the timestamp
        Date startDate = new Date(timestampLong * 1000); // Multiply by 1000 to convert seconds to milliseconds

        // Create a SimpleDateFormat object with your desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd", Locale.ENGLISH);

        // Set the timezone to the device's local timezone
        sdf.setTimeZone(TimeZone.getDefault());

        // Format the date object to a string
        String formattedDate = sdf.format(startDate);

        return formattedDate;
    }

}