package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class signup_doctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG";
    Button alreadyHaveAccount;

    Button specialtyBtn, workdaysBtn, shiftsBtn, signUpBtnDoctor;
    String userId;

    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fstoreDoctor;
    ProgressBar progBarDoctor;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    TextView mItemSelected2;
    String[] listItems2;
    boolean[] checkedItems2;
    ArrayList<Integer> mUserItems2 = new ArrayList<>();

    TextView mItemSelected3;
    String[] listItems3;
    boolean[] checkedItems3;
    ArrayList<Integer> mUserItems3 = new ArrayList<>();
    CheckBox sat, sun, mon, tues, wed, thurs, fri;
    CheckBox satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    String[][] appointment = new String[7][3];


    private EditText inputName, inputEmail, inputPassword, confirmPassword, hospitalName, practiceYear, description;
    Spinner location_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);
        getSupportActionBar().setTitle("0!");

        //Spinner specialty_type = findViewById(R.id.specialty_type);
        //specialty_type.setOnItemSelectedListener( this);

        location_type = findViewById(R.id.location_type);
        location_type.setOnItemSelectedListener(this);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountDoctor);
        signUpBtnDoctor = findViewById(R.id.signup_button);

        fAuthDoctor = FirebaseAuth.getInstance();
        fstoreDoctor = FirebaseFirestore.getInstance();

        specialtyBtn = findViewById(R.id.btnSpecialty);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.specialty_list);
        checkedItems = new boolean[listItems.length];

        //workdaysBtn = findViewById(R.id.btnWorkDays);
        mItemSelected2 = (TextView) findViewById(R.id.tvItemSelected2);
        listItems2 = getResources().getStringArray(R.array.workday_list);
        checkedItems2 = new boolean[listItems2.length];

        //shiftsBtn = findViewById(R.id.btnShifts);

        mItemSelected3 = (TextView) findViewById(R.id.tvItemSelected3);
        listItems3 = getResources().getStringArray(R.array.shift_list);
        checkedItems3 = new boolean[listItems3.length];

        inputName = findViewById(R.id.inputNameDoctor);
        inputEmail = findViewById(R.id.inputEmailDoctor);
        inputPassword = findViewById(R.id.inputPasswordDoctor);
        confirmPassword = findViewById(R.id.confirmPasswordDoctor);
        description = findViewById(R.id.inputDescriptionDoctor);
        hospitalName = findViewById(R.id.inputHospitalNameDoctor);
        practiceYear = findViewById(R.id.inputPracticeYearDoctor);


        sat = findViewById(R.id.Saturday);
        sun = findViewById(R.id.Sunday);
        mon = findViewById(R.id.Monday);
        tues = findViewById(R.id.Tuesday);
        wed = findViewById(R.id.Wednesday);
        thurs = findViewById(R.id.Thursday);
        fri = findViewById(R.id.Friday);

        satmon = findViewById(R.id.SaturdayMorning);
        sunmon = findViewById(R.id.SundayMorning);
        monmon = findViewById(R.id.MondayMorning);
        tuesmon = findViewById(R.id.TuesdayMorning);
        wedmon = findViewById(R.id.WednesdayMorning);
        thursmon = findViewById(R.id.ThursdayMorning);
        frimon = findViewById(R.id.FridayMorning);

        sateve = findViewById(R.id.SaturdayEvening);
        suneve = findViewById(R.id.SundayEvening);
        moneve = findViewById(R.id.MondayEvening);
        tueseve = findViewById(R.id.TuesdayEvening);
        wedeve = findViewById(R.id.WednesdayEvening);
        thurseve = findViewById(R.id.ThursdayEvening);
        frieve = findViewById(R.id.FridayEvening);

        signUpBtnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString();
                final String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();
                final String descript = description.getText().toString();
                final String hosName = hospitalName.getText().toString();
                final String pracYear = practiceYear.getText().toString();
                final String location = location_type.getSelectedItem().toString();


                if (name.isEmpty() || name.length() < 7) {
                    showError(inputName, "Your Name is not valid");
                    return;
                }
                if (email.isEmpty() || !email.contains("@")) {
                    showError(inputEmail, "Email is not Valid");
                    return;
                }
                if (password.isEmpty() || password.length() < 7) {
                    showError(inputPassword, "Password must be at least 7 characters");
                    return;
                }
                if (conPassword.isEmpty() || !conPassword.equals(password)) {
                    showError(confirmPassword, "Password does not match");
                    return;
                }

                getAppointment();

                //progBarMSS.setVisibility(View.VISIBLE);

                fAuthDoctor.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup_doctor.this, "User Created", Toast.LENGTH_SHORT).show();
                            userId = fAuthDoctor.getCurrentUser().getUid();
                            DocumentReference documentReference = fstoreDoctor.collection("Doctor").document(userId);
                            Map<String, Object> doctor = new HashMap<>();
                            doctor.put("Name", name);
                            doctor.put("Email", email);
                            doctor.put("Description",descript);
                            doctor.put("Hospital/Chamber Name", hosName);
                            doctor.put("Hospital/Chamber Location", location);
                            doctor.put("Practice Starting Year", pracYear);
                            documentReference.set(doctor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created");
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), DoctorMainActivity.class));
                        } else {
                            Toast.makeText(signup_doctor.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        specialtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_doctor.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        /*workdaysBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_doctor.this);
                mBuilder.setTitle(R.string.dialog_title2);
                mBuilder.setMultiChoiceItems(listItems2, checkedItems2, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked)
                    {
                        if(isChecked)
                        {
                            mUserItems2.add(position);
                        }
                        else
                        {
                            mUserItems2.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        String item = "";
                        for (int i = 0; i < mUserItems2.size(); i++)
                        {
                            item = item + listItems2[mUserItems2.get(i)];
                            item2[i] = listItems2[mUserItems2.get(i)];
                            if (i != mUserItems2.size() - 1) {
                                item = item + ", ";

                            }
                        }
                        //shiftsBtn.setText(item2[0]);
                        mItemSelected2.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        for (int i = 0; i < checkedItems2.length; i++)
                        {
                            checkedItems2[i] = false;
                            mUserItems2.clear();
                            mItemSelected2.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        shiftsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(signup_doctor.this);
                mBuilder.setTitle("Shifts");
                mBuilder.setMultiChoiceItems(listItems3, checkedItems3, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked)
                    {
                        if(isChecked)
                        {
                            mUserItems3.add(position);
                        }
                        else
                        {
                            mUserItems3.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        String item = "";

                        for (int i = 0; i < mUserItems3.size(); i++)
                        {
                            item = item + listItems3[mUserItems3.get(i)];

                            if (i != mUserItems3.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected3.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which)
                    {
                        for (int i = 0; i < checkedItems3.length; i++)
                        {
                            checkedItems3[i] = false;
                            mUserItems3.clear();
                            mItemSelected3.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        }); */

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*public void signupbutton(View view) {
        checkCredentials();
        getAppointment();
    }*/

    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_doctor.this, login.class);
        startActivity(intent);
    }

    /*private void checkCredentials() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String conPassword = confirmPassword.getText().toString();

        if (name.isEmpty() || name.length() < 7)
            showError(inputName, "Your Name is not valid");
        else if (email.isEmpty() || !email.contains("@"))
            showError(inputEmail, "Email is not Valid");
        else if (password.isEmpty() || password.length() < 7)
            showError(inputPassword, "Password must be at least 7 characters");
        else if (conPassword.isEmpty() || !conPassword.equals(password))
            showError(confirmPassword, "Password does not match");
        else
            Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
    }*/

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void getAppointment() {
        if (sat.isChecked() && (satmon.isChecked() || sateve.isChecked())) {
            appointment[0][0] = "Saturday";
            if (satmon.isChecked()) appointment[0][1] = satmon.getText().toString();
            if (sateve.isChecked()) appointment[0][2] = sateve.getText().toString();
        }

        if (sun.isChecked() && (sunmon.isChecked() || suneve.isChecked())) {
            appointment[1][0] = "Sunday";
            if (sunmon.isChecked()) appointment[1][1] = sunmon.getText().toString();
            if (suneve.isChecked()) appointment[1][2] = suneve.getText().toString();
        }

        if (mon.isChecked() && (monmon.isChecked() || moneve.isChecked())) {
            appointment[2][0] = "Monday";
            if (monmon.isChecked()) appointment[2][1] = monmon.getText().toString();
            if (moneve.isChecked()) appointment[2][2] = moneve.getText().toString();
        }

        if (tues.isChecked() && (tuesmon.isChecked() || tueseve.isChecked())) {
            appointment[3][0] = "Tuesday";
            if (tuesmon.isChecked()) appointment[3][1] = tuesmon.getText().toString();
            if (tueseve.isChecked()) appointment[3][2] = tueseve.getText().toString();
        }

        if (wed.isChecked() && (wedmon.isChecked() || wedeve.isChecked())) {
            appointment[4][0] = "Wednesday";
            if (wedmon.isChecked()) appointment[4][1] = wedmon.getText().toString();
            if (wedeve.isChecked()) appointment[4][2] = wedeve.getText().toString();
        }

        if (thurs.isChecked() && (thursmon.isChecked() || thurseve.isChecked())) {
            appointment[5][0] = "Thursday";
            if (thursmon.isChecked()) appointment[5][1] = thursmon.getText().toString();
            if (thurseve.isChecked()) appointment[5][2] = thurseve.getText().toString();
        }

        if (fri.isChecked() && (frimon.isChecked() || frieve.isChecked())) {
            appointment[6][0] = "Friday";
            if (frimon.isChecked()) appointment[6][1] = frimon.getText().toString();
            if (frieve.isChecked()) appointment[6][2] = frieve.getText().toString();
        }

    }
}
