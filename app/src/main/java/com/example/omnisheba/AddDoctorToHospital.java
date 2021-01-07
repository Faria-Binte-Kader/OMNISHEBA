package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDoctorToHospital extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG";
    Button alreadyAddedDoctor;

    Button specialtyBtn, workdaysBtn, shiftsBtn, addDoctorBtn;
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
    ArrayList<String> special = new ArrayList<String>();


    private EditText inputName, inputEmail, inputPassword, confirmPassword, practiceYear, description;
    Spinner location_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_to_hospital);
        getSupportActionBar().setTitle("0!");

        //Spinner specialty_type = findViewById(R.id.specialty_type);
        //specialty_type.setOnItemSelectedListener( this);

        alreadyAddedDoctor = findViewById(R.id.alreadyAddedDoctor);
        addDoctorBtn = findViewById(R.id.add_doctor_btn);

        fAuthDoctor = FirebaseAuth.getInstance();
        fstoreDoctor = FirebaseFirestore.getInstance();

        specialtyBtn = findViewById(R.id.addbtnSpecialty);
        mItemSelected = (TextView) findViewById(R.id.addtvItemSelected);
        listItems = getResources().getStringArray(R.array.specialty_list);
        checkedItems = new boolean[listItems.length];

        //workdaysBtn = findViewById(R.id.btnWorkDays);
        //mItemSelected2 = (TextView) findViewById(R.id.tvItemSelected2);
        listItems2 = getResources().getStringArray(R.array.workday_list);
        checkedItems2 = new boolean[listItems2.length];

        //shiftsBtn = findViewById(R.id.btnShifts);

        //mItemSelected3 = (TextView) findViewById(R.id.tvItemSelected3);
        listItems3 = getResources().getStringArray(R.array.shift_list);
        checkedItems3 = new boolean[listItems3.length];

        inputName = findViewById(R.id.addNameDoctor);
        inputEmail = findViewById(R.id.addEmailDoctor);
        inputPassword = findViewById(R.id.addPasswordDoctor);
        confirmPassword = findViewById(R.id.addconfirmPasswordDoctor);
        description = findViewById(R.id.addDescriptionDoctor);
        practiceYear = findViewById(R.id.addPracticeYearDoctor);


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

        addDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString().toUpperCase();
                final String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String conPassword = confirmPassword.getText().toString();
                final String descript = description.getText().toString();
                final String pracYear = practiceYear.getText().toString();


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

                final String[] hosName = new String[1];
                final String[] location = new String[1];

                Intent intent = getIntent();
                String hospitalId = intent.getStringExtra(DoctorsHospitalActivity.EXTRA_TEXT1);

                DocumentReference documentReference3 = fstoreDoctor.collection("Hospital").document(hospitalId);
                documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        hosName[0] = task.getResult().getString("Name");
                        location[0] = task.getResult().getString("Hospitallocation");
                    }
                });
                //Log.e(TAG, "hospital name" + hosName[0]);
                fAuthDoctor.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddDoctorToHospital.this, "Doctor Added", Toast.LENGTH_SHORT).show();
                            userId = fAuthDoctor.getCurrentUser().getUid();

                            DocumentReference documentReference = fstoreDoctor.collection("Doctor").document(userId);
                            Map<String, Object> doctor = new HashMap<>();
                            doctor.put("Name", name);
                            doctor.put("Email", email);
                            doctor.put("Description", descript);
                            doctor.put("Specialty", special);
                            doctor.put("Hospitalchambername", hosName[0]);
                            doctor.put("Hospitalchamberlocation", location[0]);
                            doctor.put("Practicesatrtingyear", pracYear);
                            doctor.put("Type", "Doctor");
                            doctor.put("DoctorID", userId);


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

                            /*DocumentReference documentReference4 = fstoreDoctor.collection("Location").document(location[0]).collection("Doctors").document(userId);
                            Map<String, Object> loc = new HashMap<>();
                            loc.put("Name", name);
                            loc.put("Description", descript);
                            loc.put("Specialty", special);
                            loc.put("Hospitalchambername", hosName[0]);
                            loc.put("Hospitalchamberlocation", location[0]);
                            loc.put("Practicesatrtingyear", pracYear);*/

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

                            /*documentReference4.set(loc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: location is created");
                                }

                            });*/

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
                            startActivity(new Intent(getApplicationContext(), DoctorsHospitalActivity.class));
                        } else {
                            Toast.makeText(AddDoctorToHospital.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        specialtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddDoctorToHospital.this);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void gotoDoctorHospitalPage(View view) {
        Intent intent = new Intent(AddDoctorToHospital.this, DoctorsHospitalActivity.class);
        startActivity(intent);
    }

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
