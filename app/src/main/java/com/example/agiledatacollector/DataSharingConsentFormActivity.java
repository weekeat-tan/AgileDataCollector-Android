package com.example.agiledatacollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.agiledatacollector.activity.HomeActivity;
import com.example.agiledatacollector.activity.TravelLocationActivity;

public class DataSharingConsentFormActivity extends AppCompatActivity {

    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sharing_consent_form);

        MyApp.sharedPreferences = getSharedPreferences("AgileDataCollector", Context.MODE_PRIVATE);
        currentUser = MyApp.sharedPreferences.getString("currentUser", "");
    }

    public void accept(View view) {

        if (currentUser.equals(getString(R.string.user1))) {
            MyApp.isAgreed1 = true;
        } else {
            MyApp.isAgreed2 = true;
        }

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        finish();
    }

    public void reject(View view) {

        if (currentUser.equals(getString(R.string.user1))) {
            MyApp.isAgreed1 = false;
        } else {
            MyApp.isAgreed2 = false;
        }

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        finish();
    }
}