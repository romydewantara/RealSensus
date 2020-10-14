package com.example.bacodelabs.libs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.bacodelabs.R;

public class BCLoadingDialog extends Dialog {

    private LottieAnimationView lottieLoading;

    public BCLoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        lottieLoading = findViewById(R.id.lottieLoading);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setWindowAnimations(android.R.style.Animation_Toast);
        playLottieView();
        setCancelable(false);
    }

    public void playLottieView() {
        lottieLoading.setScaleType(ImageView.ScaleType.FIT_CENTER);
        lottieLoading.setRepeatCount(LottieDrawable.INFINITE);
    }
}
