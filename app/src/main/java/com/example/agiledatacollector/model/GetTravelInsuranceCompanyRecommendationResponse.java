package com.example.agiledatacollector.model;

import com.google.gson.annotations.SerializedName;

public class GetTravelInsuranceCompanyRecommendationResponse {
    @SerializedName("prob_aia")
    private Double prob_aia;
    @SerializedName("prob_allianze")
    private Double prob_allianze;
    @SerializedName("prob_axa")
    private Double prob_axa;
    @SerializedName("prob_aviva")
    private Double prob_aviva;
    @SerializedName("recommended_company")
    private String recommended_company;

    public Double getProb_aia() {
        return prob_aia;
    }

    public void setProb_aia(Double prob_aia) {
        this.prob_aia = prob_aia;
    }

    public Double getProb_allianze() {
        return prob_allianze;
    }

    public void setProb_allianze(Double prob_allianze) {
        this.prob_allianze = prob_allianze;
    }

    public Double getProb_axa() {
        return prob_axa;
    }

    public void setProb_axa(Double prob_axa) {
        this.prob_axa = prob_axa;
    }

    public Double getProb_aviva() {
        return prob_aviva;
    }

    public void setProb_aviva(Double prob_aviva) {
        this.prob_aviva = prob_aviva;
    }

    public String getRecommended_company() {
        return recommended_company;
    }

    public void setRecommended_company(String recommended_company) {
        this.recommended_company = recommended_company;
    }
}
