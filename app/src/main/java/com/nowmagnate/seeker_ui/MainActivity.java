package com.nowmagnate.seeker_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nowmagnate.seeker_ui.fragments.AccountsFragment;
import com.nowmagnate.seeker_ui.fragments.CardsFragment;
import com.nowmagnate.seeker_ui.fragments.CoinsFragment;
import com.nowmagnate.seeker_ui.fragments.CrushFabFragment;
import com.nowmagnate.seeker_ui.fragments.MessagesFragment;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //Bottom App Bar Icons UI
    private ImageView cards,coins,messages,account;
    private FloatingActionButton crushFab,likeFAB,dislikeFab;

    SharedPreferences preferences;
    Fragment selectedFragment = null;

    boolean trap = true;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("seeker-378eb");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cards = findViewById(R.id.cards);
        coins = findViewById(R.id.coins);
        messages = findViewById(R.id.messages);
        account = findViewById(R.id.account);
        crushFab = findViewById(R.id.crushFab);

        preferences = getSharedPreferences("UPDATED",MODE_PRIVATE);
        if(preferences.getBoolean("isUPDATED", false)==false){
            startActivity(new Intent(MainActivity.this,EditProfileInfo.class));
        }
        Log.i("pop", String.valueOf(preferences.getBoolean("isUPDATED",false)));


        cards.setColorFilter(Color.parseColor("#AB0092FF"));
        addDateStamp();

        getCurrentTime();

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

    public void fin(){
        finish();
    }

    public void signOut(){
        SharedPreferences.Editor editor = getSharedPreferences("UPDATED", MODE_PRIVATE).edit();
        editor.putBoolean("isUPDATED",false);
        editor.apply();
    }

    public void addDateStamp(){
        ref.child(user.getUid()).child("init_date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null) {
                    Calendar c = Calendar.getInstance();
                    Log.i("pop", c.getTime().toString().substring(0, 10));
                    Map d = new HashMap();
                    d.put("init_date", c.getTime().toString().substring(0, 10));

                    ref = ref.child(user.getUid());
                    ref.updateChildren(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public String getDateStamp(){
        Calendar c = Calendar.getInstance();

        return c.getTime().toString().substring(0,10);
    }

    public long getCurrentTime(){
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
