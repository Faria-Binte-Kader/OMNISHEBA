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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to show the signup activity of the doctor type user
 * @author Tasmia Binte Sogir
 */
public class signup_doctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG";
    Button alreadyHaveAccount;

    Button specialtyBtn, signUpBtnDoctor;
    String userId;

    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fstoreDoctor;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    String[] listItems2;
    boolean[] checkedItems2;

    String[] listItems3;
    boolean[] checkedItems3;
    CheckBox sat, sun, mon, tues, wed, thurs, fri;
    CheckBox satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    String[][] appointment = new String[7][3];
    ArrayList<String> special = new ArrayList<String>();


    private EditText inputName, inputEmail, inputPassword, confirmPassword, hospitalName, practiceYear, description;
    Spinner location_type;


    /**
     * Initialize every object after created
     * Take all the user inputs and show errors for the respective input constraints
     * Create a doctor type user through firebase and save all his information in the respective collections
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);
        getSupportActionBar().setTitle("0!");

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

        listItems2 = getResources().getStringArray(R.array.workday_list);
        checkedItems2 = new boolean[listItems2.length];

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
            /**
             * after clicked, show errors for any input constraints or create a user if there aren't any errors
             * @param view
             */
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString().toUpperCase();
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

                fAuthDoctor.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    /**
                     * After creating the user with unique email and password, save all his information in respective collections
                     * @param task
                     */
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signup_doctor.this, "User Created", Toast.LENGTH_SHORT).show();
                            userId = fAuthDoctor.getCurrentUser().getUid();

                            DocumentReference documentReference = fstoreDoctor.collection("Doctor").document(userId);
                            Map<String, Object> doctor = new HashMap<>();
                            doctor.put("Name", name);
                            doctor.put("Email", email);
                            doctor.put("Description", descript);
                            doctor.put("Specialty", special);
                            doctor.put("Hospitalchambername", hosName);
                            doctor.put("Hospitalchamberlocation", location);
                            doctor.put("Practicesatrtingyear", pracYear);
                            doctor.put("Type", "Doctor");
                            doctor.put("DoctorID",userId);

                            DocumentReference documentReference2 = fstoreDoctor.collection("Appointment").document(userId);
                            Map<String, Object> App = new HashMap<>();
                            App.put("Satmon", appointment[0][1]);
                            App.put("Sateve", appointment[0][2]);
                            App.put("Sunmon", appointment[1][1]);
                            App.put("Suneve", appointment[1][2]);
                            App.put("Monmon", appointment[2][1]);
                            App.put("Moneve", appointment[2][2]);
                            App.put("Tuesmon", appointment[3][1]);
                            App.put("Tuesve", appointment[3][2]);
                            App.put("Wedmon", appointment[4][1]);
                            App.put("Wedeve", appointment[4][2]);
                            App.put("Thursmon", appointment[5][1]);
                            App.put("Thurseve", appointment[5][2]);
                            App.put("Frimon", appointment[6][1]);
                            App.put("Frieve", appointment[6][2]);

                            DocumentReference documentReference3 = fstoreDoctor.collection("Usertype").document(userId);
                            Map<String, Object> type = new HashMap<>();
                            type.put("Type", "Doctor");

                            DocumentReference documentReference5 = fstoreDoctor.collection("Monday").document(userId);
                            Map<String, Object> mon = new HashMap<>();
                            mon.put("CountMor", "0");
                            mon.put("CountEve", "0");

                            DocumentReference documentReference6 = fstoreDoctor.collection("Tuesday").document(userId);
                            Map<String, Object> tue = new HashMap<>();
                            tue.put("CountMor", "0");
                            tue.put("CountEve", "0");

                            DocumentReference documentReference7 = fstoreDoctor.collection("Wednesday").document(userId);
                            Map<String, Object> wed = new HashMap<>();
                            wed.put("CountMor", "0");
                            wed.put("CountEve", "0");

                            DocumentReference documentReference8 = fstoreDoctor.collection("Thursday").document(userId);
                            Map<String, Object> thu = new HashMap<>();
                            thu.put("CountMor", "0");
                            thu.put("CountEve", "0");

                            DocumentReference documentReference9 = fstoreDoctor.collection("Friday").document(userId);
                            Map<String, Object> fri = new HashMap<>();
                            fri.put("CountMor", "0");
                            fri.put("CountEve", "0");

                            DocumentReference documentReference10 = fstoreDoctor.collection("Saturday").document(userId);
                            Map<String, Object> sat = new HashMap<>();
                            sat.put("CountMor", "0");
                            sat.put("CountEve", "0");

                            DocumentReference documentReference11 = fstoreDoctor.collection("Sunday").document(userId);
                            Map<String, Object> sun = new HashMap<>();
                            sun.put("CountMor", "0");
                            sun.put("CountEve", "0");

                            documentReference11.set(sun).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: sunday is created");
                                }

                            });

                            documentReference10.set(sat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: saturday is created");
                                }

                            });

                            documentReference9.set(fri).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: friday is created");
                                }

                            });

                            documentReference8.set(thu).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: thursday is created");
                                }

                            });

                            documentReference7.set(wed).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: wednesday is created");
                                }

                            });

                            documentReference6.set(tue).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: tuesday is created");
                                }

                            });

                            documentReference5.set(mon).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: monday is created");
                                }

                            });

                            documentReference3.set(type).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user type is created");
                                }

                            });

                            documentReference2.set(App).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: appointment created");
                                }

                            });
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
            /**
             * After clicking the specialty button, all the item chosen in the dialogue box, previously added
             * in the arraylist, will be shown in the respective Textview
             * @param view
             */
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
                            special.add(listItems[mUserItems.get(i)]);
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

    }

    /**
     * To make the selected word white colored in the spinner and
     * to make the word pop up when selected using toast
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Method to go to the login activity when button clicked
     * @param view
     */
    public void gotoLoginPage(View view) {
        Intent intent = new Intent(signup_doctor.this, login.class);
        startActivity(intent);
    }

    /**
     * to show the popup error
     * @param input
     * @param s
     */
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    /**
     * Method to check which day of the week and shifts are checked and record them in the appointment[][] array
     */
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
