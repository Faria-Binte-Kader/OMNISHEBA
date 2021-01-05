package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        chosenShift = findViewById(R.id.book_appointment_shift);
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
        final DateFormat formatterdate = new SimpleDateFormat("yyyy-MM-dd");
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


        //  DELETES APPOINTMENT WITH DATE SMALLER THAN THE CURRENT DATE
        fStore.collection(sp1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String time;
                    String date;
                    Date dat, d1;
                    String id;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {

                            time = querySnapshot.getString("Time");
                            date = querySnapshot.getString("Date");
                            id = querySnapshot.getString("ID");

                            try {
                                dat = formatterdate.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            try {
                                d1 = formatterdate.parse(nowtime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if (dat.compareTo(d1) < 0) {
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

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookAppointment.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                Log.v("failll", e.getMessage());
            }
        });

        bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            //Spinner chosen = findViewById(R.id.book_appointment_day);
            //String day_chosen = chosen.getSelectedItem().toString();
            //String shift_chosen = chosenShift.getSelectedItem().toString();
            //deletePreviousAppointments();
            String day;
            String shift;
            final int max_mss = 20;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Spinner chosen = findViewById(R.id.book_appointment_day);
                String day_chosen = chosen.getSelectedItem().toString();
                Spinner chosen_shift = findViewById(R.id.book_appointment_shift);
                String shift_chosen = chosen_shift.getSelectedItem().toString();
                if (day_chosen.equals("Monday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Monday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Monday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                else if (day_chosen.equals("Tuesday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Tuesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Tuesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Wednesday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Wednesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Wednesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Thursday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Thursday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Thursday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Friday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Friday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Friday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Saturday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Saturday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Saturday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Sunday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Sunday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Sunday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        if (temp >= max_mss)
                                            Toast.makeText(BookAppointment.this, "Sorry!No more slots available.", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                                            addCountsUnderDoctor();
                                        }
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
            }

            /*@RequiresApi(api = Build.VERSION_CODES.O)
            private void addMSSUnderDoctor() {

                Spinner chosen = findViewById(R.id.book_appointment_day);
                final String day_chosen = chosen.getSelectedItem().toString();
                Spinner chosen_shift = findViewById(R.id.book_appointment_shift);
                LocalDate ld = LocalDate.now();
                String d = "";
                if (day_chosen.equals("Monday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                    d = ld.toString();
                } else if (day_chosen.equals("Tuesday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                    d = ld.toString();
                } else if (day_chosen.equals("Wednesday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                    d = ld.toString();
                } else if (day_chosen.equals("Thursday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                    d = ld.toString();
                } else if (day_chosen.equals("Friday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                    d = ld.toString();
                } else if (day_chosen.equals("Saturday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                    d = ld.toString();
                } else if (day_chosen.equals("Sunday")) {
                    ld = ld.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                    d = ld.toString();
                }
                String shift_chosen = chosen_shift.getSelectedItem().toString();
                String shift;
                if (shift_chosen.equals("Morning")) shift = "10:00:00";
                else shift = "20:00:00";
                DocumentReference docRef2 = fStore.collection(sp1).document();

                Map<String, Object> appdoc = new HashMap<>();
                appdoc.put("Day", day_chosen);
                appdoc.put("Date", d);
                appdoc.put("Time", shift);
                appdoc.put("MSSID", fAuthDoctor.getUid());
                appdoc.put("ID", docRef2.getId());

                docRef2.set(appdoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: appointment is created");
                        Toast.makeText(BookAppointment.this, "Appointment Created", Toast.LENGTH_SHORT).show();
                    }
                });

            }*/

            //@RequiresApi(api = Build.VERSION_CODES.O)
            private void addCountsUnderDoctor() {
                Spinner chosen = findViewById(R.id.book_appointment_day);
                String day_chosen = chosen.getSelectedItem().toString();
                Spinner chosen_shift = findViewById(R.id.book_appointment_shift);
                String shift_chosen = chosen_shift.getSelectedItem().toString();
                if (day_chosen.equals("Monday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Monday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Monday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Monday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Monday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Monday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Monday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                else if (day_chosen.equals("Tuesday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Tuesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Tuesday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Tuesday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Tuesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Monday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Tuesday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Wednesday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Wednesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Wednesday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Wednesday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Wednesday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Wednesday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Wednesday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Thursday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Thursday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Thursday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Thursday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Thursday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Thursday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Thursday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Friday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Friday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Friday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Friday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Friday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Friday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Friday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Saturday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Saturday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Saturday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Saturday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Saturday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Saturday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Saturday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
                if (day_chosen.equals("Sunday")) {
                    if (shift_chosen.equals("Morning")) {
                        DocumentReference doc = fStore.collection("Sunday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountMor");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Sunday").document(sp1)
                                                .update("CountMor",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Sunday morning count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                    else if (shift_chosen.equals("Evening")) {
                        DocumentReference doc = fStore.collection("Sunday").document(sp1);
                        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            String cnt;
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document2.exists()) {
                                        Log.d("TAG", "document found");
                                        cnt = document2.getString("CountEve");
                                        int temp = Integer.parseInt(cnt);
                                        temp = temp + 1;
                                        cnt = String.valueOf(temp);
                                        fStore.collection("Sunday").document(sp1)
                                                .update("CountEve",cnt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("TAG", "onSuccess: Sunday evening count is updated");
                                                    }
                                                });
                                    } else {
                                        Log.d("TAG", "No such document");
                                    }
                                } else {
                                    Log.d("TAG", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
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