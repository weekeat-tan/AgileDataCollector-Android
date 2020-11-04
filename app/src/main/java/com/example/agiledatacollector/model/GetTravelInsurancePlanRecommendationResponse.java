package com.example.agiledatacollector.model;

import com.google.gson.annotations.SerializedName;

public class GetTravelInsurancePlanRecommendationResponse {
    @SerializedName("prob_0")
    private Double prob_0;
    @SerializedName("prob_1")
    private Double prob_1;
    @SerializedName("prob_2")
    private Double prob_2;

    public Double getProb_0() {
        return prob_0;
    }

    public void setProb_0(Double prob_0) {
        this.prob_0 = prob_0;
    }

    public Double getProb_1() {
        return prob_1;
    }

    public void setProb_1(Double prob_1) {
        this.prob_1 = prob_1;
    }

    public Double getProb_2() {
        return prob_2;
    }

    public void setProb_2(Double prob_2) {
        this.prob_2 = prob_2;
    }
}
