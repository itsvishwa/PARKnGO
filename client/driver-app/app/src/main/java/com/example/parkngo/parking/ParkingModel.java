package com.example.parkngo.parking;

import com.example.parkngo.R;

public class ParkingModel {

    int _id;
    String parkingName;
    String parkingType;
    String parkingAddress;
    int noOfStars;
    int noOfReviews;
    String parkingStatus;

    public ParkingModel(int _id, String parkingName, String parkingType, String parkingAddress, int noOfStars, int noOfReviews, String parkingStatus) {
        this._id = _id;
        this.parkingName = parkingName;
        this.parkingType = parkingType;
        this.parkingAddress = parkingAddress;
        this.noOfStars = noOfStars;
        this.noOfReviews = noOfReviews;
        this.parkingStatus = parkingStatus;
    }

    public String getParkingName() {
        return parkingName;
    }

    public String getParkingType() {
        return parkingType;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public int getNoOfStars() {
        return noOfStars;
    }

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public int get_id(){return _id;}
}
