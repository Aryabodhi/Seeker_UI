package com.nowmagnate.seeker_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nowmagnate.seeker_ui.dialogs.DatePickerFragment;
import com.nowmagnate.seeker_ui.util.GradientStatusBar;

import java.security.PrivateKey;
import java.text.DateFormat;
import java.util.Calendar;

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
    private TextView dateOfBirthText;

    private CardView update;

    //Var
    private Calendar calendar;
    int year,day,month;

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

        GradientStatusBar.setStatusBarGradiant(this);


        toolbarTitle.setText("EDIT PROFILE");
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initSpinner();

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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
        month = mont;
        year = ye;

        dateOfBirthText.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
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
        int year;
        int month;
        int day;

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DialogFragment datePicker = new DatePickerFragment(year, month, day);
        datePicker.show(getSupportFragmentManager(), "date picker");
    }
}
