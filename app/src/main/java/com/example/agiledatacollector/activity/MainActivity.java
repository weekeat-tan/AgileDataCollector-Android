package com.example.agiledatacollector.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agiledatacollector.DataSharingConsentFormActivity;
import com.example.agiledatacollector.MyApp;
import com.example.agiledatacollector.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private static final int flag = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApp.sharedPreferences = getSharedPreferences("AgileDataCollector", Context.MODE_PRIVATE);
        MyApp.recommendationResponses = new ArrayList<>();

        this.getWindow().getDecorView().setSystemUiVisibility(flags);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        MyApp.sharedPreferences = getSharedPreferences("AgileDataCollector", Context.MODE_PRIVATE);
        String username1 = MyApp.sharedPreferences.getString("username1", "");
        assert username1 != null;
        if (username1.equals("")) {
            MyApp.sharedPreferences.edit().putString("username1", getString(R.string.user1)).apply();
        }

        String username2 = MyApp.sharedPreferences.getString("username2", "");
        assert username2 != null;
        if (username2.equals("")) {
            MyApp.sharedPreferences.edit().putString("username2", getString(R.string.user2)).apply();
        }

        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
    }

    public void login(View view) {
        String usernameInput = this.username.getText().toString();
        String passwordInput = this.password.getText().toString();

        if (usernameInput.equals(getString(R.string.user1)) && passwordInput.equals("password")) {

            MyApp.sharedPreferences.edit().putString("currentUser", getString(R.string.user1)).apply();
            Toast.makeText(MainActivity.this, "Logged in successfully as " + getString(R.string.user1) + "!", Toast.LENGTH_LONG).show();

            if (MyApp.isFirstLogin1) {
                Intent intent = new Intent(this, DataSharingConsentFormActivity.class);
                startActivity(intent);
                MyApp.isFirstLogin1 = false;
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }

        } else if (usernameInput.equals(getString(R.string.user2)) && passwordInput.equals("password")) {

            MyApp.isFirstLogin1 = false;
            MyApp.sharedPreferences.edit().putString("currentUser", getString(R.string.user2)).apply();
            Toast.makeText(MainActivity.this, "Logged in successfully as " + getString(R.string.user2) + "!", Toast.LENGTH_LONG).show();

            if (MyApp.isFirstLogin2) {
                Intent intent = new Intent(this, DataSharingConsentFormActivity.class);
                startActivity(intent);
                MyApp.isFirstLogin2 = false;
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }

        } else {

            Toast.makeText(MainActivity.this, "Invalid login credentials! Please try again!", Toast.LENGTH_LONG).show();

        }

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}