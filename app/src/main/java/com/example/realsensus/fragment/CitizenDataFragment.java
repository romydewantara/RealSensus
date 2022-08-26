package com.example.realsensus.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.realsensus.MainActivity;
import com.example.realsensus.R;
import com.example.realsensus.adapter.CitizensDataAdapter;
import com.example.realsensus.constant.Constant;
import com.example.realsensus.helper.FragmentPermissionHelper;
import com.example.realsensus.helper.RSPreference;
import com.example.realsensus.library.CitizenFormBottomSheetDialog;
import com.example.realsensus.library.CitizenFormDialog;
import com.example.realsensus.library.PopUpDialog;
import com.example.realsensus.listener.CitizenFormBottomSheetDialogListener;
import com.example.realsensus.listener.CitizenFormDialogListener;
import com.example.realsensus.listener.FragmentListener;
import com.example.realsensus.listener.PopUpDialogListener;
import com.example.realsensus.model.Citizen;
import com.example.realsensus.model.CitizenDataMaster;
import com.example.realsensus.util.AppUtil;
import com.google.gson.Gson;

/**
 * Created by Muhammad Fakhri Pratama
 * github: ari.japindo@gmail.com | akufakhri61
 */
public class CitizenDataFragment extends Fragment implements CitizensDataAdapter.ClickListener, CitizenFormBottomSheetDialogListener {

    private Context context;
    private CitizenDataMaster citizenDataMaster;
    private FragmentListener fragmentListener;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    //widget
    private RecyclerView recyclerViewCitizensData;
    private CardView cardViewAdd;
    private LottieAnimationView loading;

    private Animation animFadeIn, animFadeOut;

    private final long DELAY_MILLIS = 1500L;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private String previousFragment = "";

    public void addPrevFragmentTag(String previousFragment) {
        this.previousFragment = previousFragment;
    }

    public CitizenDataFragment() {
        // Required empty public constructor
    }

    public static CitizenDataFragment newInstance(String param1, String param2) {
        CitizenDataFragment fragment = new CitizenDataFragment();
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

        if (!previousFragment.equals("")) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(previousFragment)).commit();
        }

        animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_citizen_data, container, false);
        recyclerViewCitizensData = v.findViewById(R.id.recyclerViewCitizensData);
        cardViewAdd = v.findViewById(R.id.cardViewAdd);
        loading = v.findViewById(R.id.loading);
        v.findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
        v.findViewById(R.id.imageAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
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
        requestPermissionLauncher = new FragmentPermissionHelper().startPermissionHelper(this, isGranted -> {
            if (isGranted) {
                //fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                showCitizenFormDialog("");
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.no_camera_permission), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestPermissions() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }

    private void fetchCitizensData() { //memuat data warga
        citizenDataMaster = RSPreference.getInstance(context).loadCitizensDataMaster();
        CitizensDataAdapter citizensDataAdapter = new CitizensDataAdapter(context, citizenDataMaster, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewCitizensData.setLayoutManager(linearLayoutManager);
        recyclerViewCitizensData.setAdapter(citizensDataAdapter);
    }

    private void showCitizenFormDialog(String params) {
        FragmentManager fm = getFragmentManager();
        CitizenFormDialog citizenFormDialog = CitizenFormDialog.newInstance(context, params)
                .getCitizenFormDialogListener(new CitizenFormDialogListener() {
                    @Override
                    public void onButtonSaveClicked() {
                        Citizen citizen = (Citizen) RSPreference.getInstance(context).getCitizen();
                        new AppUtil(context).addCitizenDataMaster(citizen);
                        fetchCitizensData();
                        RSPreference.getInstance(context).saveTempCitizenScanResult(null); //reset temp citizen result
                    }

                    @Override
                    public void onButtonCancelClicked() {
                        RSPreference.getInstance(context).saveTempCitizenScanResult(null); //reset temp citizen result
                    }

                    @Override
                    public void onScanNikClicked() {
                        Constant.scanType = Constant.scanNumber;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanNameClicked() {
                        Constant.scanType = Constant.scanName;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanPobClicked() {
                        Constant.scanType = Constant.scanPob;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanDobClicked() {
                        Constant.scanType = Constant.scanDob;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanAddressClicked() {
                        Constant.scanType = Constant.scanAddress;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanReligionClicked() {
                        Constant.scanType = Constant.scanReligion;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanMarriageStateClicked() {
                        Constant.scanType = Constant.scanMarriageState;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanTypeOfWorkClicked() {
                        Constant.scanType = Constant.scanTypeOfWork;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanCitizenshipClicked() {
                        Constant.scanType = Constant.scanCitizenship;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }

                    @Override
                    public void onScanValidUntilClicked() {
                        Constant.scanType = Constant.scanValidUntil;
                        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_SCANNER, true);
                    }
                });
        if (fm != null) {
            citizenFormDialog.show(fm, "citizen_form_dialog");
        }
    }

    private void showLoading() {
        cardViewAdd.setVisibility(View.GONE);
        loading.setRepeatCount(LottieDrawable.INFINITE);
        loading.playAnimation();
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation(animFadeIn);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            loading.pauseAnimation();
            loading.setVisibility(View.GONE);
            loading.startAnimation(animFadeOut);
            cardViewAdd.setVisibility(View.VISIBLE);

            fetchCitizensData();
            if (previousFragment != null && previousFragment.equalsIgnoreCase(MainActivity.TAG_FRAGMENT_SCANNER)) {
                showCitizenFormDialog(RSPreference.getInstance(context).takeOCRTextResult());
            }
        }, DELAY_MILLIS);
    }

    @Override
    public void onCitizenDetails(Citizen citizen) {
        Log.d("CitizenDataFragment", "onButtonEditClicked - citizen: " + new Gson().toJson(citizen));
        CitizenFormBottomSheetDialog citizenFormBottomSheetDialog = new CitizenFormBottomSheetDialog(context,
                CitizenDataFragment.this, citizen);
        citizenFormBottomSheetDialog.setCancelable(false);
        citizenFormBottomSheetDialog.show(getChildFragmentManager(), getTag());
    }

    @Override
    public void onButtonEditClicked(Citizen citizen) {
        //goToCitizenFormFragment
        fragmentListener.onFragmentPassingCitizen(citizen);
        fragmentListener.onFragmentFinish(CitizenDataFragment.this, MainActivity.FRAGMENT_FINISH_GOTO_CITIZEN_FORM, true);
    }

    @Override
    public void onButtonDeleteClicked(Citizen citizen) {
        String message = "Data KTP yang dihapus tidak bisa dimuat ulang, apakah Anda yakin ingin menghapus data "
                + citizen.getName() + "?";
        FragmentManager fm = getFragmentManager();
        PopUpDialog citizenFormDialog = PopUpDialog.newInstance(context, "Peringatan", message)
                .setButton("", "", new PopUpDialogListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        new AppUtil(context).deleteCitizen(citizen);
                        fetchCitizensData();
                    }
                    @Override
                    public void onNegativeButtonClicked() {}
                });
        if (fm != null) {
            citizenFormDialog.show(fm, "citizen_form_dialog");
        }
    }

    @Override
    public void onButtonSaveClicked() {
        showLoading();
    }

    @Override
    public void onButtonCancelClicked() {
        //Toast.makeText(context, R.string.info_data_canceled, Toast.LENGTH_SHORT).show();
    }
}