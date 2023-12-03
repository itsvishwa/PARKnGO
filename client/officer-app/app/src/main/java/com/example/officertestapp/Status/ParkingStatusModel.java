package com.example.officertestapp.Status;

public class ParkingStatusModel {
    String parkingID;
    String parkingStatus;
    String vehicleNumber;


    public ParkingStatusModel(String parkingID, String parkingStatus, String vehicleNumber) {
        this.parkingID = parkingID;
        this.parkingStatus = parkingStatus;
        this.vehicleNumber = vehicleNumber;
    }


    public String getParkingID() {
        return parkingID;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}
