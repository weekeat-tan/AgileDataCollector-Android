package com.example.agiledatacollector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.agiledatacollector.api.Api;
import com.example.agiledatacollector.api.RetrofitClient;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationResponse;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsurancePlanActivity extends AppCompatActivity {
    private Api api;

    // TODO: FINISH UP INSURANCE PLAN!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_plan);

        this.api = RetrofitClient.getRetrofitInstance().create(Api.class);

        String selectedRequest = getIntent().getExtras().getString("selectedRequest");
        String selectedCompany = getIntent().getExtras().getString("selectedCompany");

        // Typo on Python side
        if (selectedCompany.equals("Allianz")) {
            selectedCompany = "Allianze";
        }

        GetTravelInsuranceCompanyRecommendationRequest request = selectedRequest.equals("req1") ? MyApp.req1 : MyApp.req2;

        GetTravelInsurancePlanRecommendationRequest planRequest = new GetTravelInsurancePlanRecommendationRequest(
                request.getId(),
                request.getAvg_income_ttm(),
                request.getAvg_expense_ttm(),
                request.getOri_country(),
                request.getDest_country(),
                request.getTrip_type_oneway(),
                request.getTrip_type_roundtrip(),
                request.getTrip_cost(),
                request.getNum_stops(),
                request.getBooking_date(),
                request.getDept_date(),
                request.getReturn_date(),
                request.getNum_travellers(),
                request.getTraveller_one_dob(),
                request.getTraveller_two_dob(),
                request.getTraveller_three_dob(),
                request.getTraveller_four_dob(),
                selectedCompany
        );

        Call<GetTravelInsurancePlanRecommendationResponse> call = api.getTravelInsurancePlanRecommendation(planRequest);
        call.enqueue(new Callback<GetTravelInsurancePlanRecommendationResponse>() {
            @Override
            public void onResponse(Call<GetTravelInsurancePlanRecommendationResponse> call, Response<GetTravelInsurancePlanRecommendationResponse> response) {
                Log.i("Recommendation ID", planRequest.getId().toString());
                Log.i("Prob 0", response.body().getProb_0().toString());
                Log.i("Prob 1", response.body().getProb_1().toString());
                Log.i("Prob 2", response.body().getProb_2().toString());
            }

            @Override
            public void onFailure(Call<GetTravelInsurancePlanRecommendationResponse> call, Throwable t) {
                Log.i("           Recommendation ID", planRequest.getId().toString());
                Log.e("Failed to get recommendation", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}