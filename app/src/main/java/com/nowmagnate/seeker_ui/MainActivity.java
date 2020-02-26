package com.nowmagnate.seeker_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nowmagnate.seeker_ui.fragments.AccountsFragment;
import com.nowmagnate.seeker_ui.fragments.CardsFragment;
import com.nowmagnate.seeker_ui.fragments.CoinsFragment;
import com.nowmagnate.seeker_ui.fragments.CrushFabFragment;
import com.nowmagnate.seeker_ui.fragments.MessagesFragment;

public class MainActivity extends AppCompatActivity {

    //Bottom App Bar Icons UI
    private ImageView cards,coins,messages,account;
    private FloatingActionButton crushFab;

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cards = findViewById(R.id.cards);
        coins = findViewById(R.id.coins);
        messages = findViewById(R.id.messages);
        account = findViewById(R.id.account);
        crushFab = findViewById(R.id.crushFab);


        cards.setColorFilter(Color.parseColor("#AB0092FF"));

        setStatusBarGradiant(this);
        intBottomNavBar();
    }

    public void intBottomNavBar(){

        replaceFragment(new CardsFragment());
        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CardsFragment());
                navIconSelected(cards);

            }
        });


        coins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CoinsFragment());
                navIconSelected(coins);
            }
        });


        crushFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CrushFabFragment());
                navIconSelected(crushFab);
            }
        });


        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MessagesFragment());
                navIconSelected(messages);
            }
        });


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AccountsFragment());
                navIconSelected(account);
            }
        });
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                fragment).commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_red_blue);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public void navIconSelected(ImageView c){

        cards.setColorFilter(null);
        coins.setColorFilter(null);
        messages.setColorFilter(null);
        account.setColorFilter(null);
        crushFab.setColorFilter(null);


        c.setColorFilter(Color.parseColor("#AB0092FF"));

    }

}
