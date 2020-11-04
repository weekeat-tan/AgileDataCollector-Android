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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        setCompanyDescripton(selectedCompany);

        GetTravelInsuranceCompanyRecommendationRequest request = selectedRequest.equals("req1") ? MyApp.req1 : MyApp.req2;

        GetTravelInsurancePlanRecommendationRequest planRequest = new GetTravelInsurancePlanRecommendationRequest(request.getId(), request.getAvg_income_ttm(), request.getAvg_expense_ttm(),
                request.getOri_country(), request.getDest_country(), request.getTrip_type_oneway(), request.getTrip_type_roundtrip(), request.getTrip_cost(),
                request.getNum_stops(), request.getBooking_date(), request.getDept_date(), request.getReturn_date(), request.getNum_travellers(),
                request.getTraveller_one_dob(), request.getTraveller_two_dob(), request.getTraveller_three_dob(), request.getTraveller_four_dob(), selectedCompany
        );

        Call<GetTravelInsurancePlanRecommendationResponse> call = api.getTravelInsurancePlanRecommendation(planRequest);
        call.enqueue(new Callback<GetTravelInsurancePlanRecommendationResponse>() {
            @Override
            public void onResponse(Call<GetTravelInsurancePlanRecommendationResponse> call, Response<GetTravelInsurancePlanRecommendationResponse> response) {
                Log.i("Recommendation ID", planRequest.getId().toString());
                Log.i("Prob 0", response.body().getProb_0().toString());
                Log.i("Prob 1", response.body().getProb_1().toString());
                Log.i("Prob 2", response.body().getProb_2().toString());

                prepareListData();

                Log.i("List Data Header", listDataHeader.toString());
                Log.i("List Data Child Attributes", listDataChildAttributes.toString());
                Log.i("List Data Child Values", listDataChildValues.toString());

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
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChildAttributes = new HashMap<>();
        listDataChildValues = new HashMap<>();

        // Adding child data
        listDataHeader.add("Plan 1");
        listDataHeader.add("Plan 2");
        listDataHeader.add("Plan 3");

        List<String> attributes = new ArrayList<>();
        attributes.add("Trip Cancellation");
        attributes.add("Trip Interruption");
        attributes.add("Trip Delay");
        attributes.add("Missed Connection");
        listDataChildAttributes.put(listDataHeader.get(0), attributes);
        listDataChildAttributes.put(listDataHeader.get(1), attributes);
        listDataChildAttributes.put(listDataHeader.get(2), attributes);

        List<String> values = new ArrayList<>();
        values.add("100% of insured trip cost");
        values.add("100% of insured trip cost");
        values.add("100% of insured trip cost");
        values.add("100% of insured trip cost");
        listDataChildValues.put(listDataHeader.get(0), values);
        listDataChildValues.put(listDataHeader.get(1), values);
        listDataChildValues.put(listDataHeader.get(2), values);
    }

    private void setCompanyDescripton(String selectedCompany) {
        switch (selectedCompany) {
            case "AIA":
                textViewCompanyDescription.setText(getString(R.string.aia_description));
                break;
            case "AXA":
                textViewCompanyDescription.setText(getString(R.string.axa_description));
                break;
            case "Allianz":
                textViewCompanyDescription.setText(getString(R.string.allianz_description));
                break;
            default:
                textViewCompanyDescription.setText(getString(R.string.aviva_description));
                break;
        }
    }

    public void subscribe(View view) {

    }
}