package com.nowmagnate.seeker_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nowmagnate.seeker_ui.dialogs.DatePickerFragment;
import com.nowmagnate.seeker_ui.util.GradientStatusBar;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditProfileInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //toolbar Views
    private TextView toolbarTitle;
    private ImageView toolbarBack;

    private Spinner edu_spinner;
    private Spinner prof_spinner;

    private EditText profileName;
    private EditText aboutMe;
    private EditText heightFeet;
    private EditText heightInch;
    private EditText phone;
    private EditText location;

    private ImageView dateOfBirth;
    private TextView dateOfBirthText,maleText,femaleText,genderText,heightText;

    private CardView update;

    //Var
    private Calendar calendar;
    int year,day,month;

    Map name,dob,
            gender,about,
            height_feet,height_inch,
            phone_base,location_base,
            current_profession,highest_edu;

    String gen;
    boolean isAllFieldsClear = true;
    boolean isAllFieldsUpdated = false;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("seeker-378eb");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_info);


        toolbarBack = findViewById(R.id.back);
        toolbarTitle = findViewById(R.id.title);
        edu_spinner = findViewById(R.id.spinner_edu);
        prof_spinner = findViewById(R.id.spinner_prof);
        profileName = findViewById(R.id.profile_name_editText);
        dateOfBirth = findViewById(R.id.btn_select_date);
        dateOfBirthText = findViewById(R.id.selected_date);
        aboutMe = findViewById(R.id.about_editText);
        heightFeet = findViewById(R.id.height_feet_editText);
        heightInch = findViewById(R.id.height_inches_editText);
        phone = findViewById(R.id.phone_editText);
        location = findViewById(R.id.location_editText);
        update = findViewById(R.id.update);
        maleText = findViewById(R.id.male_text);
        femaleText = findViewById(R.id.female_text);
        genderText = findViewById(R.id.gender_text);
        heightText = findViewById(R.id.height_text);

        GradientStatusBar.setStatusBarGradiant(this);


        toolbarTitle.setText("EDIT PROFILE");
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initSpinner();

        initUI();


        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dateDialog();
            }
        });

        dateOfBirthText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add map fragment code here
                Toast.makeText(EditProfileInfo.this,"Add map fragment code here", Toast.LENGTH_LONG).show();
            }
        });

        maleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maleText.setBackground(getResources().getDrawable(R.drawable.edittext_pink_left_round_background));
                femaleText.setBackground(getResources().getDrawable(R.drawable.edittext_grey_right_round_background));
                gen = "male";
            }
        });

        femaleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                femaleText.setBackground(getResources().getDrawable(R.drawable.edittext_pink_right_round_background));
                maleText.setBackground(getResources().getDrawable(R.drawable.edittext_grey_left_round_background));
                gen = "female";
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(isAllFieldsUpdated){
            startActivity(new Intent(EditProfileInfo.this,MainActivity.class));
        }
        //super.onBackPressed();
    }

    public void initSpinner(){
        ArrayAdapter<String> adapterProf = new ArrayAdapter<String>(this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.prof));
        ArrayAdapter<String> adapterEdu = new ArrayAdapter<String>(this,
                R.layout.spinner_item, getResources()
                .getStringArray(R.array.edu));

        adapterProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEdu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        prof_spinner.setAdapter(adapterProf);
        prof_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        edu_spinner.setAdapter(adapterEdu);
        edu_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ye, int mont, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ye);
        c.set(Calendar.MONTH, mont);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        day = dayOfMonth;
        month = mont+1;
        year = ye;

        String d;
        String m;
        String y;

        if(day <10){
            d = "0"+day;
        }
        else{
            d=String.valueOf(day);
        }


        if(month <10){
            m = "0"+(month);
        }
        else{
            m = String.valueOf(month);
        }

        if(Integer.parseInt(calculateAge(year,month,year)) < 13){
            dateOfBirthText.setTextColor(Color.RED);
            dateOfBirthText.setText("You need to be atleast 13");
        }
        else {
            dateOfBirthText.setTextColor(getResources().getColor(R.color.TextColor));
            dateOfBirthText.setText(d + "/" + m + "/" + String.valueOf(year));
        }
    }

    public void hideKeyboard() {
        try {
            // use application level context to avoid unnecessary leaks.
            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputManager != null;
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dateDialog(){
        hideKeyboard();

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DialogFragment datePicker = new DatePickerFragment(year, month, day);
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    public void update(){
        name = new HashMap();
        dob = new HashMap();
        gender = new HashMap();
        about= new HashMap();
        height_feet = new HashMap();
        height_inch= new HashMap();
        phone_base = new HashMap();
        location_base= new HashMap();
        current_profession = new HashMap();
        highest_edu = new HashMap();

        if(dateOfBirthText.getText().toString().isEmpty()){
            dateOfBirthText.setHintTextColor(Color.RED);
            isAllFieldsClear = false;
            popToast(null);
        }
        else {
            isAllFieldsClear = true;
        }
        if(gen==null||gen.isEmpty()){
            genderText.setTextColor(Color.RED);
            isAllFieldsClear = false;
            popToast(null);
        }
        else {
            isAllFieldsClear = true;
        }
        if(phone.getText().toString().isEmpty()){
            phone.setHintTextColor(Color.RED);
            isAllFieldsClear = false;
            popToast(null);
        }
        else {
            isAllFieldsClear = true;
        }
        if(heightFeet.getText().toString().isEmpty()||heightInch.getText().toString().isEmpty()){
            heightText.setTextColor(Color.RED);
            if(heightFeet.getText().toString().isEmpty()){
                heightFeet.setHintTextColor(Color.RED);
            }
            if(heightInch.getText().toString().isEmpty()){
                heightInch.setHintTextColor(Color.RED);
            }
            isAllFieldsClear = false;
            popToast(null);
        }
        else {
            isAllFieldsClear = true;
        }

        if(isAllFieldsClear){


            if(profileName.getText().toString().isEmpty()){
            name.put("name",user.getDisplayName());}
            else{
                name.put("name",profileName.getText().toString());
            }
            gender.put("gender",gen);
            about.put("about",aboutMe.getText().toString());
            height_feet.put("height_feet",heightFeet.getText().toString());
            height_inch.put("height_inch",heightInch.getText().toString());
            phone_base.put("phone",phone.getText().toString());
            current_profession.put("current_profession",prof_spinner.getSelectedItem().toString());
            highest_edu.put("highest_edu",edu_spinner.getSelectedItem().toString());
            dob.put("dob",dateOfBirthText.getText());

            ref.updateChildren(name);
            ref.updateChildren(dob);
            ref.updateChildren(gender);
            ref.updateChildren(about);
            ref.updateChildren(height_feet);
            ref.updateChildren(height_inch);
            ref.updateChildren(phone_base);
            ref.updateChildren(current_profession);
            ref.updateChildren(highest_edu);
            isAllFieldsUpdated = true;
            startActivity(new Intent(EditProfileInfo.this,MainActivity.class));
            SharedPreferences.Editor editor = getSharedPreferences("UPDATED", MODE_PRIVATE).edit();
            editor.putBoolean("isUPDATED",true);
            editor.apply();
        }

    }

    public void popToast(String s){
        if(s == null) {
            Toast.makeText(EditProfileInfo.this, "Please enter all required fields", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(EditProfileInfo.this, s, Toast.LENGTH_SHORT).show();
        }
    }



    public void initUI(){
        ref = ref.child(user.getUid());
        ref.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    profileName.setText(dataSnapshot.getValue().toString());
                    isAllFieldsUpdated = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("dob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                dateOfBirthText.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("gender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    gen = dataSnapshot.getValue().toString();
                if(gen.equals("male")){
                    maleText.setBackground(getResources().getDrawable(R.drawable.edittext_pink_left_round_background));
                    femaleText.setBackground(getResources().getDrawable(R.drawable.edittext_grey_right_round_background));
                }else{
                    maleText.setBackground(getResources().getDrawable(R.drawable.edittext_grey_left_round_background));
                    femaleText.setBackground(getResources().getDrawable(R.drawable.edittext_pink_right_round_background));
                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("current_profession").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    //spinner display
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("highest_edu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                //spinner display
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("height_feet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                heightFeet.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("height_inch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                heightInch.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                phone.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child("about").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                aboutMe.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String calculateAge(int year, int month, int day){
        Calendar d_o_b = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        d_o_b.set(year, month, day);

        int age = today.get(Calendar.YEAR) - d_o_b.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < d_o_b.get(Calendar.DAY_OF_YEAR)){
            age--;
        }


        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}
