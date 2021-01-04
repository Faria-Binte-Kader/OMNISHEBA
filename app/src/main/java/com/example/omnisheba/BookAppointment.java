package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BookAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView name, satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fStore;
    String DoctorID;
    Calendar calendar;
    private ArrayList<Doctor> doctorArrayList2;

    Button bookAppointmentBtn;
    Spinner chosenDay, chosenShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("0!");
        setContentView(R.layout.activity_book_appointment);
        Intent intent = getIntent();
        final String sp1 = intent.getStringExtra(FindDoctor.EXTRA_TEXT7);
        calendar = Calendar.getInstance();
        //name = findViewById(R.id.doctornameapp);
        //name.setText(sp1);
        doctorArrayList2 = new ArrayList<>();

        bookAppointmentBtn = findViewById(R.id.book_appointment_btn);
        chosenDay = findViewById(R.id.book_appointment_day);
        chosenDay.setOnItemSelectedListener(this);
        chosenShift.findViewById(R.id.book_appointment_shift);
        chosenShift.setOnItemSelectedListener(this);

        satmon = findViewById(R.id.showSatMon2);
        sunmon = findViewById(R.id.showSunMon2);
        monmon = findViewById(R.id.showMonMon2);
        tuesmon = findViewById(R.id.showTuesMon2);
        wedmon = findViewById(R.id.showWedMon2);
        thursmon = findViewById(R.id.showthursMon2);
        frimon = findViewById(R.id.showFriMon2);

        sateve = findViewById(R.id.showSatEve2);
        suneve = findViewById(R.id.showSunEve2);
        moneve = findViewById(R.id.showMonEve2);
        tueseve = findViewById(R.id.showTuesEve2);
        wedeve = findViewById(R.id.showWedEve2);
        thurseve = findViewById(R.id.showThursEve2);
        frieve = findViewById(R.id.showFriEve2);

        fAuthDoctor = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        final DateFormat formatterdate = new SimpleDateFormat("dd-MM-yyyy");
        final String nowtime = formatterdate.format(calendar.getTime());
        /*Date d1 = null;
        try {
             d1= formatterdate.parse(nowtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        String thentime;


        DocumentReference docRef = fStore.collection("Appointment").document(sp1);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        satmon.setText(document.getString("Satmon"));
                        sunmon.setText(document.getString("Sunmon"));
                        monmon.setText(document.getString("Monmon"));
                        tuesmon.setText(document.getString("Tuesmon"));
                        wedmon.setText(document.getString("Wedmon"));
                        thursmon.setText(document.getString("Thursmon"));
                        frimon.setText(document.getString("Frimon"));

                        sateve.setText(document.getString("Sateve"));
                        suneve.setText(document.getString("Suneve"));
                        moneve.setText(document.getString("Moneve"));
                        tueseve.setText(document.getString("Tueseve"));
                        wedeve.setText(document.getString("Wedeve"));
                        thurseve.setText(document.getString("Thurseve"));
                        frieve.setText(document.getString("Frieve"));
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
      /*    ADDS APPOINTMENT TO THE SELECTED DOCTOR
       DocumentReference docRef2 = fStore.collection(sp1).document();

        Map<String, Object> appdoc = new HashMap<>();
        appdoc.put("Day", calendar.get(Calendar.DAY_OF_WEEK));
        appdoc.put("Date", formatterdate.format(calendar.getTime()));
        appdoc.put("Time",  formatter.format(calendar.getTime()));
        appdoc.put("MSSID", fAuthDoctor.getUid());
        appdoc.put("ID", docRef2.getId());

        docRef2.set(appdoc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "onSuccess: appointment is created");
            }
        });*/



      /*  DELETES APPOINTMENT WITH DATE SMALLER THAN THE CURRENT DATE
      fStore.collection(sp1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String time;
                    String date;
                    Date dat,d1;
                    String id;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()) {

                                    time=querySnapshot.getString("Time");
                                    date=querySnapshot.getString("Date");
                                    id= querySnapshot.getString("ID");

                            try {
                                dat=formatterdate.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            try {
                                d1= formatterdate.parse(nowtime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if(dat.compareTo(d1)<0)
                            {
                                fStore.collection(sp1).document(id)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error deleting document", e);
                                            }
                                        });
                            }

                        }}}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookAppointment.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                Log.v("failll", e.getMessage());
            }
        });*/

        bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            //deletePreviousAppointments();
            Integer sat = 0;
            Integer sun = 0;
            Integer mon = 0;
            Integer tues = 0;
            Integer wed = 0;
            Integer thurs = 0;
            Integer fri = 0;
            String day;
            final Integer max_mss = 10;
            @Override
            public void onClick(View view) {
                addMSSUnderDoctor();
            }
                /*fStore.collection(sp1)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    day = querySnapshot.getString("Day");
                                    switch (Objects.requireNonNull(day)) {
                                        case "Saturday":
                                            sat++;
                                            break;
                                        case "Sunday":
                                            sun++;
                                            break;
                                        case "Monday":
                                            mon++;
                                            break;
                                        case "Tuesday":
                                            tues++;
                                            break;
                                        case "Wednesday":
                                            wed++;
                                            break;
                                        case "Thursday":
                                            thurs++;
                                            break;
                                        case "Friday":
                                            fri++;
                                            break;
                                        default:
                                            throw new IllegalStateException("Unexpected value: " + day);
                                    }
                                }
                            }
                        });
                final String day_chosen = chosenDay.getSelectedItem().toString();
                switch (day_chosen){
                    case "Saturday":
                        if(sat>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        else
                            addMSSUnderDoctor();
                        break;
                    case "Sunday":
                        if(sun>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        break;
                    case "Monday":
                        if(mon>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        else
                            addMSSUnderDoctor();
                        break;
                    case "Tuesday":
                        if(tues>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        else
                            addMSSUnderDoctor();
                        break;
                    case "Wednesday":
                        if(wed>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        else
                            addMSSUnderDoctor();
                        break;
                    case "Thursday":
                        if(thurs>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        else
                            addMSSUnderDoctor();
                        break;
                    case "Friday":
                        if(fri>=10)
                            Toast.makeText(BookAppointment.this, "No available slot left", Toast.LENGTH_SHORT).show();
                        else
                            addMSSUnderDoctor();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + chosenDay);
                }
            }*/

            private void addMSSUnderDoctor() {
                DocumentReference docRef2 = fStore.collection(sp1).document();

                Map<String, Object> appdoc = new HashMap<>();
                appdoc.put("Day", calendar.get(Calendar.DAY_OF_WEEK));
                appdoc.put("Date", formatterdate.format(calendar.getTime()));
                appdoc.put("Time",  formatter.format(calendar.getTime()));
                appdoc.put("MSSID", fAuthDoctor.getUid());
                appdoc.put("ID", docRef2.getId());

                docRef2.set(appdoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: appointment is created");
                    }
                });
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
}