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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bacodelabs.R;
import com.example.bacodelabs.fragment.TeamFourFragment;
import com.example.bacodelabs.fragment.TeamOneFragment;
import com.example.bacodelabs.fragment.TeamThreeFragment;
import com.example.bacodelabs.fragment.TeamTwoFragment;
import com.example.bacodelabs.libs.BCCustomDialog;
import com.example.bacodelabs.libs.BCLoadingDialog;
import com.example.bacodelabs.listener.CustomDialogListener;
import com.example.bacodelabs.model.Developer;
import com.example.bacodelabs.support.DevelopersBottomSheet;
import com.example.bacodelabs.util.BCPreference;
import com.example.bacodelabs.util.Fonts;
import com.example.bacodelabs.viewmodel.DataViewModel;

import java.util.ArrayList;

/**
 * Created by: kamikaze
 * on October, 12 2020
 * */

public class HomeActivity extends AppCompatActivity implements CustomDialogListener {

    public final static int FRAGMENT_GOTO_TEAM_ONE = 0;
    public final static int FRAGMENT_GOTO_TEAM_TWO = 1;
    public final static int FRAGMENT_GOTO_TEAM_THREE = 2;
    public final static int FRAGMENT_GOTO_TEAM_FOUR = 3;
    public final static int FRAGMENT_GOTO_TEAM_ACCOUNT = 4;

    public final static String TAG_FRAGMENT_TEAM_ONE = "team_one";
    public final static String TAG_FRAGMENT_TEAM_TWO = "team_two";
    public final static String TAG_FRAGMENT_TEAM_THREE = "team_three";
    public final static String TAG_FRAGMENT_TEAM_FOUR = "team_four";
    public final static String TAG_FRAGMENT_ACCOUNT = "account_fragment";

    public static final String TAG_SAVED_FRAGMENT = "fragment";

    // Sebat dengan botak
    // Magic chess bareng botak, uhh mantab
    // Santia Mantan Terindah Budi
    // anjay

    // Define or declare all Widgets
    private Fonts fonts;
    private ImageView btnLogout;
    private ImageView btnMenu;
    private TextView tvTitlePage;
    private TextView tvWelcome;
    private TextView tvUsername;
    private TextView tvDevOne;
    private TextView tvDevTwo;
    private TextView tvDevThree;
    private TextView tvDevFour;
    private TextView tvDevFive;
    private ImageView imageIcon1;
    private ImageView imageIcon2;
    private ImageView imageIcon3;
    private ImageView imageIcon4;
    private FrameLayout frameLayoutContent;
    private LinearLayout layoutBottomBar;
    private RelativeLayout relative1;
    private RelativeLayout relative2;
    private RelativeLayout relative3;
    private RelativeLayout relative4;
    private RelativeLayout relative5;

    // Drawer Components
    private DrawerLayout drawerLayout;
    private ImageView btnClose;
    private TextView tvName;
    private TextView tvHome;
    private TextView tvSetting;

    // Bottom Sheet
    private DevelopersBottomSheet developersBottomSheet;

    // Animation
    private Animation fadeInAnimation;
    private BCLoadingDialog bcLoadingDialog;

    // Developer Object
    private Developer developer;
    private DataViewModel dataViewModel;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setData();
        initListener();
        goToTeamOne();
    }

    // initialize component
    private void init() {
        // Layout Home Components
        dataViewModel = new DataViewModel(getApplicationContext());
        developer = dataViewModel.getDeveloper();

        fonts = new Fonts(getApplicationContext());
        btnLogout = findViewById(R.id.btnLogout);
        btnMenu = findViewById(R.id.btnMenu);
        tvTitlePage = findViewById(R.id.tvTitlePage);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvUsername = findViewById(R.id.tvUsername);
        tvDevOne = findViewById(R.id.tvDevOne);
        tvDevTwo = findViewById(R.id.tvDevTwo);
        tvDevThree = findViewById(R.id.tvDevThree);
        tvDevFour = findViewById(R.id.tvDevFour);
        tvDevFive = findViewById(R.id.tvDevFive);
        imageIcon1 = findViewById(R.id.imageIcon1);
        imageIcon2 = findViewById(R.id.imageIcon2);
        imageIcon3 = findViewById(R.id.imageIcon3);
        imageIcon4 = findViewById(R.id.imageIcon4);

        frameLayoutContent = findViewById(R.id.frameLayoutContent);
        layoutBottomBar = findViewById(R.id.layoutBottomBar);
        relative1 = findViewById(R.id.relative1);
        relative2 = findViewById(R.id.relative2);
        relative3 = findViewById(R.id.relative3);
        relative4 = findViewById(R.id.relative4);
        relative5 = findViewById(R.id.relative5);

        // Layout Drawer Components
        drawerLayout = findViewById(R.id.drawerLayout);
        btnClose = findViewById(R.id.btnClose);
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
        tvDevOne.setTypeface(fonts.stRegular());
        tvDevTwo.setTypeface(fonts.stRegular());
        tvDevThree.setTypeface(fonts.stRegular());
        tvDevFour.setTypeface(fonts.stRegular());
        tvDevFive.setTypeface(fonts.stRegular());

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
        tvName.setText(developerName);
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
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTeamOne();
            }
        });
        relative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTeamTwo();
            }
        });
        relative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTeamThree();
            }
        });
        relative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTeamFour();
            }
        });
        relative5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                developersBottomSheet = new DevelopersBottomSheet(HomeActivity.this, developer);
                developersBottomSheet.show(getSupportFragmentManager(), developersBottomSheet.getTag());
            }
        });
    }

    private void logout() {
        ArrayList<String> buttons = new ArrayList<>();
        buttons.add("Ok");
        buttons.add("Cancel");
        showCustomDialog("Sign Out", "Are you sure want to sign out?", buttons);
    }

    private void goToTeamOne() {
        setUpButtonTeamOne();
        Bundle bundle = new Bundle();
        fragment = new TeamOneFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutContent, fragment, TAG_FRAGMENT_TEAM_ONE)
                .commit();
    }

    private void goToTeamTwo() {
        setUpButtonTeamTwo();
        Bundle bundle = new Bundle();
        fragment = new TeamTwoFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutContent, fragment, TAG_FRAGMENT_TEAM_TWO)
                .commit();
    }

    private void goToTeamThree() {
        setUpButtonTeamThree();
        Bundle bundle = new Bundle();
        fragment = new TeamThreeFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutContent, fragment, TAG_FRAGMENT_TEAM_THREE)
                .commit();
    }

    private void goToTeamFour() {
        setUpButtonTeamFour();
        Bundle bundle = new Bundle();
        fragment = new TeamFourFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutContent, fragment, TAG_FRAGMENT_TEAM_FOUR)
                .commit();
    }

    private void setUpButtonTeamOne() {
        tvDevOne.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_spotify_button));
        tvDevTwo.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevThree.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevFour.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevFive.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_blue_dark));
        imageIcon1.setImageResource(R.drawable.ic_spotify);
        imageIcon2.setImageResource(R.drawable.ic_twitter_off);
        imageIcon3.setImageResource(R.drawable.ic_instagram_off);
        imageIcon4.setImageResource(R.drawable.ic_facebook_off);
    }

    private void setUpButtonTeamTwo() {
        tvDevOne.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevTwo.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_twitter_button));
        tvDevThree.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevFour.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevFive.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_blue_dark));
        imageIcon1.setImageResource(R.drawable.ic_spotify_off);
        imageIcon2.setImageResource(R.drawable.ic_twitter);
        imageIcon3.setImageResource(R.drawable.ic_instagram_off);
        imageIcon4.setImageResource(R.drawable.ic_facebook_off);
    }

    private void setUpButtonTeamThree() {
        tvDevOne.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevTwo.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevThree.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_instagram_button));
        tvDevFour.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevFive.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_blue_dark));
        imageIcon1.setImageResource(R.drawable.ic_spotify_off);
        imageIcon2.setImageResource(R.drawable.ic_twitter_off);
        imageIcon3.setImageResource(R.drawable.ic_instagram);
        imageIcon4.setImageResource(R.drawable.ic_facebook_off);
    }

    private void setUpButtonTeamFour() {
        tvDevOne.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevTwo.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevThree.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_grey));
        tvDevFour.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_facebook_button));
        tvDevFive.setTextColor(getApplicationContext().getResources().getColor(R.color.bacode_blue_dark));
        imageIcon1.setImageResource(R.drawable.ic_spotify_off);
        imageIcon2.setImageResource(R.drawable.ic_twitter_off);
        imageIcon3.setImageResource(R.drawable.ic_instagram_off);
        imageIcon4.setImageResource(R.drawable.ic_facebook);
    }

    private void showCustomDialog(String title, String message, ArrayList<String> action) {
        FragmentManager fm = getSupportFragmentManager();
        BCCustomDialog bcCustomDialog = BCCustomDialog.newInstance(HomeActivity.this, title, message, action);
        bcCustomDialog.show(fm, "fragment_custom_dialog");
    }

    private void showNativeDialog() {
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

    private void showProgressBar() {
        bcLoadingDialog = new BCLoadingDialog(this);
        bcLoadingDialog.show();
    }

    @Override
    public void negativeButtonPressed() {}

    @Override
    public void positiveButtonPressed() {
        showProgressBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bcLoadingDialog.dismiss();
                BCPreference.logout(HomeActivity.this);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
