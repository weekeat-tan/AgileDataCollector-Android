package com.example.agiledatacollector.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agiledatacollector.MyApp;
import com.example.agiledatacollector.R;
import com.example.agiledatacollector.api.Api;
import com.example.agiledatacollector.api.RetrofitClient;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationResponse;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationResponse;
import com.example.agiledatacollector.adapter.ExpandableListAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsurancePlanActivity extends AppCompatActivity {
    private Api api;

    private TextView textViewSelectedCompany;
    private TextView textViewSelectedCompanySlogan;

    private TabLayout tabLayout;
    private CardView cardViewCompanyDescription;
    private TextView textViewCompanyDescription;
    private CardView linearLayoutInsurancePlans;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChildAttributes;
    private HashMap<String, List<String>> listDataChildValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_plan);

        this.api = RetrofitClient.getRetrofitInstance().create(Api.class);

        this.textViewSelectedCompany = findViewById(R.id.textViewSelectedCompany);
        this.textViewSelectedCompanySlogan = findViewById(R.id.textViewSelectedCompanySlogan);

        this.tabLayout = findViewById(R.id.tabLayout);
        this.cardViewCompanyDescription = findViewById(R.id.cardViewCompanyDescription);
        this.textViewCompanyDescription = findViewById(R.id.textViewCompanyDescription);
        this.linearLayoutInsurancePlans = findViewById(R.id.linearLayoutInsurancePlans);

        this.expandableListView = findViewById(R.id.expandableListView);

        String selectedRequest = getIntent().getExtras().getString("selectedRequest");
        String selectedCompany = getIntent().getExtras().getString("selectedCompany");

        // Typo on Python side
        if (selectedCompany.equals("Allianz")) {
            selectedCompany = "Allianze";
        }

        setCompanyDetails(selectedCompany);

        GetTravelInsuranceCompanyRecommendationRequest request = selectedRequest.equals("req1") ? MyApp.req1 : MyApp.req2;

        GetTravelInsurancePlanRecommendationRequest planRequest = new GetTravelInsurancePlanRecommendationRequest(request.getId(), request.getAvg_income_ttm(), request.getAvg_expense_ttm(),
                request.getOri_country(), request.getDest_country(), request.getTrip_type_oneway(), request.getTrip_type_roundtrip(), request.getTrip_cost(),
                request.getNum_stops(), request.getBooking_date(), request.getDept_date(), request.getReturn_date(), request.getNum_travellers(),
                request.getTraveller_one_dob(), request.getTraveller_two_dob(), request.getTraveller_three_dob(), request.getTraveller_four_dob(), selectedCompany
        );

        String finalSelectedCompany = selectedCompany;

        Call<GetTravelInsurancePlanRecommendationResponse> call = api.getTravelInsurancePlanRecommendation(planRequest);
        call.enqueue(new Callback<GetTravelInsurancePlanRecommendationResponse>() {
            @Override
            public void onResponse(Call<GetTravelInsurancePlanRecommendationResponse> call, Response<GetTravelInsurancePlanRecommendationResponse> response) {
                Log.i("Recommendation ID", planRequest.getId().toString());
                Log.i("Prob 0", response.body().getProb_0().toString());
                Log.i("Prob 1", response.body().getProb_1().toString());
                Log.i("Prob 2", response.body().getProb_2().toString());

                prepareTravelInsurancePlanData(finalSelectedCompany, response.body());

                expandableListAdapter = new ExpandableListAdapter(InsurancePlanActivity.this, listDataHeader, listDataChildAttributes, listDataChildValues);
                expandableListView.setAdapter(expandableListAdapter);
            }

            @Override
            public void onFailure(Call<GetTravelInsurancePlanRecommendationResponse> call, Throwable t) {
                Log.i("           Recommendation ID", planRequest.getId().toString());
                Log.e("Failed to get recommendation", t.getMessage());
                t.printStackTrace();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    cardViewCompanyDescription.setVisibility(View.VISIBLE);
                    linearLayoutInsurancePlans.setVisibility(View.GONE);
                } else {
                    cardViewCompanyDescription.setVisibility(View.GONE);
                    linearLayoutInsurancePlans.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareTravelInsurancePlanData(String company, GetTravelInsurancePlanRecommendationResponse response) {
        listDataHeader = new ArrayList<>();
        listDataChildAttributes = new HashMap<>();
        listDataChildValues = new HashMap<>();

        Map<Double, String> maps = new TreeMap<>();
        Double reducer = 0.000001;

        switch (company) {
            case "AIA":
                maps.put(response.getProb_0(), "Classic");

                if (maps.get(response.getProb_0()) == null) {
                    maps.put(response.getProb_1(), "Deluxe");
                } else {
                    response.setProb_1(response.getProb_1() - reducer);
                    maps.put(response.getProb_1(), "Deluxe");
                    reducer += 0.000001;
                }

                if (maps.get(response.getProb_1()) == null) {
                    maps.put(response.getProb_2(), "Premier");
                } else {
                    response.setProb_2(response.getProb_2() - reducer);
                    maps.put(response.getProb_2(), "Premier");
                }
                break;

            case "AXA":
                maps.put(response.getProb_0(), "Lite");

                if (maps.get(response.getProb_0()) == null) {
                    maps.put(response.getProb_1(), "Easy");
                } else {
                    response.setProb_1(response.getProb_1() - reducer);
                    maps.put(response.getProb_1(), "Easy");
                    reducer += 0.000001;
                }

                if (maps.get(response.getProb_1()) == null) {
                    maps.put(response.getProb_2(), "Pro");
                } else {
                    response.setProb_2(response.getProb_2() - reducer);
                    maps.put(response.getProb_2(), "Pro");
                }
                break;

            case "Allianz":
                maps.put(response.getProb_0(), "Bronze");

                if (maps.get(response.getProb_0()) == null) {
                    maps.put(response.getProb_1(), "Silver");
                } else {
                    response.setProb_1(response.getProb_1() - reducer);
                    maps.put(response.getProb_1(), "Silver");
                    reducer += 0.000001;
                }

                if (maps.get(response.getProb_1()) == null) {
                    maps.put(response.getProb_2(), "Gold");
                } else {
                    response.setProb_2(response.getProb_2() - reducer);
                    maps.put(response.getProb_2(), "Gold");
                }
                break;

            default:
                maps.put(response.getProb_0(), "Travel Lite");

                if (maps.get(response.getProb_0()) == null) {
                    maps.put(response.getProb_1(), "Travel Plus");
                } else {
                    response.setProb_1(response.getProb_1() - reducer);
                    maps.put(response.getProb_1(), "Travel Plus");
                    reducer += 0.000001;
                }

                if (maps.get(response.getProb_1()) == null) {
                    maps.put(response.getProb_2(), "Travel Prestige");
                } else {
                    response.setProb_2(response.getProb_2() - reducer);
                    maps.put(response.getProb_2(), "Travel Prestige");
                }
                break;
        }

        // 2, 1, 0 --> From highly recommended to least recommended
        List<String> rankings = new ArrayList<>();
        maps.forEach((prob, plan) -> rankings.add(plan));

        // Adding child data
        listDataHeader.add(rankings.get(2));
        listDataHeader.add(rankings.get(1));
        listDataHeader.add(rankings.get(0));

        List<String> attributes = new ArrayList<>();
        attributes.add("Personal Accident");
        attributes.add("Overseas Medical Expenses");
        attributes.add("Emergency Medical Evacuation");
        attributes.add("Travel Cancellation");
        attributes.add("Travel Postponement");
        attributes.add("Travel Delay");
        listDataChildAttributes.put(listDataHeader.get(0), attributes);
        listDataChildAttributes.put(listDataHeader.get(1), attributes);
        listDataChildAttributes.put(listDataHeader.get(2), attributes);

        switch (company) {
            case "AIA":
                for (String plan : listDataHeader) {
                    if (plan.equals("Classic")) {
                        listDataChildValues.put(plan, Arrays.asList("$150,000", "$200,000", "$500,000", "$5,000", "$500", "$1,000"));
                    } else if (plan.equals("Deluxe")) {
                        listDataChildValues.put(plan, Arrays.asList("$200,000", "$500,000", "As Charged", "$10,000", "$1,000", "$1,000"));
                    } else {
                        listDataChildValues.put(plan, Arrays.asList("$500,000", "$2,000,000", "As Charged", "$15,000", "$2,000", "$1,000"));
                    }
                }
                break;

            case "AXA":
                for (String plan : listDataHeader) {
                    if (plan.equals("Lite")) {
                        listDataChildValues.put(plan, Arrays.asList("$50,000", "$10,000", "$100,000", "$1,000", "$200", "$500"));
                    } else if (plan.equals("Easy")) {
                        listDataChildValues.put(plan, Arrays.asList("$150,000", "$250,000", "$500,000", "$5,000", "$500", "$1,000"));
                    } else {
                        listDataChildValues.put(plan, Arrays.asList("$500,000", "$300,000", "Unlimited", "$6,000", "$800", "$1,000"));
                    }
                }
                break;

            case "Allianz":
                for (String plan : listDataHeader) {
                    if (plan.equals("Bronze")) {
                        listDataChildValues.put(plan, Arrays.asList("$400,000", "$400,000", "$500,000", "$10,000", "$10,000", "$1,500"));
                    } else if (plan.equals("Silver")) {
                        listDataChildValues.put(plan, Arrays.asList("$1,000,000", "$1,000,000", "Unlimited", "$15,000", "$15,000", "$1,500"));
                    } else {
                        listDataChildValues.put(plan, Arrays.asList("Unlimited", "Unlimited", "Unlimited", "$25,000", "$25,000", "$1,500"));
                    }
                }
                break;

            default:
                for (String plan : listDataHeader) {
                    if (plan.equals("Travel Lite")) {
                        listDataChildValues.put(plan, Arrays.asList("$50,000", "$250,000", "$250,000", "$5,000", "$1,000", "$500"));
                    } else if (plan.equals("Travel Plus")) {
                        listDataChildValues.put(plan, Arrays.asList("$100,000", "$2,000,000", "$2,000,000", "$15,000", "$2,000", "$1,000"));
                    } else {
                        listDataChildValues.put(plan, Arrays.asList("$500,000", "Unlimited", "Unlimited", "$20,000", "$3,000", "$2,000"));
                    }
                }
                break;
        }
    }

    private void setCompanyDetails(String selectedCompany) {
        switch (selectedCompany) {
            case "AIA":
                textViewSelectedCompany.setText("AIA");
                textViewSelectedCompanySlogan.setText(getString(R.string.aia_slogan));
                textViewCompanyDescription.setText(getString(R.string.aia_description));
                break;
            case "AXA":
                textViewSelectedCompany.setText("AXA");
                textViewSelectedCompanySlogan.setText(getString(R.string.axa_slogan));
                textViewCompanyDescription.setText(getString(R.string.axa_description));
                break;
            case "Allianz":
                textViewSelectedCompany.setText("Allianz");
                textViewSelectedCompanySlogan.setText(getString(R.string.allianz_slogan));
                textViewCompanyDescription.setText(getString(R.string.allianz_description));
                break;
            default:
                textViewSelectedCompany.setText("Aviva");
                textViewSelectedCompanySlogan.setText(getString(R.string.aviva_slogan));
                textViewCompanyDescription.setText(getString(R.string.aviva_description));
                break;
        }
    }

    public void subscribe(View view) {

    }
}