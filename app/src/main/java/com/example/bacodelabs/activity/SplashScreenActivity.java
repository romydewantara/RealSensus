package com.example.bacodelabs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.bacodelabs.R;
import com.example.bacodelabs.util.Fonts;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView splashTitle;
    private Fonts fonts;
    //testing commit-push

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fonts = new Fonts(getApplicationContext());

        splashTitle = findViewById(R.id.splashTitle);
        splashTitle.setTypeface(fonts.stBold());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

}
