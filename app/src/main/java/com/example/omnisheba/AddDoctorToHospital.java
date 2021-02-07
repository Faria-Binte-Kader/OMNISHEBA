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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for adding a doctor under a hospital.
 * @author
 */
public class AddDoctorToHospital extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddDoctor";
    Button alreadyAddedDoctor;

    Button specialtyBtn, addDoctorBtn;
    String uid;

    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fstoreDoctor;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String hosName;
    String location;
    String hospitalId;

    String[] listItems2;
    boolean[] checkedItems2;

    String[] listItems3;
    boolean[] checkedItems3;

    CheckBox sat, sun, mon, tues, wed, thurs, fri;
    CheckBox satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    String[][] appointment = new String[7][3];
    ArrayList<String> special = new ArrayList<String>();


    private EditText inputName, inputEmail, practiceYear, description;


    /**
     * When this is created, set the content layout as activity_add_doctor_to_hospital
     * The functions for all the button are also defined and described.
     * @param savedInstanceState  to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_to_hospital);
        getSupportActionBar().setTitle("0!");

        alreadyAddedDoctor = findViewById(R.id.alreadyAddedDoctor);
        addDoctorBtn = findViewById(R.id.add_doctor_btn);

        fAuthDoctor = FirebaseAuth.getInstance();
        fstoreDoctor = FirebaseFirestore.getInstance();

        specialtyBtn = findViewById(R.id.addbtnSpecialty);
        mItemSelected = (TextView) findViewById(R.id.addtvItemSelected);
        listItems = getResources().getStringArray(R.array.specialty_list);
        checkedItems = new boolean[listItems.length];

        listItems2 = getResources().getStringArray(R.array.workday_list);
        checkedItems2 = new boolean[listItems2.length];

        listItems3 = getResources().getStringArray(R.array.shift_list);
        checkedItems3 = new boolean[listItems3.length];

        inputName = findViewById(R.id.addNameDoctor);
        inputEmail = findViewById(R.id.addEmailDoctor);
        description = findViewById(R.id.addDescriptionDoctor);
        practiceYear = findViewById(R.id.addPracticeYearDoctor);

        Intent intent = getIntent();
        hospitalId = intent.getStringExtra(DoctorsHospitalActivity.EXTRA_TEXT1);
        DocumentReference documentReference3 = fstoreDoctor.collection("Hospital").document(hospitalId);

        /**
         * Get the hospital name and location from database by using the hospitalID.
         */
        documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                hosName = task.getResult().getString("Name");
                location = task.getResult().getString("Hospitallocation");
            }
        });

        Log.e(TAG, "hospital name " + hosName);

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

        /**
         * Setting the actions of the Add Button.
         */
        addDoctorBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Check the name and email fields whether they follow the correct format.
             * Set their appointment schedule.
             * Save the inputted data in the collections Doctor, Appointment, Usertype and the ones for the weekdays in firebase firestore.
             * Go back to DoctorsHospitalActivity.java when data are successfully saved in the database.
             * @param view The view, Add Button, has been clicked.
             */
            @Override
            public void onClick(View view) {
                final String name = inputName.getText().toString().toUpperCase();
                final String email = inputEmail.getText().toString();
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

                getAppointment();

                DocumentReference documentReference = fstoreDoctor.collection("Doctor").document();
                uid = documentReference.getId();
                Map<String, Object> doctor = new HashMap<>();
                doctor.put("Name", name);
                doctor.put("Email", email);
                doctor.put("Description", descript);
                doctor.put("Specialty", special);
                doctor.put("Hospitalchambername", hosName);
                doctor.put("Hospitalchamberlocation", location);
                doctor.put("Practicesatrtingyear", pracYear);
                doctor.put("Type", "Doctor");
                doctor.put("DoctorID", uid);

                DocumentReference documentReference2 = fstoreDoctor.collection("Appointment").document(uid);
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

                DocumentReference documentReference4 = fstoreDoctor.collection("Usertype").document(uid);
                Map<String, Object> type = new HashMap<>();
                type.put("Type", "Doctor");

                DocumentReference documentReference5 = fstoreDoctor.collection("Monday").document(uid);
                Map<String, Object> mon = new HashMap<>();
                mon.put("CountMor", "0");
                mon.put("CountEve", "0");

                DocumentReference documentReference6 = fstoreDoctor.collection("Tuesday").document(uid);
                Map<String, Object> tue = new HashMap<>();
                tue.put("CountMor", "0");
                tue.put("CountEve", "0");

                DocumentReference documentReference7 = fstoreDoctor.collection("Wednesday").document(uid);
                Map<String, Object> wed = new HashMap<>();
                wed.put("CountMor", "0");
                wed.put("CountEve", "0");

                DocumentReference documentReference8 = fstoreDoctor.collection("Thursday").document(uid);
                Map<String, Object> thu = new HashMap<>();
                thu.put("CountMor", "0");
                thu.put("CountEve", "0");

                DocumentReference documentReference9 = fstoreDoctor.collection("Friday").document(uid);
                Map<String, Object> fri = new HashMap<>();
                fri.put("CountMor", "0");
                fri.put("CountEve", "0");

                DocumentReference documentReference10 = fstoreDoctor.collection("Saturday").document(uid);
                Map<String, Object> sat = new HashMap<>();
                sat.put("CountMor", "0");
                sat.put("CountEve", "0");

                DocumentReference documentReference11 = fstoreDoctor.collection("Sunday").document(uid);
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

                documentReference4.set(type).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        Log.d(TAG, "onSuccess: doctor added");
                    }

                });

                startActivity(new Intent(getApplicationContext(), DoctorsHospitalActivity.class));
            }
        });

        /**
         * Set actions for Specialty button.
         * Used for selecting multiple specialties.
         */
        specialtyBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * Setup multiple choice builder.
             * @param view The view, Specialties, has been clicked.
             */
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

    /**
     * When items are selected, show them using a toast.
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Go back to DoctorsHospitalActivity.java.
     * @param view
     */
    public void gotoDoctorHospitalPage(View view) {
        Intent intent = new Intent(AddDoctorToHospital.this, DoctorsHospitalActivity.class);
        startActivity(intent);
    }

    /**
     * Show error if text in EditText doesn't follow format.
     * @param input the text in EditText.
     * @param s The error to show.
     */
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    /**
     * Set the general appointment schedule of the doctor.
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
