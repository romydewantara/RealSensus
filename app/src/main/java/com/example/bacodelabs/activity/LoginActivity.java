package com.example.bacodelabs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.bacodelabs.R;
import com.example.bacodelabs.util.BCPreference;
import com.example.bacodelabs.util.Fonts;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Fonts fonts;
    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvRegister;
    private TextView tvBC;
    private TextView tvVersion;
    private LottieAnimationView lottieView;
    private ArrayList<String> developers;
    private ArrayList<String> users;
    private String email = "";
    private int uniqueId = 0;
    private String username = "";

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
        tvBC = findViewById(R.id.tvBC);
        tvVersion = findViewById(R.id.tvVersion);
        lottieView = findViewById(R.id.lottieView);

        developers = new ArrayList<>();
        users = new ArrayList<>();
        developers.add(getApplicationContext().getResources().getString(R.string.mail_adit));
        users.add(getApplicationContext().getResources().getString(R.string.name_adit));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_adom));
        users.add(getApplicationContext().getResources().getString(R.string.name_adom));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_avip));
        users.add(getApplicationContext().getResources().getString(R.string.name_avip));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_budi));
        users.add(getApplicationContext().getResources().getString(R.string.name_budi));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_bidol));
        users.add(getApplicationContext().getResources().getString(R.string.name_bidol));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_fajar));
        users.add(getApplicationContext().getResources().getString(R.string.name_fajar));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_jams));
        users.add(getApplicationContext().getResources().getString(R.string.name_jams));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_romy));
        users.add(getApplicationContext().getResources().getString(R.string.name_romy));
        developers.add(getApplicationContext().getResources().getString(R.string.mail_wawa));
        users.add(getApplicationContext().getResources().getString(R.string.name_wawa));
        developers.add(getApplicationContext().getResources().getString(R.string.admin_account));
        users.add(getApplicationContext().getResources().getString(R.string.name_admin));

        setCosmetic();
    }

    private void setCosmetic() {
        etEmail.setTypeface(fonts.stRegular());
        etPassword.setTypeface(fonts.stRegular());
        tvRegister.setTypeface(fonts.stRegular());
        tvBC.setTypeface(fonts.stThin());
        tvVersion.setTypeface(fonts.stThin());
        btnLogin.setTypeface(fonts.stBold());
    }

    private void initListener() {
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[2].getBounds().width())) {
                        if(etPassword.getTransformationMethod() instanceof PasswordTransformationMethod){
                            etPassword.setTransformationMethod(null);
                            Drawable img = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_visibility_off);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(null,null, img,null);
                        } else {
                            etPassword.setTransformationMethod(new PasswordTransformationMethod());
                            Drawable img = ContextCompat.getDrawable(view.getContext(), R.drawable.ic_visibility_on);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(null,null, img,null);}
                    }
                }
                return false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Upcoming features.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validation() {
        for (int i = 0; i < developers.size(); i++) {
            if (etEmail.getText().toString().equalsIgnoreCase(developers.get(i))) {
                email = developers.get(i);
                username = users.get(i);
                uniqueId = i;
                break;
            }
        }
        if (etEmail.getText().toString().equalsIgnoreCase("")) {
            etEmail.setError("Please enter your email");
        } else if (!etEmail.getText().toString().equalsIgnoreCase(email)) {
            etEmail.setError("Please enter your valid email");
        } else if (etPassword.getText().toString().equalsIgnoreCase("")) {
            etPassword.setError("Please enter your password");
        } else if (!etPassword.getText().toString().equalsIgnoreCase("password")) {
            etPassword.setError("Password doesn't match");
        } else {
            goToHome();
        }
    }

    private void goToHome() {
        BCPreference.getInstance(getApplicationContext()).setDeveloperId(uniqueId);
        BCPreference.getInstance(getApplicationContext()).setDeveloperName(username);
        BCPreference.getInstance(getApplicationContext()).setUsername(getApplicationContext(), email);
        Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intentLogin);
        finish();
    }

    private void playLottieView() {
        lottieView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        lottieView.setRepeatCount(LottieDrawable.INFINITE);
    }

}
