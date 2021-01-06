package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentsMss extends AppCompatActivity {


    RecyclerView mssRecyclerView;
    FirebaseFirestore dbApp;
    FirebaseAuth fAuthApp;
    ArrayList<Appointment> appointmentsArrayList;
    AppointmentsMSSAdapter adapter;

    String TAG = "FindAppointments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_mss);

        Intent intent = getIntent();

        appointmentsArrayList=new ArrayList<>();

        mssRecyclerView=findViewById(R.id.appointmentsMSSRV);
        mssRecyclerView.setHasFixedSize(true);
        mssRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setUpRecyclerView();

        dbApp = FirebaseFirestore.getInstance();
        fAuthApp = FirebaseAuth.getInstance();
        //setUpFireBase();

        if (appointmentsArrayList.size() > 0)
            appointmentsArrayList.clear();

        dbApp.collection("Schedule").document(fAuthApp.getUid())
                .collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    String dt;
                    String d;
                    String t;
                    String doc;
                    String hn;
                    String hl;
                    String id = fAuthApp.getUid();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d("TAG", "Date " + dt + "Day " + d + "Time " + t + "Doctor " + doc + "Hospital " + hn + "Location " + hl);

                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            dt = querySnapshot.getString("Date");
                            d = querySnapshot.getString("Day");
                            t = querySnapshot.getString("Time");
                            doc = querySnapshot.getString("Doctor");
                            hn = querySnapshot.getString("Hospital/Chamber name");
                            hl = querySnapshot.getString("Hospital/Chamber location");

                            Log.d("TAG", "Date " + dt + "Day " + d + "Time " + t + "Doctor " + doc + "Hospital " + hn + "Location " + hl);

                            Appointment appointment = new Appointment(dt, d, t, doc, hn, hl);
                            appointmentsArrayList.add(appointment);
                        }
                        adapter = new AppointmentsMSSAdapter(AppointmentsMss.this,appointmentsArrayList);
                        mssRecyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AppointmentsMss.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Failed");
            }
        });
        //loadDataFromFirebase();
    }

    /*private void loadDataFromFirebase() {
        if (appointmentsArrayList.size() > 0)
            appointmentsArrayList.clear();

        dbApp.collection("Schedule").document(fAuthApp.getUid())
                .collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    String dt;
                    String d;
                    String t;
                    String doc;
                    String hn;
                    String hl;
                    String id = fAuthApp.getUid();

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            dt = querySnapshot.getString("Date");
                            d = querySnapshot.getString("Day");
                            t = querySnapshot.getString("Time");
                            doc = querySnapshot.getString("Doctor");
                            hn = querySnapshot.getString("Hospital/Chamber name");
                            hl = querySnapshot.getString("Hospital/Chamber location");

                            Log.d("TAG", "Date " + dt + "Day " + d + "Time " + t + "Doctor " + doc + "Hospital " + hn + "Location " + hl);

                            Appointment appointment = new Appointment(dt, d, t, doc, hn, hl);
                            appointmentsArrayList.add(appointment);
                        }
                        adapter = new AppointmentsMSSAdapter(AppointmentsMss.this,appointmentsArrayList);
                        mssRecyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AppointmentsMss.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Failed");
            }
        });

        dbApp.collection("Schedule").document(fAuthApp.getUid())
                .collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Log.d("TAG", "data fetched");
                            Appointment appointment = new Appointment(querySnapshot.getString("Date"),
                                    querySnapshot.getString("Day"),
                                    querySnapshot.getString("Time"),
                                    querySnapshot.getString("Doctor"),
                                    querySnapshot.getString("Hospital/Chamber name"),
                                    querySnapshot.getString("Hospital/Chamber location"));
                            appointmentsArrayList.add(appointment);
                            if (appointmentsArrayList.size() > 0)
                                Log.d("TAG", "constructor worked");
                        }
                        adapter = new AppointmentsMSSAdapter(AppointmentsMss.this,appointmentsArrayList);
                        mssRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AppointmentsMss.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void setUpFireBase() {
        dbApp = FirebaseFirestore.getInstance();
        fAuthApp = FirebaseAuth.getInstance();
    }

    private void setUpRecyclerView() {
        mssRecyclerView=findViewById(R.id.appointmentsMSSRV);
        mssRecyclerView.setHasFixedSize(true);
        mssRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }*/
}