package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        if (appointmentsArrayList.size() > 0)
            appointmentsArrayList.clear();
        dbApp.collection("Schedule").document(fAuthApp.getUid())
                .collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Appointment appointment = new Appointment(querySnapshot.getString("Date"),
                                    querySnapshot.getString("Day"),
                                    querySnapshot.getString("Time"),
                                    querySnapshot.getString("Doctor"),
                                    querySnapshot.getString("Hospital/Chamber name"),
                                    querySnapshot.getString("Hospital/Chamber location"));
                            appointmentsArrayList.add(appointment);
                            if (appointmentsArrayList.size() > 0)
                                Log.d("TAG", "DocumentSnapshot data: " + querySnapshot.getData());
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

   /* @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }*/
}