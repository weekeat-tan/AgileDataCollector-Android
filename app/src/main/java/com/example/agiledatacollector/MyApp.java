package com.example.agiledatacollector;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationResponse;

import java.util.List;

public class MyApp extends Application {
    public static SharedPreferences sharedPreferences;

    public static GetTravelInsuranceCompanyRecommendationRequest req1;
    public static GetTravelInsuranceCompanyRecommendationRequest req2;

    public static List<GetTravelInsuranceCompanyRecommendationResponse> recommendationResponses;

    public MyApp() {
        // This method fires only once per application start.
        // getApplicationContext() returns null here
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // This method fires once per application start
        // Application has context here
    }
}

