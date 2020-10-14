package com.example.bacodelabs.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.bacodelabs.R;
import com.example.bacodelabs.util.Fonts;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamOneFragment extends Fragment {

    private Context context;
    private View rootView;
    private TextView tvFeeds;
    private TextView tvFeedsTitle;
    private TextView tvFeedsDesc;
    private TextView tvTeammate;
    private TextView tvTask;
    private TextView tvTaskMessage;
    private CardView cardViewFeed;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeamOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamOneFragment newInstance(String param1, String param2) {
        TeamOneFragment fragment = new TeamOneFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.layout_home_content, container, false);
        init(rootView);

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init(View v) {
        tvFeeds = v.findViewById(R.id.tvFeed);
        tvFeedsTitle = v.findViewById(R.id.tvFeedTitle);
        tvFeedsDesc = v.findViewById(R.id.tvFeedDesc);
        tvTeammate = v.findViewById(R.id.tvTeammate);
        tvTask = v.findViewById(R.id.tvTask);
        tvTaskMessage = v.findViewById(R.id.tvTaskMessage);
        cardViewFeed = v.findViewById(R.id.cardViewFeed);
        setCosmetics();
    }

    private void setCosmetics() {
        // Set Fonts Style
        Fonts fonts = new Fonts(context);
        tvFeeds.setTypeface(fonts.stRegular());
        tvFeedsTitle.setTypeface(fonts.stRegular());
        tvFeedsDesc.setTypeface(fonts.stRegular());
        tvTeammate.setTypeface(fonts.stRegular());
        tvTask.setTypeface(fonts.stRegular());
        tvTaskMessage.setTypeface(fonts.stRegular());

        // Set Background Color
        //cardViewFeed.setCardBackgroundColor(context.getResources().getColor(R.color.bacode_blue_light));

    }
}
