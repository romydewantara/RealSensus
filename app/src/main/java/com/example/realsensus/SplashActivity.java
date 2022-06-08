package com.example.realsensus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.realsensus.helper.RSPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent openMainActivity;
            if (RSPreference.getInstance(getApplicationContext()).isSignIn()) {
                openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                openMainActivity = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(openMainActivity);
            finish();
        }, 2000);
    }
}