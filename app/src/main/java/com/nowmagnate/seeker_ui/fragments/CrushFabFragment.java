package com.nowmagnate.seeker_ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nowmagnate.seeker_ui.MainActivity;
import com.nowmagnate.seeker_ui.ProfileDetail;
import com.nowmagnate.seeker_ui.R;
import com.nowmagnate.seeker_ui.adapters.CardImageViewPagerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class CrushFabFragment extends Fragment {

    private TextView datingText;
    private TextView streetText;
    private TextView timer;
    private LinearLayout payLayout;
    private FloatingActionButton likeFAB,dislikeFAB;

    private CardView profileCard;
    private int min , sec;
    private long currentTime,counterStartTime;

    boolean CounterEnable = true;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference ref = database.getReference("seeker-378eb").child(user.getUid());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crush_fab, container, false);

        datingText = view.findViewById(R.id.dating_text);
        streetText = view.findViewById(R.id.street_text);
        timer = view.findViewById(R.id.timer);
        payLayout = view.findViewById(R.id.pay_layout);
        likeFAB = view.findViewById(R.id.likeFAB);
        dislikeFAB = view.findViewById(R.id.dislikeFaB);

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

        likeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterEnable = false;
            }
        });

        dislikeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterEnable = false;
            }
        });

        checkDate();


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        profileCard.setClickable(true);
    }

    public void Counter(){
        if(CounterEnable) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (CounterEnable) {
                        sec = sec - 1;
                        if (sec == 0) {
                            min = min - 1;
                            if (min == 0) {
                                timerUp();
                                CounterEnable = false;
                                setCounterAvailable(false);
                            } else {
                                sec = 60;
                                timer.setText(String.valueOf(min) + ":" + String.valueOf(sec) + " min left");
                            }
                        }
                        Counter();
                    }
                }
            }, 1000);
        }
    }

    public void checkCounterAvailable(){
        ref.child("isTimerUp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    addTimeToDB();
                    CounterEnable = true;
                    setCounterAvailable(true);
                    min = 30;
                    sec = 0;
                    timer.setText("30:00 min left");
                    Counter();
                }
                else if(Boolean.parseBoolean(dataSnapshot.getValue().toString())){
                    CounterEnable = true;
                    getCounterStartTime();
                    Long timeDelta = currentTime - counterStartTime;
                    if(TimeUnit.MILLISECONDS.toMinutes(timeDelta) > 30){
                        min = 30 - (int) TimeUnit.MILLISECONDS.toMinutes(timeDelta);
                        sec = (int) TimeUnit.MILLISECONDS.toSeconds(timeDelta);
                        Counter();
                    }
                    else{
                        setCounterAvailable(false);
                        timerUp();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addTimeToDB(){
        ref.child("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    Map time = new HashMap();
                    currentTime = ((MainActivity)getContext()).getCurrentTime();
                    time.put("time",currentTime);
                    ref.updateChildren(time);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getCounterStartTime(){
        ref.child("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counterStartTime = Long.parseLong(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setCounterAvailable(boolean available){
        Map a = new HashMap();
        a.put("CounterAvailable", available);
        ref.updateChildren(a);
        CounterEnable = available;
    }

    public void timerUp(){
        payLayout.setVisibility(View.VISIBLE);
        timer.setText("Timer Up.");
    }

    public void checkDate(){
        ref.child("init_date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!((MainActivity)getContext()).getDateStamp().equals(dataSnapshot.getValue())){
                    Map time = new HashMap();
                    currentTime = ((MainActivity)getContext()).getCurrentTime();
                    time.put("time",currentTime);
                    ref.updateChildren(time);
                    setCounterAvailable(true);
                    CounterEnable = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        checkCounterAvailable();
    }

}
