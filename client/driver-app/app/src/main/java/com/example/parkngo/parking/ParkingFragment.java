package com.example.parkngo.parking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parkngo.R;

public class ParkingFragment extends Fragment {

    String[] parkingNames = {"CMC CAR PARK 01", "Lotus CAR PARK", "ABC CAR PARk 01", "CMC CAR PARK 02"};
    String[] parkingTypes = {"Public", "Customer Only", "Public", "Customer Only"};
    String[] parkingRates = {"Rs.40/ 1H", "Rs.40/ 1H", "Rs.40/ 1H", "Rs.40/ 1H"};
    int[] noOfStars = {3, 4, 5, 3};
    int[] noOfReviews = {86, 90, 15, 78};
    int[] images = {R.drawable.parking_space_sample, R.drawable.parking_space_sample, R.drawable.parking_space_sample, R.drawable.parking_space_sample};

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parking, container, false);
        listView = view.findViewById(R.id.parking_frag_listview);
        CustomAdapter adapter = new CustomAdapter(getContext(), parkingNames, images, parkingTypes, parkingRates, noOfReviews, noOfStars);
        listView.setAdapter(adapter);
        return view;
    }
}

class CustomAdapter extends ArrayAdapter<String>{
    Context context;
    String[] parkingNames;
    int[] parkingImages;
    String[] parkingTypes;
    String[] parkingRates;
    int[] parkingReviewCounts;
    int[] parkingStarCounts;

    CustomAdapter(Context context, String[] parkingNames, int[] parkingImages, String[] parkingTypes, String[] parkingRates, int[] reviewCounts,  int[] starCounts){
        super(context, R.layout.parking_item, R.id.name_parking_item, parkingNames);
        this.context = context;
        this.parkingImages = parkingImages;
        this.parkingNames = parkingNames;
        this.parkingTypes = parkingTypes;
        this.parkingRates = parkingRates;
        this.parkingReviewCounts = reviewCounts;
        this.parkingStarCounts = starCounts;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parkingItem = inflater.inflate(R.layout.parking_item, parent, false);
        ImageView parkingImageView = parkingItem.findViewById(R.id.image_parking_item);
        TextView parkingNameView = parkingItem.findViewById(R.id.name_parking_item);
        TextView parkingTypeView = parkingItem.findViewById(R.id.type_parking_item);
        TextView parkingRateView = parkingItem.findViewById(R.id.rate_parking_item);
        TextView parkingReviewCount = parkingItem.findViewById(R.id.review_count_parking_item);

        parkingImageView.setImageResource(parkingImages[position]);
        parkingNameView.setText(parkingNames[position]);
        parkingRateView.setText(parkingRates[position]);
        parkingTypeView.setText(parkingTypes[position]);
        parkingReviewCount.setText("(" + parkingReviewCounts[position] + ")");

        int num = parkingStarCounts[position];
        int mainColor = context.getResources().getColor(R.color.primaryColor);
        if(num >= 1){
            ImageView starView = parkingItem.findViewById(R.id.star1_parking_item);
            ImageViewCompat.setImageTintList(starView, ColorStateList.valueOf(mainColor));
        }
        if(num >= 2){
            ImageView starView = parkingItem.findViewById(R.id.star2_parking_item);
            ImageViewCompat.setImageTintList(starView, ColorStateList.valueOf(mainColor));
        }
        if (num >= 3) {
            ImageView starView = parkingItem.findViewById(R.id.star3_parking_item);
            ImageViewCompat.setImageTintList(starView, ColorStateList.valueOf(mainColor));
        }
        if (num >= 4) {
            ImageView starView = parkingItem.findViewById(R.id.star4_parking_item);
            ImageViewCompat.setImageTintList(starView, ColorStateList.valueOf(mainColor));
        }
        if (num >= 5){
            ImageView starView = parkingItem.findViewById(R.id.star5_parking_item);
            ImageViewCompat.setImageTintList(starView, ColorStateList.valueOf(mainColor));
        }


        return parkingItem;
    }
}