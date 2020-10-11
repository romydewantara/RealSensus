package com.example.bacodelabs.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bacodelabs.R;
import com.example.bacodelabs.support.DevelopersBottomSheet;
import com.example.bacodelabs.util.BCPreference;
import com.example.bacodelabs.util.Fonts;


public class HomeActivity extends AppCompatActivity {

    // Define or declare all Widgets
    private Fonts fonts;
    private CardView devOne;
    private CardView devTwo;
    private CardView devThree;
    private CardView devFour;
    private TextView tvTitlePage;
    private TextView tvWelcome;
    private TextView tvUsername;
    private TextView tvTitleDevOne;
    private TextView tvSubtitleDevOne;
    private TextView tvTitleDevTwo;
    private TextView tvSubtitleDevTwo;
    private TextView tvTitleDevThree;
    private TextView tvSubtitleDevThree;
    private TextView tvTitleDevFour;
    private TextView tvSubtitleDevFour;
    private TextView tvBC;
    private TextView tvVersion;
    private ImageView btnLogout;
    private ImageView btnMenu;

    // Drawer Components
    private DrawerLayout drawerLayout;
    private TextView tvName;
    private TextView tvHome;
    private TextView tvSetting;

    // Bottom Sheet
    private DevelopersBottomSheet developersBottomSheet;

    // Animation
    private Animation fadeInAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setData();
        initListener();
    }

    // initialize component
    private void init() {
        // Layout Home Components
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

        // Layout Drawer Components
        drawerLayout = findViewById(R.id.drawerLayout);
        tvName = findViewById(R.id.tvName);
        tvHome = findViewById(R.id.tvHome);
        tvSetting = findViewById(R.id.tvSetting);

        fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in);

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
        tvBC.setTypeface(fonts.stThin());
        tvVersion.setTypeface(fonts.stThin());

        tvName.setTypeface(fonts.stRegular());
        tvHome.setTypeface(fonts.stRegular());
        tvSetting.setTypeface(fonts.stRegular());
    }

    private void setData() {
        String developerName = BCPreference.getDeveloperName(getApplicationContext());
        boolean isLoggedIn = BCPreference.getLoggedIn(getApplicationContext());
        if (isLoggedIn) {
            tvWelcome.setText("Hello, welcome back!");
        }
        BCPreference.getInstance(getApplicationContext()).setKeyLoggedIn(true);
        tvUsername.setVisibility(View.INVISIBLE);
        tvUsername.setText(developerName);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvUsername.setVisibility(View.VISIBLE);
                tvUsername.startAnimation(fadeInAnimation);
            }
        }, 1500);
    }

    private void initListener() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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

    private void logout() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
        dialog.setCancelable(false);
        dialog.setMessage("Are you sure you want to sign out?");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BCPreference.logout(HomeActivity.this);
                dialogInterface.dismiss();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}
