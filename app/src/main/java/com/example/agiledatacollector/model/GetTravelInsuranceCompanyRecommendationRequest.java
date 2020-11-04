package com.example.agiledatacollector.model;

public class GetTravelInsuranceCompanyRecommendationRequest {
    private Integer id;
    private Double avg_income_ttm;
    private Double avg_expense_ttm;
    private String ori_country;
    private String dest_country;
    private Boolean trip_type_oneway;
    private Boolean trip_type_roundtrip;
    private Double trip_cost;
    private Integer num_stops;
    private String booking_date;
    private String dept_date;
    private String return_date;
    private Integer num_travellers;
    private String traveller_one_dob;
    private String traveller_two_dob;
    private String traveller_three_dob;
    private String traveller_four_dob;

    public GetTravelInsuranceCompanyRecommendationRequest(Integer id, Double avg_income_ttm, Double avg_expense_ttm, String ori_country, String dest_country, Boolean trip_type_oneway, Boolean trip_type_roundtrip, Double trip_cost, Integer num_stops, String booking_date, String dept_date, String return_date, Integer num_travellers, String traveller_one_dob, String traveller_two_dob, String traveller_three_dob, String traveller_four_dob) {
        this.id = id;
        this.avg_income_ttm = avg_income_ttm;
        this.avg_expense_ttm = avg_expense_ttm;
        this.ori_country = ori_country;
        this.dest_country = dest_country;
        this.trip_type_oneway = trip_type_oneway;
        this.trip_type_roundtrip = trip_type_roundtrip;
        this.trip_cost = trip_cost;
        this.num_stops = num_stops;
        this.booking_date = booking_date;
        this.dept_date = dept_date;
        this.return_date = return_date;
        this.num_travellers = num_travellers;
        this.traveller_one_dob = traveller_one_dob;
        this.traveller_two_dob = traveller_two_dob;
        this.traveller_three_dob = traveller_three_dob;
        this.traveller_four_dob = traveller_four_dob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAvg_income_ttm() {
        return avg_income_ttm;
    }

    public void setAvg_income_ttm(Double avg_income_ttm) {
        this.avg_income_ttm = avg_income_ttm;
    }

    public Double getAvg_expense_ttm() {
        return avg_expense_ttm;
    }

    public void setAvg_expense_ttm(Double avg_expense_ttm) {
        this.avg_expense_ttm = avg_expense_ttm;
    }

    public String getOri_country() {
        return ori_country;
    }

    public void setOri_country(String ori_country) {
        this.ori_country = ori_country;
    }

    public String getDest_country() {
        return dest_country;
    }

    public void setDest_country(String dest_country) {
        this.dest_country = dest_country;
    }

    public Boolean getTrip_type_oneway() {
        return trip_type_oneway;
    }

    public void setTrip_type_oneway(Boolean trip_type_oneway) {
        this.trip_type_oneway = trip_type_oneway;
    }

    public Boolean getTrip_type_roundtrip() {
        return trip_type_roundtrip;
    }

    public void setTrip_type_roundtrip(Boolean trip_type_roundtrip) {
        this.trip_type_roundtrip = trip_type_roundtrip;
    }

    public Double getTrip_cost() {
        return trip_cost;
    }

    public void setTrip_cost(Double trip_cost) {
        this.trip_cost = trip_cost;
    }

    public Integer getNum_stops() {
        return num_stops;
    }

    public void setNum_stops(Integer num_stops) {
        this.num_stops = num_stops;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getDept_date() {
        return dept_date;
    }

    public void setDept_date(String dept_date) {
        this.dept_date = dept_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public Integer getNum_travellers() {
        return num_travellers;
    }

    public void setNum_travellers(Integer num_travellers) {
        this.num_travellers = num_travellers;
    }

    public String getTraveller_one_dob() {
        return traveller_one_dob;
    }

    public void setTraveller_one_dob(String traveller_one_dob) {
        this.traveller_one_dob = traveller_one_dob;
    }

    public String getTraveller_two_dob() {
        return traveller_two_dob;
    }

    public void setTraveller_two_dob(String traveller_two_dob) {
        this.traveller_two_dob = traveller_two_dob;
    }

    public String getTraveller_three_dob() {
        return traveller_three_dob;
    }

    public void setTraveller_three_dob(String traveller_three_dob) {
        this.traveller_three_dob = traveller_three_dob;
    }

    public String getTraveller_four_dob() {
        return traveller_four_dob;
    }

    public void setTraveller_four_dob(String traveller_four_dob) {
        this.traveller_four_dob = traveller_four_dob;
    }
}
