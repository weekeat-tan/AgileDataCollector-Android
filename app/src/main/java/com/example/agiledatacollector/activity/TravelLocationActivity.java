package com.example.agiledatacollector.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agiledatacollector.MyApp;
import com.example.agiledatacollector.R;

public class TravelLocationActivity extends AppCompatActivity {

    private TextView textViewTravelDestination1;
    private TextView textViewTravelDestinationDescription1;
    private ImageView imageViewTravelDestination1;

    private TextView textViewTravelDestination2;
    private TextView textViewTravelDestinationDescription2;
    private ImageView imageViewTravelDestination2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_location);

        this.textViewTravelDestination1 = findViewById(R.id.textViewTravelDestination1);
        this.textViewTravelDestinationDescription1 = findViewById(R.id.textViewTravelDestinationDescription1);
        this.imageViewTravelDestination1 = findViewById(R.id.imageViewTravelDestination1);

        this.textViewTravelDestination2 = findViewById(R.id.textViewTravelDestination2);
        this.textViewTravelDestinationDescription2 = findViewById(R.id.textViewTravelDestinationDescription2);
        this.imageViewTravelDestination2 = findViewById(R.id.imageViewTravelDestination2);

        initializeView();
    }

    public void viewTravelInsuranceCompanyRecommendationForSelectedCountry(View view) {
//        String selectedRequest = view.getTag().toString();
//        Intent intent = new Intent(this, InsuranceCompanyActivity.class);
//        intent.putExtra("selectedRequest", selectedRequest);
//
//        startActivity(intent);
    }

    private void initializeView() {
        if (MyApp.req1 != null) {
            String destination1 = MyApp.req1.getDest_country();
            String departureDate1 = MyApp.req1.getDept_date();
            String returnDate1 = MyApp.req1.getReturn_date();
            this.textViewTravelDestination1.setText(destination1);
            this.textViewTravelDestinationDescription1.setText("Based on your recent expenses on flight ticket to " + destination1 + " from " + departureDate1 + " to " + returnDate1 + ".");
            switch (destination1) {
                case "China":
                    this.imageViewTravelDestination1.setImageResource(R.drawable.shanghai);
                    break;
                case "Malaysia":
                    this.imageViewTravelDestination1.setImageResource(R.drawable.kuala_lumpur);
                    break;
                case "United States":
                    this.imageViewTravelDestination1.setImageResource(R.drawable.new_york);
                    break;
                default:
                    this.imageViewTravelDestination1.setImageResource(R.drawable.taipei);
                    break;
            }
        }

        if (MyApp.req2 != null) {
            String destination2 = MyApp.req2.getDest_country();
            String departureDate2 = MyApp.req2.getDept_date();
            String returnDate2 = MyApp.req2.getReturn_date();
            this.textViewTravelDestination2.setText(destination2);
            this.textViewTravelDestinationDescription2.setText("Based on your recent expenses on flight ticket to " + destination2 + " from " + departureDate2 + " to " + returnDate2 + ".");
            switch (destination2) {
                case "China":
                    this.imageViewTravelDestination2.setImageResource(R.drawable.shanghai);
                    break;
                case "Malaysia":
                    this.imageViewTravelDestination2.setImageResource(R.drawable.kuala_lumpur);
                    break;
                case "United States":
                    this.imageViewTravelDestination2.setImageResource(R.drawable.new_york);
                    break;
                default:
                    this.imageViewTravelDestination2.setImageResource(R.drawable.taipei);
                    break;
            }
        }
    }
}