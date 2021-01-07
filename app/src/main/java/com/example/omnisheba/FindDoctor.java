package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbDoctor;
    ArrayList<Doctor> doctorArrayList;
    DoctorAdapter adapter;

    public static final String EXTRA_TEXT7 = "com.example.application.example.EXTRA_TEXT7";


    String TAG = "FindDoctor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
        getSupportActionBar().setTitle("0!");

        Intent intent = getIntent();
        String sp1 = intent.getStringExtra(SearchFragment.EXTRA_TEXT1);
        String sp2 = intent.getStringExtra(SearchFragment.EXTRA_TEXT2);

        TextView v1 = (TextView) findViewById(R.id.sp1);
        TextView v2 = (TextView) findViewById(R.id.sp2);
        v1.setText(sp1);
        v2.setText(sp2);

        doctorArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase(sp1,sp2);
        searchDataInFirebase();

        /*Button appointbtn = (Button) findViewById(R.id.appointment_btn);
        appointbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment();
            }
        });*/
    }

    public void appointment(String s) {
        //SharedPrefManager.getInstance(this).clear();

        Intent intent = new Intent(FindDoctor.this, BookAppointment.class);
        intent.putExtra(EXTRA_TEXT7,s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void searchDataInFirebase() {
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewDoctorName);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (doctorArrayList.size() > 0)
                    doctorArrayList.clear();
                dbDoctor.collection("Doctor")
                        .whereGreaterThanOrEqualTo("Name",s.toUpperCase())
                        .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                            querySnapshot.getString("Email"),
                                            querySnapshot.getString("Description"),
                                            querySnapshot.getString("Hospitalchambername"),
                                            querySnapshot.getString("Practicesatrtingyear"),
                                            querySnapshot.getString("Hospitalchamberlocation"),
                                            querySnapshot.getString("DoctorID"));
                                    doctorArrayList.add(doctor);


                                }
                                adapter = new DoctorAdapter(FindDoctor.this, doctorArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindDoctor.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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

    private void loadDataFromFirebase(String sp1, String sp2) {
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();
        if (sp1.contentEquals("No Specialty") && sp2.contentEquals("No Location"))
        {
            dbDoctor.collection("Doctor")
                    //.whereEqualTo("Hospitalchamnberlocation",sp2)
                    //.whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hospitalchambername"),
                                        querySnapshot.getString("Practicesatrtingyear"),
                                        querySnapshot.getString("Hospitalchamberlocation"),
                                        querySnapshot.getString("DoctorID"));
                                doctorArrayList.add(doctor);
                            }
                            adapter = new DoctorAdapter(FindDoctor.this, doctorArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindDoctor.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
    }

        else if (sp1.contentEquals("No Specialty"))
        {
            dbDoctor.collection("Doctor")
                    .whereEqualTo("Hospitalchamberlocation",sp2)
                    //.whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hospitalchambername"),
                                        querySnapshot.getString("Practicesatrtingyear"),
                                        querySnapshot.getString("Hospitalchamberlocation"),
                                        querySnapshot.getString("DoctorID"));
                                doctorArrayList.add(doctor);
                            }
                            adapter = new DoctorAdapter(FindDoctor.this, doctorArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindDoctor.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

        else if (sp2.contentEquals("No Location"))
        {
            dbDoctor.collection("Doctor")
                    //.whereEqualTo("Hospitalchamnberlocation",sp2)
                    .whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hospitalchambername"),
                                        querySnapshot.getString("Practicesatrtingyear"),
                                        querySnapshot.getString("Hospitalchamberlocation"),
                                        querySnapshot.getString("DoctorID"));
                                doctorArrayList.add(doctor);
                            }
                            adapter = new DoctorAdapter(FindDoctor.this, doctorArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindDoctor.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

        else
        {
            dbDoctor.collection("Doctor")
                    .whereEqualTo("Hospitalchamberlocation",sp2)
                    .whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hospitalchambername"),
                                        querySnapshot.getString("Practicesatrtingyear"),
                                        querySnapshot.getString("Hospitalchamberlocation"),
                                        querySnapshot.getString("DoctorID"));
                                doctorArrayList.add(doctor);
                            }
                            adapter = new DoctorAdapter(FindDoctor.this, doctorArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindDoctor.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

    }

    private void setUpFireBase() {
        dbDoctor = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.doctorRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

   /* @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }*/
}