package com.nowmagnate.seeker_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nowmagnate.seeker_ui.util.GradientStatusBar;

public class LoginRegister extends AppCompatActivity {

    private ConstraintLayout splashScreen;
    private ImageView animImage;
    private TextView animStreet, animDating;

    private CardView googleCard, facebookCard, phoneCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        splashScreen = findViewById(R.id.splashScreen);
        googleCard = findViewById(R.id.google_card);
        facebookCard = findViewById(R.id.facebook_card);
        phoneCard = findViewById(R.id.phone_card);
        animImage = findViewById(R.id.anim_image);
        animStreet = findViewById(R.id.anim_street);
        animDating = findViewById(R.id.anim_dating);

        GradientStatusBar.setStatusBarGradiant(this);

        splashScreen.setClickable(true);



        SplashScreenAnim();

        googleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableClick();
                onLoginCardClick();
            }
        });

        facebookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableClick();
                onLoginCardClick();
            }
        });

        phoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableClick();
                onLoginCardClick();
            }
        });

    }

    public void onLoginCardClick(){
        startActivity(new Intent(LoginRegister.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleCard.setClickable(true);
        facebookCard.setClickable(true);
        phoneCard.setClickable(true);
    }

    public void SplashScreenAnim(){
        Handler handler = new Handler();

        animStreet.animate().translationX(0).setDuration(800);
        animDating.animate().translationY(0).setDuration(800);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                splashScreen.animate().translationX(splashScreen.getRight()).alpha(0);
                splashScreen.setClickable(false);
            }
        }, 2000);
    }

    public void disableClick(){
        phoneCard.setClickable(false);
        facebookCard.setClickable(false);
        googleCard.setClickable(false);
    }
}
