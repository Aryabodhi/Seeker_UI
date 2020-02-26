package com.nowmagnate.seeker_ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nowmagnate.seeker_ui.ProfileDetail;
import com.nowmagnate.seeker_ui.R;
import com.nowmagnate.seeker_ui.adapters.CardImageViewPagerAdapter;

public class CrushFabFragment extends Fragment {

    private TextView datingText;
    private TextView streetText;

    private CardView profileCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crush_fab, container, false);

        datingText = view.findViewById(R.id.dating_text);
        streetText = view.findViewById(R.id.street_text);

        profileCard = view.findViewById(R.id.profileCard);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileCard.setClickable(false);
                startActivity(new Intent(getContext(), ProfileDetail.class));
            }
        });

        datingText.setVisibility(View.GONE);
        streetText.setText("CRUSH ZONE");

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        profileCard.setClickable(true);
    }
}
