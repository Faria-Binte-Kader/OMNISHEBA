package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Class to show the appointments made by a Medical Service Seeker.
 * Is set to action, when a Medical Service Seeker presses the view appointments button on their view profile fragment.
 * @author
 */
public class AppointmentsMss extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView mssRecyclerView;
    FirebaseFirestore dbApp;
    FirebaseAuth fAuthApp;
    ArrayList<Appointment> appointmentsArrayList;
    AppointmentsMSSAdapter adapter;

    String TAG = "TAG FindAppointments";

    /**
     * Set the Recylcerview so it can show the necessary information regarding the appointments from the database.
     * Retrieve Appointment data from the collection Schedule
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("0!");
        setContentView(R.layout.activity_appointments_mss);

        appointmentsArrayList = new ArrayList<>();

        mssRecyclerView = findViewById(R.id.appointmentsMSSRV);
        mssRecyclerView.setHasFixedSize(true);
        mssRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbApp = FirebaseFirestore.getInstance();
        fAuthApp = FirebaseAuth.getInstance();

        if (appointmentsArrayList.size() > 0)
            appointmentsArrayList.clear();
        dbApp.collection("Schedule").document(fAuthApp.getUid())
                .collection("Appointments")
                .orderBy("Time")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String dt;
                    String d;
                    String t;
                    String doc;
                    String hn;
                    String hl;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {

                            dt = querySnapshot.getString("Date");
                            d = querySnapshot.getString("Day");
                            t = querySnapshot.getString("Time");
                            doc = querySnapshot.getString("Doctor");
                            hn = querySnapshot.getString("Hospitalchambername");
                            hl = querySnapshot.getString("Hospitalchamberlocation");

                            Log.d(TAG, " Date " + dt + " Day " + d + " Time " + t + " Doctor " + doc + " Hospital " + hn + " Location " + hl);

                            Appointment appointment = new Appointment(dt, d, t, doc, hn, hl);
                            appointmentsArrayList.add(appointment);
                        }
                        adapter = new AppointmentsMSSAdapter(AppointmentsMss.this, appointmentsArrayList);
                        mssRecyclerView.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            /**
             * If an error occurs while retrieving the data, show it using a toast.
             * @param e
             */
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AppointmentsMss.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed");
            }
        });
    }

    /**
     * When Items are selected, show them using a toast.
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
}