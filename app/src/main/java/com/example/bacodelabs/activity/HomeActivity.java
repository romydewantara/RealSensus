package com.example.bacodelabs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bacodelabs.R;
import com.example.bacodelabs.support.DevelopersBottomSheet;
import com.example.bacodelabs.support.RoundedBottomSheetDialog;
import com.example.bacodelabs.util.Fonts;


public class HomeActivity extends AppCompatActivity {

    // Define or declare all widgets
    Fonts fonts;
    CardView devOne;
    CardView devTwo;
    CardView devThree;
    CardView devFour;
    TextView tvTitlePage;
    TextView tvWelcome;
    TextView tvUsername;
    TextView tvTitleDevOne;
    TextView tvSubtitleDevOne;
    TextView tvTitleDevTwo;
    TextView tvSubtitleDevTwo;
    TextView tvTitleDevThree;
    TextView tvSubtitleDevThree;
    TextView tvTitleDevFour;
    TextView tvSubtitleDevFour;
    TextView tvBC;
    TextView tvVersion;
    ImageView btnLogout;
    ImageView btnMenu;
    DrawerLayout drawerLayout;
    DevelopersBottomSheet developersBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        initListener();
    }

    // initialize component
    private void init() {
        fonts = new Fonts(getApplicationContext());
        tvTitlePage = findViewById(R.id.tvTitlePage);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvUsername = findViewById(R.id.tvUsername);
        tvTitleDevOne = findViewById(R.id.tvTitleDevOne);
        tvSubtitleDevOne = findViewById(R.id.tvSubtitleDevOne);
        tvTitleDevTwo = findViewById(R.id.tvTitleDevTwo);
        tvSubtitleDevTwo = findViewById(R.id.tvSubtitleDevTwo);
        tvTitleDevThree = findViewById(R.id.tvTitleDevThree);
        tvSubtitleDevThree = findViewById(R.id.tvSubtitleDevThree);
        tvTitleDevFour = findViewById(R.id.tvTitleDevFour);
        tvSubtitleDevFour = findViewById(R.id.tvSubtitleDevFour);
        tvBC = findViewById(R.id.tvBC);
        tvVersion = findViewById(R.id.tvVersion);
        devOne = findViewById(R.id.devOne);
        devTwo = findViewById(R.id.devTwo);
        devThree = findViewById(R.id.devThree);
        devFour = findViewById(R.id.devFour);
        btnLogout = findViewById(R.id.btnLogout);
        btnMenu = findViewById(R.id.btnMenu);
        drawerLayout = findViewById(R.id.drawerLayout);

        setCosmetic();
    }

    private void setCosmetic() {
        tvTitlePage.setTypeface(fonts.stBold());
        tvWelcome.setTypeface(fonts.stRegular());
        tvUsername.setTypeface(fonts.stBold());
        tvTitleDevOne.setTypeface(fonts.stBold());
        tvSubtitleDevOne.setTypeface(fonts.stRegular());
        tvTitleDevTwo.setTypeface(fonts.stBold());
        tvSubtitleDevTwo.setTypeface(fonts.stRegular());
        tvTitleDevThree.setTypeface(fonts.stBold());
        tvSubtitleDevThree.setTypeface(fonts.stRegular());
        tvTitleDevFour.setTypeface(fonts.stBold());
        tvSubtitleDevFour.setTypeface(fonts.stRegular());
        tvBC.setTypeface(fonts.stRegular());
        tvVersion.setTypeface(fonts.stRegular());
    }

    private void initListener() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogout = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        devOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                developersBottomSheet = new DevelopersBottomSheet(HomeActivity.this);
                if (getSupportFragmentManager() != null) {
                    developersBottomSheet.show(getSupportFragmentManager(), developersBottomSheet.getTag());
                }
            }
        });
        devTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hello Developers Team 2! \nComing soon.", Toast.LENGTH_SHORT).show();
            }
        });
        devThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hello Developers Team 3! \nComing soon.", Toast.LENGTH_SHORT).show();
            }
        });
        devFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Hello Developers Team 4! \nComing soon.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
