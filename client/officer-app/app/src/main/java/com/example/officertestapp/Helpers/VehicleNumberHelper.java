package com.example.officertestapp.Helpers;

public class VehicleNumberHelper {
    // 12#ශ්‍රී#7920#NONE => 12#SRI#7920#NA
    public static String preprocessVehicleNumber(String originalVehicleNumber) {
        // Replace "ශ්‍රී" with "SRI" if it exists
        if (originalVehicleNumber.contains("ශ්‍රී")) {
            originalVehicleNumber = originalVehicleNumber.replace("ශ්‍රී", "SRI");
        }
        // Replace "-" with "DH" if it exists
        if (originalVehicleNumber.contains("-")) {
            originalVehicleNumber = originalVehicleNumber.replace("-", "DH");
        }
        // Replace "NONE" with "NA" if it exists
        if (originalVehicleNumber.contains("NONE")) {
            originalVehicleNumber = originalVehicleNumber.replace("NONE", "NA");
        }
        return originalVehicleNumber;
    }


    // 12#SRI#7920#NA => 12 ශ්‍රී 7920
    public static String splitVehicleNumber(String vehicleNumber) {
        // Split the vehicle number by "#"
        String[] parts = vehicleNumber.split("#");

        // Iterate over each part and perform the replacements
        for (int i = 0; i < parts.length; i++) {
            // Replace "NA" with ""
            if (parts[i].contains("NA")) {
                parts[i] = parts[i].replace("NA", "");
            }
            // Replace "SRI" with "ශ්‍රී"
            if (parts[i].contains("SRI")) {
                parts[i] = parts[i].replace("SRI", "ශ්‍රී");
            }
            // Replace "DH" with "-"
            if (parts[i].contains("DH")) {
                parts[i] = parts[i].replace("DH", "-");
            }
        }

        // Join the parts back together with an empty space
        return String.join(" ", parts);
    }
}
