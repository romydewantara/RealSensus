package com.example.bacodelabs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.bacodelabs.R;
import com.example.bacodelabs.util.Fonts;

public class LoginActivity extends AppCompatActivity {

    Fonts fonts;
    Button btnLogin;
    EditText etEmail;
    EditText etPassword;
    TextView tvRegister;
    LottieAnimationView lottieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initListener();
        playLottieView();

    }

    // initialize all variables
    private void init() {
        fonts = new Fonts(getApplicationContext());
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        lottieView = findViewById(R.id.lottieView);

        setCosmetic();
    }

    private void setCosmetic() {
        etEmail.setTypeface(fonts.stRegular());
        etPassword.setTypeface(fonts.stRegular());
        tvRegister.setTypeface(fonts.stRegular());
        btnLogin.setTypeface(fonts.stRegular());
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
    }

    private void goToHome() {
        Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intentLogin);
        finish();
    }

    private void playLottieView() {
        lottieView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        lottieView.setRepeatCount(LottieDrawable.INFINITE);
    }

}
