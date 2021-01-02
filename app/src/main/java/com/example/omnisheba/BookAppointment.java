package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookAppointment extends AppCompatActivity {
    private TextView name,  satmon, sateve, sunmon, suneve, monmon, moneve, tuesmon, tueseve, wedmon, wedeve,
            thursmon, thurseve, frimon, frieve;
    FirebaseAuth fAuthDoctor;
    FirebaseFirestore fStore;
    String DoctorID;
    private ArrayList<Doctor> doctorArrayList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("0!");
        setContentView(R.layout.activity_book_appointment);
        Intent intent = getIntent();
        String sp1 = intent.getStringExtra(FindDoctor.EXTRA_TEXT7);
        //name = findViewById(R.id.doctornameapp);
        //name.setText(sp1);
        doctorArrayList2=new ArrayList<>();

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

       // if(fStore.collection("Doctor").document(sp1)!=null)
        /*if (doctorArrayList2.size() > 0)
            doctorArrayList2.clear();
        fStore.collection("Doctor")
                .whereGreaterThanOrEqualTo("Name", sp1.toUpperCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                           // if(querySnapshot.getString("DoctorID")!=null)
                            //{
                            /*Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                    querySnapshot.getString("Email"),
                                    querySnapshot.getString("Description"),
                                    querySnapshot.getString("Hospitalchambername"),
                                    querySnapshot.getString("Practicesatrtingyear"),
                                    querySnapshot.getString("Hospitalchamnberlocation"),
                                    querySnapshot.getString("DoctorID"));
                            doctorArrayList2.add(doctor);*/

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
/*
                            // }
                            //DoctorID=doctor.getDoctorID();

                            //  doctorArrayList.add(doctor);
                        }
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText(BookAppointment.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                Log.v("Fail", e.getMessage());
            }
        });*/



     /*if(DoctorID!=null && fStore.collection("Appointment").document(DoctorID)!=null){
        DocumentReference documentReference2 = fStore.collection("Appointment").document(DoctorID);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                satmon.setText(value.getString("Satmon"));
                sunmon.setText(value.getString("Sunmon"));
                monmon.setText(value.getString("Monmon"));
                tuesmon.setText(value.getString("Tuesmon"));
                wedmon.setText(value.getString("Wedmon"));
                thursmon.setText(value.getString("Thursmon"));
                frimon.setText(value.getString("Frimon"));

                sateve.setText(value.getString("Sateve"));
                suneve.setText(value.getString("Suneve"));
                moneve.setText(value.getString("Moneve"));
                tueseve.setText(value.getString("Tueseve"));
                wedeve.setText(value.getString("Wedeve"));
                thurseve.setText(value.getString("Thurseve"));
                frieve.setText(value.getString("Frieve"));

            }
        });
    }
     else
     {Log.d("TAG","Fail");
    }*/
}}