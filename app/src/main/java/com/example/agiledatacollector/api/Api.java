package com.example.agiledatacollector.api;

import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationResponse;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {
    @POST("get_travel_insurance_company")
    Call<GetTravelInsuranceCompanyRecommendationResponse> getTravelInsuranceCompanyRecommendation(
            @Body GetTravelInsuranceCompanyRecommendationRequest getTravelInsuranceCompanyRecommendationRequest
    );

    @POST("get_travel_insurance_plan")
    Call<GetTravelInsurancePlanRecommendationResponse> getTravelInsurancePlanRecommendation(
            @Body GetTravelInsurancePlanRecommendationRequest getTravelInsurancePlanRecommendationRequest
    );
}
