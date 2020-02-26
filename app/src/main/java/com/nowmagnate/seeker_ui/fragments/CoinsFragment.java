package com.nowmagnate.seeker_ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nowmagnate.seeker_ui.R;

public class CoinsFragment extends Fragment {

    private TextView datingText;
    private TextView streetText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coins, container, false);

        datingText = view.findViewById(R.id.dating_text);
        streetText = view.findViewById(R.id.street_text);

        datingText.setVisibility(View.GONE);
        streetText.setText("COINS");

        return view;
    }
}
