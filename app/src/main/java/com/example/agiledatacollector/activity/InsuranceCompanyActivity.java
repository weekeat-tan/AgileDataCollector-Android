package com.example.agiledatacollector.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agiledatacollector.InsurancePlanActivity;
import com.example.agiledatacollector.MyApp;
import com.example.agiledatacollector.R;
import com.example.agiledatacollector.api.Api;
import com.example.agiledatacollector.api.RetrofitClient;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationResponse;
import com.example.agiledatacollector.model.GetTravelInsurancePlanRecommendationRequest;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuranceCompanyActivity extends AppCompatActivity {
    private Api api;

    private TextView textViewSelectedCountry;

    private ProgressIndicator progressIndicatorInsuranceCompanies;
    private LinearLayout linearLayoutInsuranceCompanies;

    private ConstraintLayout constraintLayoutCompany1;
    private TextView textViewPrice1;
    private TextView textViewCompany1;
    private TextView textViewCompanySlogan1;

    private ConstraintLayout constraintLayoutCompany2;
    private TextView textViewPrice2;
    private TextView textViewCompany2;
    private TextView textViewCompanySlogan2;

    private ConstraintLayout constraintLayoutCompany3;
    private TextView textViewPrice3;
    private TextView textViewCompany3;
    private TextView textViewCompanySlogan3;

    private ConstraintLayout constraintLayoutCompany4;
    private TextView textViewPrice4;
    private TextView textViewCompany4;
    private TextView textViewCompanySlogan4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_company);

        this.api = RetrofitClient.getRetrofitInstance().create(Api.class);

        this.textViewSelectedCountry = findViewById(R.id.textViewSelectedCountry);

        this.progressIndicatorInsuranceCompanies = findViewById(R.id.progressIndicatorInsuranceCompanies);
        this.progressIndicatorInsuranceCompanies.setVisibility(View.VISIBLE);

        this.linearLayoutInsuranceCompanies = findViewById(R.id.linearLayoutInsuranceCompanies);
        this.linearLayoutInsuranceCompanies.setVisibility(View.GONE);

        this.textViewPrice1 = findViewById(R.id.textViewPrice1);
        this.textViewCompany1 = findViewById(R.id.textViewCompany1);
        this.textViewCompanySlogan1 = findViewById(R.id.textViewCompanySlogan1);
        this.constraintLayoutCompany1 = findViewById(R.id.constraintLayoutCompany1);

        this.textViewPrice2 = findViewById(R.id.textViewPrice2);
        this.textViewCompany2 = findViewById(R.id.textViewCompany2);
        this.textViewCompanySlogan2 = findViewById(R.id.textViewCompanySlogan2);
        this.constraintLayoutCompany2 = findViewById(R.id.constraintLayoutCompany2);

        this.textViewPrice3 = findViewById(R.id.textViewPrice3);
        this.textViewCompany3 = findViewById(R.id.textViewCompany3);
        this.textViewCompanySlogan3 = findViewById(R.id.textViewCompanySlogan3);
        this.constraintLayoutCompany3 = findViewById(R.id.constraintLayoutCompany3);

        this.textViewPrice4 = findViewById(R.id.textViewPrice4);
        this.textViewCompany4 = findViewById(R.id.textViewCompany4);
        this.textViewCompanySlogan4 = findViewById(R.id.textViewCompanySlogan4);
        this.constraintLayoutCompany4 = findViewById(R.id.constraintLayoutCompany4);

        new Handler().postDelayed(() -> {
            this.progressIndicatorInsuranceCompanies.setVisibility(View.GONE);
            this.linearLayoutInsuranceCompanies.setVisibility(View.VISIBLE);
        }, 5000);

        String selectedRequest = getIntent().getExtras().getString("selectedRequest");
        GetTravelInsuranceCompanyRecommendationRequest request = selectedRequest.equals("req1") ? MyApp.req1 : MyApp.req2;

        if (request != null) {

            this.textViewSelectedCountry.setText(request.getDest_country() + " (" + request.getDept_date() + " - " + request.getReturn_date() + ")");

            Call<GetTravelInsuranceCompanyRecommendationResponse> call = api.getTravelInsuranceCompanyRecommendation(request);

            call.enqueue(new Callback<GetTravelInsuranceCompanyRecommendationResponse>() {
                @Override
                public void onResponse(Call<GetTravelInsuranceCompanyRecommendationResponse> call, Response<GetTravelInsuranceCompanyRecommendationResponse> response) {
                    Log.i("InsuranceCompanyActivity", "Creating map...");
                    Log.i("       Recommendation ID", request.getId().toString());
                    Log.i("     Recommended Company", response.body().getRecommended_company());
                    Log.i("                Prob AIA", response.body().getProb_aia().toString());
                    Log.i("                Prob AXA", response.body().getProb_axa().toString());
                    Log.i("           Prob Allianze", response.body().getProb_allianze().toString());
                    Log.i("              Prob Aviva", response.body().getProb_aviva().toString());

                    Map<Double, String> maps = new TreeMap<>();
                    Double reducer = 0.000001;
                    maps.put(response.body().getProb_aia(), "AIA");

                    if (maps.get(response.body().getProb_axa()) == null) {
                        maps.put(response.body().getProb_axa(), "AXA");
                    } else {
                        response.body().setProb_axa(response.body().getProb_axa() - reducer);
                        maps.put(response.body().getProb_axa(), "AXA");
                        reducer += 0.000001;
                    }

                    if (maps.get(response.body().getProb_allianze()) == null) {
                        maps.put(response.body().getProb_allianze(), "Allianz");
                    } else {
                        response.body().setProb_allianze(response.body().getProb_allianze() - reducer);
                        maps.put(response.body().getProb_allianze(), "Allianz");
                        reducer += 0.000001;
                    }

                    if (maps.get(response.body().getProb_aviva()) == null) {
                        maps.put(response.body().getProb_aviva(), "Aviva");
                    } else {
                        response.body().setProb_aviva(response.body().getProb_aviva() - reducer);
                        maps.put(response.body().getProb_aviva(), "Aviva");
                        reducer += 0.000001;
                    }

                    List<String> rankings = new ArrayList<>();
                    maps.forEach((prob, comp) -> rankings.add(comp));

                    textViewCompany1.setText(rankings.get(3));
                    constraintLayoutCompany1.setTag(rankings.get(3));

                    textViewCompany2.setText(rankings.get(2));
                    constraintLayoutCompany2.setTag(rankings.get(2));

                    textViewCompany3.setText(rankings.get(1));
                    constraintLayoutCompany3.setTag(rankings.get(1));

                    textViewCompany4.setText(rankings.get(0));
                    constraintLayoutCompany4.setTag(rankings.get(0));

                    setPriceAndSlogan(textViewCompany1, textViewCompanySlogan1, textViewPrice1);
                    setPriceAndSlogan(textViewCompany2, textViewCompanySlogan2, textViewPrice2);
                    setPriceAndSlogan(textViewCompany3, textViewCompanySlogan3, textViewPrice3);
                    setPriceAndSlogan(textViewCompany4, textViewCompanySlogan4, textViewPrice4);
                }

                @Override
                public void onFailure(Call<GetTravelInsuranceCompanyRecommendationResponse> call, Throwable t) {
                    Log.i("           Recommendation ID", MyApp.req1.getId().toString());
                    Log.e("Failed to get recommendation", t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }

    private void setPriceAndSlogan(TextView textViewCompany, TextView textViewSlogan, TextView textViewPrice) {
        switch (textViewCompany.getText().toString()) {
            case "AIA":
                textViewSlogan.setText("Travel smart with the protection you choose.");
                textViewPrice.setText("128.00 - 226.80");
                break;
            case "AXA":
                textViewSlogan.setText("Go conquer the world. Leave the rest to us.");
                textViewPrice.setText("71.50 - 171.76");
                break;
            case "Allianz":
                textViewSlogan.setText("Well insured by Allianz.");
                textViewPrice.setText("139.00 - 236.00");
                break;
            default:
                textViewSlogan.setText("We've got you covered.");
                textViewPrice.setText("142.32 - 304.86");
                break;
        }
    }

    public void viewTravelInsurancePlanRecommendation(View view) {
        String selectedCompany = view.getTag().toString();
        String selectedRequest = getIntent().getExtras().getString("selectedRequest");

//        Intent intent = new Intent(this, InsurancePlanActivity.class);
//        intent.putExtra("selectedRequest", selectedRequest);
//        intent.putExtra("selectedCompany", selectedCompany);
//
//        startActivity(intent);
    }

}