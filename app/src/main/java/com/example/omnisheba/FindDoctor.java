package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindDoctor extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbDoctor;
    ArrayList<Doctor> doctorArrayList;
    DoctorAdapter adapter;

    String TAG = "FindDoctor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

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
                                        querySnapshot.getString("Hospitalchamnberlocation"));
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
                    .whereEqualTo("Hospitalchamnberlocation",sp2)
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
                                        querySnapshot.getString("Hospitalchamnberlocation"));
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
                                        querySnapshot.getString("Hospitalchamnberlocation"));
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
                    .whereEqualTo("Hospitalchamnberlocation",sp2)
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
                                        querySnapshot.getString("Hospitalchamnberlocation"));
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

   /* @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }*/
}