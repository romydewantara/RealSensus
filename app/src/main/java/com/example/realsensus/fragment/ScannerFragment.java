package com.example.realsensus.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.realsensus.MainActivity;
import com.example.realsensus.R;
import com.example.realsensus.camera.CameraSource;
import com.example.realsensus.camera.CameraSourcePreview;
import com.example.realsensus.camera.GraphicOverlay;
import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.listener.FragmentListener;
import com.example.realsensus.util.OcrDetector;
import com.example.realsensus.util.OcrGraphic;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class ScannerFragment extends Fragment {

    private Context context;
    private FragmentListener fragmentListener;

    private static final String TAG = "CaptureFragment";
    private static final int RC_HANDLE_GMS = 9001;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String TextBlockObject = "String";
    private CameraSource mCameraSource;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private Animation animBlinkInfinite, animScaleHide, animScaleShow, animFadeOut;

    //widget
    private CameraSourcePreview mPreview;
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private LottieAnimationView loading;
    private ConstraintLayout scannerLayout;
    private ConstraintLayout buttonContainer;
    private ConstraintLayout constraintInfoResult;
    private AppCompatTextView textViewInfoScan;
    private AppCompatTextView textViewResult;
    private ImageView imageFlash;
    private ImageView imageStripe;
    private ImageView imageBack;
    private Button buttonRescan;
    private Button buttonSend;
    private Button buttonAddData;

    //flag
    private boolean isFlashOn = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String previousFragment = "";

    public void addPrevFragmentTag(String previousFragment) {
        this.previousFragment = previousFragment;
    }

    public ScannerFragment() {
        // Required empty public constructor
    }

    public static ScannerFragment newInstance(String param1, String param2) {
        ScannerFragment fragment = new ScannerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (!previousFragment.equals("")){
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(previousFragment)).commit();
        }

        animBlinkInfinite = AnimationUtils.loadAnimation(context, R.anim.blink_infinite);
        animScaleHide = AnimationUtils.loadAnimation(context, R.anim.scale_hide);
        animScaleShow = AnimationUtils.loadAnimation(context, R.anim.scale_show);
        animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scanner, container, false);
        mPreview = v.findViewById(R.id.preview);
        mGraphicOverlay = v.findViewById(R.id.graphicOverlay);
        loading = v.findViewById(R.id.loading);
        scannerLayout = v.findViewById(R.id.scannerLayout);
        buttonContainer = v.findViewById(R.id.buttonContainer);
        constraintInfoResult = v.findViewById(R.id.constraintInfoResult);
        textViewInfoScan = v.findViewById(R.id.textViewInfoScan);
        textViewResult = v.findViewById(R.id.textViewResult);
        imageFlash = v.findViewById(R.id.imageFlash);
        imageStripe = v.findViewById(R.id.imageStripe);
        imageBack = v.findViewById(R.id.imageBack);
        buttonRescan = v.findViewById(R.id.buttonRescan);
        buttonSend = v.findViewById(R.id.buttonSend);
        buttonAddData = v.findViewById(R.id.buttonAddData);
        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createCameraSource(true, false);

        gestureDetector = new GestureDetector(context, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        //Snackbar.make(mGraphicOverlay, "Tap to capture. Pinch/Stretch to zoom", Snackbar.LENGTH_INDEFINITE).show();
        textViewInfoScan.startAnimation(animBlinkInfinite);
        mGraphicOverlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                boolean b = scaleGestureDetector.onTouchEvent(e);
                boolean c = gestureDetector.onTouchEvent(e);
                return b || c;
            }
        });
        imageFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    isFlashOn = false;
                    imageStripe.setVisibility(View.INVISIBLE);
                } else {
                    isFlashOn = true;
                    imageStripe.setVisibility(View.VISIBLE);
                }
            }
        });
        buttonRescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reScan();
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RSPreference.getInstance(context).storeOCRTextResult(textViewResult.getText().toString());
                fragmentListener.onFragmentFinish(ScannerFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_CITIZEN, true);
            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentListener) {
            fragmentListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }

    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        textRecognizer.setProcessor(new OcrDetector(mGraphicOverlay));

        if (!textRecognizer.isOperational()) {

            Log.w(TAG, "Detector dependencies are not yet available.");
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = context.registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(context, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        mCameraSource = new CameraSource.Builder(context, textRecognizer)
                .setFacing(com.google.android.gms.vision.CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null)
                .build();
        startCameraSource();
    }

    private void startCameraSource() throws SecurityException {

        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private boolean onTap(float rawX, float rawY) {
        OcrGraphic graphic = mGraphicOverlay.getGraphicAtLocation(rawX, rawY);
        TextBlock text = null;
        Log.d(TAG, "onTap - graphic: " + graphic);
        if (graphic != null) {
            text = graphic.getTextBlock();
            Log.d(TAG, "onTap - text: " + text);
            if (text != null && text.getValue() != null) {
                Log.d(TAG, "onTap - textValue: " + text.getValue());
                Intent data = new Intent();
                data.putExtra(TextBlockObject, text.getValue());
                if (getActivity() != null) {
                    getActivity().setResult(CommonStatusCodes.SUCCESS, data);
                    showResult(text.getValue());
                }
            } else {
                Log.d(TAG, "text data is null");
            }
        } else {
            Log.d(TAG, "no text detected");
        }
        return text != null;
    }

    private void showResult(String textResult) {
        if (mPreview != null) {
            mPreview.stop();
        }
        scannerLayout.setVisibility(View.GONE);
        scannerLayout.startAnimation(animScaleHide);

        loading.setRepeatCount(LottieDrawable.INFINITE);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            loading.pauseAnimation();
            loading.setVisibility(View.GONE);
            loading.startAnimation(animFadeOut);

            buttonContainer.setVisibility(View.VISIBLE);
            constraintInfoResult.setVisibility(View.VISIBLE);
            textViewResult.setText(textResult);
        }, 1500);
    }

    private void reScan() {
        scannerLayout.setVisibility(View.VISIBLE);
        scannerLayout.startAnimation(animScaleShow);
        buttonContainer.setVisibility(View.GONE);
        constraintInfoResult.setVisibility(View.INVISIBLE);
        textViewResult.setText("");
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startCameraSource();
        }, 500);
    }

}