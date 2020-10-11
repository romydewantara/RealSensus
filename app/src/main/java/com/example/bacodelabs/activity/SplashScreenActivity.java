package com.example.bacodelabs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.bacodelabs.R;
import com.example.bacodelabs.util.BCPreference;
import com.example.bacodelabs.util.Fonts;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView splashTitle;
    private Fonts fonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToLogin();
            }
        }, 3000);
    }

    private void init() {
        fonts = new Fonts(getApplicationContext());
        splashTitle = findViewById(R.id.splashTitle);
        splashTitle.setTypeface(fonts.stBold());
    }

    private void goToLogin() {
        Intent intent = null;
        if (BCPreference.getInstance(getApplicationContext()).getLoggedIn(getApplicationContext())) {
            intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
        } else {
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

}
