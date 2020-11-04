package com.example.agiledatacollector.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.agiledatacollector.MyApp;
import com.example.agiledatacollector.R;
import com.example.agiledatacollector.api.Api;
import com.example.agiledatacollector.api.RetrofitClient;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationRequest;
import com.example.agiledatacollector.model.GetTravelInsuranceCompanyRecommendationResponse;
import com.google.android.material.progressindicator.ProgressIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    public static Double AVG_INCOME_TTM;
    public static Double AVG_EXPENSE_TTM;
    public static String ORI_COUNTRY = "Singapore";

    private Api api;

    private TextView textViewAverageIncome;
    private TextView textViewAverageExpense;

    private TextView textViewTransactionDate1;
    private TextView textViewTransactionHistory1;
    private TextView textViewTransactionPrice1;

    private TextView textViewTransactionDate2;
    private TextView textViewTransactionHistory2;
    private TextView textViewTransactionPrice2;

    private HorizontalScrollView horizontalScrollViewInsuranceRecommendation;
    private ProgressIndicator progressIndicatorInsuranceRecommendation;

    private ImageView imageViewCompanyLogo1;
    private ImageView imageViewCompanyLogo2;
    private ImageView imageViewCompanyLogo3;
    private ImageView imageViewCompanyLogo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.api = RetrofitClient.getRetrofitInstance().create(Api.class);

        MyApp.sharedPreferences = getSharedPreferences("AgileDataCollector", Context.MODE_PRIVATE);
        String currentUser = MyApp.sharedPreferences.getString("currentUser", "");

        this.textViewAverageIncome = findViewById(R.id.textViewAverageIncome);
        this.textViewAverageExpense = findViewById(R.id.textViewAverageExpense);

        this.textViewTransactionDate1 = findViewById(R.id.textViewTransactionDate1);
        this.textViewTransactionHistory1 = findViewById(R.id.textViewTransactionHistory1);
        this.textViewTransactionPrice1 = findViewById(R.id.textViewTransactionPrice1);

        this.textViewTransactionDate2 = findViewById(R.id.textViewTransactionDate2);
        this.textViewTransactionHistory2 = findViewById(R.id.textViewTransactionHistory2);
        this.textViewTransactionPrice2 = findViewById(R.id.textViewTransactionPrice2);

        this.horizontalScrollViewInsuranceRecommendation = findViewById(R.id.horizontalScrollViewInsuranceRecommendation);
        this.horizontalScrollViewInsuranceRecommendation.setVisibility(View.GONE);

        this.progressIndicatorInsuranceRecommendation = findViewById(R.id.progressIndicatorInsuranceRecommendation);
        this.progressIndicatorInsuranceRecommendation.setVisibility(View.VISIBLE);

        this.imageViewCompanyLogo1 = findViewById(R.id.companyLogo1);
        this.imageViewCompanyLogo2 = findViewById(R.id.companyLogo2);
        this.imageViewCompanyLogo3 = findViewById(R.id.companyLogo3);
        this.imageViewCompanyLogo4 = findViewById(R.id.companyLogo4);

        if (currentUser != null) {

            initializeFlightPurchaseHistory(currentUser);
            // [ AIA AXA Allianze Aviva ]
            doGetTravelInsuranceRecommendation();

            new Handler().postDelayed(() -> {
                this.progressIndicatorInsuranceRecommendation.setVisibility(View.GONE);
                this.horizontalScrollViewInsuranceRecommendation.setVisibility(View.VISIBLE);
            }, 1750);
        }
    }

    public void viewTravelInsuranceRecommendation(View view) {
        Intent intent = new Intent(this, TravelLocationActivity.class);
        startActivity(intent);
    }

    private void initializeFlightPurchaseHistory(String user) {
        if (user.equals(getString(R.string.user1))) {
            AVG_INCOME_TTM = 3476.88;
            AVG_EXPENSE_TTM = 1759.99;

            this.textViewAverageIncome.setText(AVG_INCOME_TTM.toString());
            this.textViewAverageExpense.setText(AVG_EXPENSE_TTM.toString());

            // Outcome: AIA
            MyApp.req1 = new GetTravelInsuranceCompanyRecommendationRequest(1001, AVG_INCOME_TTM, AVG_EXPENSE_TTM, ORI_COUNTRY, "China", true, false,
                    1024.63, 3, "2020/10/22", "2021/02/02", "2021/02/23", 3, "1983/04/24", "1988/09/08", "1993/04/13", "");

            this.textViewTransactionDate1.setText("MON, 26 OCT 2020");
            this.textViewTransactionHistory1.setText("FLYSCOOT.00000000CDVKH3 22 OCT");
            this.textViewTransactionPrice1.setText("SGD -1024.63");

            // Outcome: AXA
            MyApp.req2 = new GetTravelInsuranceCompanyRecommendationRequest(1002, AVG_INCOME_TTM, AVG_EXPENSE_TTM, ORI_COUNTRY, "Malaysia", false, true,
                    1685.12, 0, "2020/10/28", "2021/02/08", "2021/02/26", 1, "1982/10/03", "1987/02/26", "1988/03/16", ""
            );

            this.textViewTransactionDate2.setText("FRI, 30 OCT 2020");
            this.textViewTransactionHistory2.setText("Singapore Airlines MSIAH8 28 OCT");
            this.textViewTransactionPrice2.setText("SGD -1685.12");
        } else if (user.equals(getString(R.string.user2))) {
            AVG_INCOME_TTM = 6181.49;
            AVG_EXPENSE_TTM = 2404.25;

            this.textViewAverageIncome.setText(AVG_INCOME_TTM.toString());
            this.textViewAverageExpense.setText(AVG_EXPENSE_TTM.toString());

            // Outcome: AIA
            MyApp.req1 = new GetTravelInsuranceCompanyRecommendationRequest(1003, AVG_INCOME_TTM, AVG_EXPENSE_TTM, ORI_COUNTRY, "United States", true, false,
                    1782.78, 2, "2020/10/14", "2021/02/07", "2021/03/21", 2, "1974/01/21", "2010/07/09", "", "");

            this.textViewTransactionDate1.setText("FRI, 16 OCT 2020");
            this.textViewTransactionHistory1.setText("Qatar Airways QTUS3F 14 OCT");
            this.textViewTransactionPrice1.setText("SGD -1782.78");

            // Outcome: Allianze
            MyApp.req2 = new GetTravelInsuranceCompanyRecommendationRequest(1004, AVG_INCOME_TTM, AVG_EXPENSE_TTM, ORI_COUNTRY, "Taiwan", false, true,
                    838.08, 0, "2020/10/19", "2021/02/01", "2021/03/26", 3, "2007/12/22", "1987/02/26", "1988/03/16", ""
            );

            this.textViewTransactionDate2.setText("WED, 21 OCT 2020");
            this.textViewTransactionHistory2.setText("FLYSCOOT.00000000XDP5T3 19 OCT");
            this.textViewTransactionPrice2.setText("SGD -838.08");
        }
    }

    private void doGetTravelInsuranceRecommendation() {
        Call<GetTravelInsuranceCompanyRecommendationResponse> call1 = api.getTravelInsuranceCompanyRecommendation(MyApp.req1);

        call1.enqueue(new Callback<GetTravelInsuranceCompanyRecommendationResponse>() {
            @Override
            public void onResponse(Call<GetTravelInsuranceCompanyRecommendationResponse> call, Response<GetTravelInsuranceCompanyRecommendationResponse> response) {
                Log.i("  Recommendation ID", MyApp.req1.getId().toString());
                Log.i("Recommended Company", response.body().getRecommended_company());
                Log.i("           Prob AIA", response.body().getProb_aia().toString());
                Log.i("           Prob AXA", response.body().getProb_axa().toString());
                Log.i("      Prob Allianze", response.body().getProb_allianze().toString());
                Log.i("         Prob Aviva", response.body().getProb_aviva().toString());

                List<String> companyNamesAdded = new ArrayList<>();
                MyApp.recommendationResponses.add(response.body());
                companyNamesAdded.add(response.body().getRecommended_company());
                setCompanyLogo(response.body().getRecommended_company(), imageViewCompanyLogo1);

                Call<GetTravelInsuranceCompanyRecommendationResponse> call2 = api.getTravelInsuranceCompanyRecommendation(MyApp.req2);

                call2.enqueue(new Callback<GetTravelInsuranceCompanyRecommendationResponse>() {
                    @Override
                    public void onResponse(Call<GetTravelInsuranceCompanyRecommendationResponse> call, Response<GetTravelInsuranceCompanyRecommendationResponse> response) {
                        Log.i("  Recommendation ID", MyApp.req2.getId().toString());
                        Log.i("Recommended Company", response.body().getRecommended_company());
                        Log.i("           Prob AIA", response.body().getProb_aia().toString());
                        Log.i("           Prob AXA", response.body().getProb_axa().toString());
                        Log.i("      Prob Allianze", response.body().getProb_allianze().toString());
                        Log.i("         Prob Aviva", response.body().getProb_aviva().toString());

                        MyApp.recommendationResponses.add(response.body());
                        companyNamesAdded.add(response.body().getRecommended_company());
                        setCompanyLogo(response.body().getRecommended_company(), imageViewCompanyLogo2);

                        List<ImageView> imageViews = Arrays.asList(imageViewCompanyLogo3, imageViewCompanyLogo4);
                        List<String> companyNames = Arrays.asList("Allianze", "AXA", "AIA", "Prudential");

                        for (String name : companyNames) {
                            if (!companyNamesAdded.contains(name)) {
                                companyNamesAdded.add(name);
                                setCompanyLogo(name, imageViewCompanyLogo3);
                                break;
                            }
                        }

                        for (String name : companyNames) {
                            if (!companyNamesAdded.contains(name)) {
                                companyNamesAdded.add(name);
                                setCompanyLogo(name, imageViewCompanyLogo4);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTravelInsuranceCompanyRecommendationResponse> call, Throwable t) {
                        Log.i("           Recommendation ID", MyApp.req2.getId().toString());
                        Log.e("Failed to get recommendation", t.getMessage());
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<GetTravelInsuranceCompanyRecommendationResponse> call, Throwable t) {
                Log.i("           Recommendation ID", MyApp.req1.getId().toString());
                Log.e("Failed to get recommendation", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void setCompanyLogo(String company, ImageView imageView) {
        if (company.equals("AIA")) {
            imageView.setImageResource(R.drawable.aia);
        } else if (company.equals("AXA")) {
            imageView.setImageResource(R.drawable.axa);
        } else if (company.equals("Allianze")) {
            imageView.setImageResource(R.drawable.allianz);
        } else {
            imageView.setImageResource(R.drawable.aviva);
        }
    }
}