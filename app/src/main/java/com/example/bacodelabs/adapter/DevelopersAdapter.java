package com.example.bacodelabs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.bacodelabs.R;
import com.example.bacodelabs.model.Developers;
import com.example.bacodelabs.util.Fonts;

import java.util.List;

public class DevelopersAdapter extends BaseAdapter {

    private Context context;
    private List<Developers> developers;
    private Fonts fonts;

    public DevelopersAdapter(Context context, List<Developers> developers) {
        this.context = context;
        this.developers = developers;
        fonts = new Fonts(context);
    }

    @Override
    public int getCount() {
        return developers.size();
    }

    @Override
    public Object getItem(int i) {
        return developers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.layout_items_developer, null);
        ConstraintLayout layoutItem = v.findViewById(R.id.layoutItems);
        ImageView icon = v.findViewById(R.id.imageIcon);
        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvBirth = v.findViewById(R.id.tvBirth);
        TextView tvRole = v.findViewById(R.id.tvRole);

        tvName.setText(developers.get(i).getName());
        tvName.setTypeface(fonts.stMedium());
        tvBirth.setText(developers.get(i).getBirth());
        tvBirth.setTypeface(fonts.stMedium());
        tvRole.setText(developers.get(i).getRole());
        tvRole.setTypeface(fonts.stThin());

        layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // goToDevelopersPage
            }
        });

        return v;
    }
}