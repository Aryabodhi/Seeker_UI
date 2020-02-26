package com.nowmagnate.seeker_ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.nowmagnate.seeker_ui.AddEditProfileImages;
import com.nowmagnate.seeker_ui.EditProfileInfo;
import com.nowmagnate.seeker_ui.R;
import com.nowmagnate.seeker_ui.Settings;
import com.nowmagnate.seeker_ui.Verification;

import java.util.Random;

public class AccountsFragment extends Fragment {

    //Profile Card UI
    private ImageView profileImage, verificationImage,
                        editProfileImage , editProfileName,
                        blurImg;
    private TextView profileName;
    private TextView datingText;
    private TextView streetText;

    //Cards
    private  CardView preferenceCard , settingsCard,
                        whoLikesYouCard , logOutCard,
                            referEarnCard, verifyProfileImageCard,
                                vipMemberCard;

    private Context c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        profileImage = view.findViewById(R.id.accountImage);
        profileName = view.findViewById(R.id.accountName);
        verificationImage = view.findViewById(R.id.verifiedImage);
        editProfileImage = view.findViewById(R.id.edit_profile_image);
        editProfileName = view.findViewById(R.id.edit_profile_name);
        blurImg = view.findViewById(R.id.blurImg);
        datingText = view.findViewById(R.id.dating_text);
        streetText = view.findViewById(R.id.street_text);

        preferenceCard = view.findViewById(R.id.preference_card);
        settingsCard = view.findViewById(R.id.settings_card);
        whoLikesYouCard = view.findViewById(R.id.who_likes_you_card);
        logOutCard = view.findViewById(R.id.log_out_card);
        referEarnCard = view.findViewById(R.id.refer_earn_card);
        verifyProfileImageCard = view.findViewById(R.id.verify_dp_card);
        vipMemberCard = view.findViewById(R.id.vip_member_card);

        datingText.setVisibility(View.GONE);
        streetText.setText("ACCOUNT");

        settingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsCard.setClickable(false);
                startActivity(new Intent(getContext(), Settings.class));
            }
        });

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileImage.setClickable(false);
                startActivity(new Intent(getContext() , AddEditProfileImages.class));
            }
        });

        editProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileName.setClickable(false);
                startActivity(new Intent(getContext(), EditProfileInfo.class));
            }
        });

        verifyProfileImageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyProfileImageCard.setClickable(false);
                startActivity(new Intent(getContext(), Verification.class));
            }
        });




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        settingsCard.setClickable(true);
        preferenceCard.setClickable(true);
        whoLikesYouCard.setClickable(true);
        logOutCard.setClickable(true);
        referEarnCard.setClickable(true);
        verifyProfileImageCard.setClickable(true);
        editProfileName.setClickable(true);
        editProfileImage.setClickable(true);

    }
}
