package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateDoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button specialtyBtn, updatebtn;
    private EditText name, email,oldpass, newpass, conpass,description, hosname;
    CheckBox sat, sun, mon, tues, wed, thurs, fri;
    CheckBox satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    String[][] appointment = new String[7][3];
    Spinner location_type;
    public static final String TAG = "TAG";
    FirebaseAuth fAuthdoc;
    private FirebaseUser user;
    FirebaseFirestore fStore;
    String userID;
    private String loc;



    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<String> test = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);
        getSupportActionBar().setTitle("0!");

        name = findViewById(R.id.updatenamedoc);
        email = findViewById(R.id.docmail);
        oldpass = findViewById(R.id.docoldpass);
        newpass = findViewById(R.id.docpass);
        conpass= findViewById(R.id.docconpass);
        description = findViewById(R.id.updatedescdoc);
        hosname = findViewById(R.id.updatehosdoc);
        fAuthdoc = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthdoc.getCurrentUser().getUid();
        updatebtn = findViewById(R.id.doctorUpdate_button);
        location_type = findViewById(R.id.location_type);
        location_type.setOnItemSelectedListener(this);

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

        specialtyBtn = findViewById(R.id.btnUpdateSpecialty);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.specialty_list);
        checkedItems = new boolean[listItems.length];


        specialtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UpdateDoctorActivity.this);
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
                            test.add(listItems[mUserItems.get(i)]);
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

    updatebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getAppointment();
            Updatedoc();
        }
    });
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void Updatedoc() {
        final String nam = name.getText().toString();
        final String mail = email.getText().toString();
        final String oldpassword = oldpass.getText().toString();
        final String Password = newpass.getText().toString();
        String conPassword = conpass.getText().toString();
        final String desc = description.getText().toString();
        final String phon = hosname.getText().toString();
        //final String type = specialty_type.getSelectedItem().toString();
        final String location = location_type.getSelectedItem().toString();


        if ( !nam.isEmpty() && nam.length() < 7) {
            showError(name, "Your Name is not valid");
            return;
        }
        if((mail.isEmpty() || oldpassword.isEmpty()) && !Password.isEmpty())
        {  showError(email, "Please input mail and old password");
            return;
        }
        if (!Password.isEmpty() && Password.length() < 7) {
            showError(newpass, "Password must be at least 7 characters");
            return;
        }
        if ((conPassword.isEmpty() || !conPassword.equals(Password))&& !Password.isEmpty()) {
            showError(conpass, "Password does not match");
            return;
        }

        if(!mail.isEmpty() && !oldpassword.isEmpty() && !conPassword.isEmpty())
        {

            user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(mail,oldpassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                View coordinatorLayout;
                                if(!task.isSuccessful()){
                                    Log.d(TAG, "Error");
                                }else {
                                    Toast.makeText(UpdateDoctorActivity.this, "Password changed ", Toast.LENGTH_SHORT).show();
                                    oldpass.setText("");
                                    newpass.setText("");
                                    conpass.setText("");
                                    email.setText("");

                                }
                            }
                        });
                    }else {
                        Toast.makeText(UpdateDoctorActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        if(!nam.isEmpty())
        {
            fStore.collection("Doctor").document(userID)
                    .update("Name",nam.toUpperCase())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated Name", Toast.LENGTH_SHORT).show();
                            name.setText("");
                        }
                    });
        }

        if(!desc.isEmpty())
        {
            fStore.collection("Doctor").document(userID)
                    .update("Description",desc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated Description", Toast.LENGTH_SHORT).show();
                            description.setText("");
                        }
                    });
        }

        if(!phon.isEmpty())
        {
            fStore.collection("Doctor").document(userID)
                    .update("Hospitalchambername",phon)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated Hospital/chamber name", Toast.LENGTH_SHORT).show();
                            hosname.setText("");
                        }
                    });
        }

        if(test.size()>0)
        {
            fStore.collection("Doctor").document(userID)
                    .update("Specialty",test)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated tspecialty", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < checkedItems.length; i++) {
                                checkedItems[i] = false;
                                mUserItems.clear();
                                mItemSelected.setText("");
                            }
                        }
                    });
        }

        if (sun.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Suneve",appointment[1][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Sunmon",appointment[1][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(suneve.isChecked())
            {suneve.toggle();}

            if(sunmon.isChecked())
            {sunmon.toggle();}

            sun.toggle();
        }
        if (mon.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Moneve",appointment[2][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated Appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Monmon",appointment[2][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated Appointment ", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(moneve.isChecked())
            {moneve.toggle();}

            if(monmon.isChecked())
            {monmon.toggle();}

            mon.toggle();
        }
        if (tues.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Tuesve",appointment[3][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Tuesmon",appointment[3][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(tueseve.isChecked())
            {tueseve.toggle();}

            if(tuesmon.isChecked())
            {tuesmon.toggle();}

            tues.toggle();
        }
        if (wed.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Wedeve",appointment[4][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Wedmon",appointment[4][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(wedeve.isChecked())
            {wedeve.toggle();}

            if(wedmon.isChecked())
            {wedmon.toggle();}

            wed.toggle();
        }
        if (thurs.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Thurseve",appointment[5][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Thursmon",appointment[5][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(thurseve.isChecked())
            {thurseve.toggle();}

            if(thursmon.isChecked())
            {thursmon.toggle();}

            thurs.toggle();
        }
        if (fri.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Frieve",appointment[6][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Frimon",appointment[6][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(frieve.isChecked())
            {frieve.toggle();}

            if(frimon.isChecked())
            {frimon.toggle();}

            fri.toggle();
        }
        if (sat.isChecked())
        {
            fStore.collection("Appointment").document(userID)
                    .update("Sateve",appointment[0][2])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            fStore.collection("Appointment").document(userID)
                    .update("Satmon",appointment[0][1])
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated appointment", Toast.LENGTH_SHORT).show();

                        }
                    });
            if(sateve.isChecked())
            {sateve.toggle();}

            if(satmon.isChecked())
            {satmon.toggle();}

            sat.toggle();
        }


       if(!location.equals("No Location"))
        {
            fStore.collection("Doctor").document(userID)
                    .update("Hospitalchamberlocation",location)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateDoctorActivity.this, "Updated Location", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
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

       /* Map<String, Object> App = new HashMap<>();
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
        App.put("Frieve", appointment[6][2]);*/

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}