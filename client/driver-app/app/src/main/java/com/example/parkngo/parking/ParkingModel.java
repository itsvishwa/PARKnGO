package com.example.parkngo.parking;

import com.example.parkngo.R;

public class ParkingModel {
    String parkingName;
    String parkingType;
    String parkingRate;
    int noOfStars;
    int noOfReviews;
    int image;

    public ParkingModel(String parkingName, String parkingType, String parkingRate, int noOfStars, int noOfReviews, int image) {
        this.parkingName = parkingName;
        this.parkingType = parkingType;
        this.parkingRate = parkingRate;
        this.noOfStars = noOfStars;
        this.noOfReviews = noOfReviews;
        this.image = image;
    }

    public String getParkingName() {
        return parkingName;
    }

    public String getParkingType() {
        return parkingType;
    }

    public String getParkingRate() {
        return parkingRate;
    }

    public int getNoOfStars() {
        return noOfStars;
    }

    public int getNoOfReviews() {
        return noOfReviews;
    }

    public int getImage() {
        return image;
    }
}
