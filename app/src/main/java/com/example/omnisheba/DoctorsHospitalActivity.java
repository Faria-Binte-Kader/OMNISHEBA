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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * To show the doctors list and add new doctors under a hospital
 * @author
 */
public class DoctorsHospitalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] s = {"No Specialty", "Cardiology", "ENT", "General", "Medicine", "Nephrology", "Neurology", "OB/GYN",
            "Oncology", "Opthalmology", "Physiology", "Psychology", "Urology",};

    RecyclerView mRecyclerView;
    String hospitalId;
    FirebaseFirestore dbDoctor;
    ArrayList<Doctor> doctorArrayList;
    DoctorsHospitalAdapter adapter;
    String hosName;

    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";

    String TAG = "TAG DoctorsHospitalActivity";

    /**
     * When created set up the Recyclerview, Firebase.
     * Load all doctors' information under that hospital
     * Activate the search option.
     * Set up the action for the add doctors button.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_hospital);
        getSupportActionBar().setTitle("0!");

        Button addBtn = findViewById(R.id.addbtn);
        hospitalId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        doctorArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
        searchDataInFirebase();
    }

    /**
     * Query on the doctors that match with the text in the Searchview in the Doctor collection in the Firebase Firestore.
     * Use the userID from FirebaseAuth to find the hospital name of the hospital admin using the application
     * Use DoctorsHospitalAdapter to adapt the doctorArrayList to the recyclerview
     */
    private void searchDataInFirebase() {
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewDoctorHospitalName);

        DocumentReference documentReference3 = dbDoctor.collection("Hospital").document(hospitalId);
        documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                hosName = task.getResult().getString("Name");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (doctorArrayList.size() > 0)
                    doctorArrayList.clear();

                dbDoctor.collection("Doctor")
                        .whereGreaterThanOrEqualTo("Name", s.toUpperCase())
                        .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String nam, email, location, hospital, pcyear, des, id;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    nam = querySnapshot.getString("Name");
                                    email = querySnapshot.getString("Email");
                                    des = querySnapshot.getString("Description");
                                    hospital = querySnapshot.getString("Hospitalchambername");
                                    pcyear = querySnapshot.getString("Practicesatrtingyear");
                                    location = querySnapshot.getString("Hospitalchamberlocation");
                                    id = querySnapshot.getString("DoctorID");
                                    if (hospital != null) {
                                        if (hospital.equals(hosName)) {
                                            Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                            doctorArrayList.add(doctor);
                                        }
                                    }
                                }
                                adapter = new DoctorsHospitalAdapter(DoctorsHospitalActivity.this, doctorArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DoctorsHospitalActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("---I---", e.getMessage());
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    /**
     * Load all the doctors of the hospital from the Doctor collection in the Firebase Firestore.
     * Use the userID from FirebaseAuth to find the hospital name of the hospital admin using the application
     * Use DoctorsHospitalAdapter to adapt the doctorArrayList to the recyclerview
     */
    private void loadDataFromFirebase() {
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();

        DocumentReference documentReference3 = dbDoctor.collection("Hospital").document(hospitalId);
        documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                hosName = task.getResult().getString("Name");
            }
        });

        dbDoctor.collection("Doctor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String nam, email, location, hospital, pcyear, des, id;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            nam = querySnapshot.getString("Name");
                            email = querySnapshot.getString("Email");
                            des = querySnapshot.getString("Description");
                            hospital = querySnapshot.getString("Hospitalchambername");
                            pcyear = querySnapshot.getString("Practicesatrtingyear");
                            location = querySnapshot.getString("Hospitalchamberlocation");
                            id = querySnapshot.getString("DoctorID");
                            if (hospital != null) {
                                if (hospital.equals(hosName)) {
                                    Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                    doctorArrayList.add(doctor);
                                }
                            }
                        }
                        adapter = new DoctorsHospitalAdapter(DoctorsHospitalActivity.this, doctorArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DoctorsHospitalActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    /**
     * Set up FirebaseFirestore
     */
    private void setUpFireBase() {
        dbDoctor = FirebaseFirestore.getInstance();
    }

    /**
     * Set up Recyclerview
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.doctorsHospitalRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * When items are selected, use a toast to show it
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
     * To go to the AddDoctorToHospital page to fillup the information on the new doctor to add under the hospital
     */
    private void add() {
        Intent intent = new Intent(this, AddDoctorToHospital.class);
        FirebaseUser userHospital;
        FirebaseAuth fAuthHos;
        fAuthHos = FirebaseAuth.getInstance();
        userHospital = fAuthHos.getCurrentUser();
        String hospitalId = userHospital.getUid();
        intent.putExtra(EXTRA_TEXT1, hospitalId);
        startActivity(intent);
    }
}