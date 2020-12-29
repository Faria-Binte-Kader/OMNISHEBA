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

public class FindHospital extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbHospital;
    ArrayList<Hospital> hospitalArrayList;
    HospitalAdapter adapter;

    String TAG = "FindHospital";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hospital);

        Intent intent = getIntent();
        String sp3 = intent.getStringExtra(SearchFragment.EXTRA_TEXT3);
        String sp4 = intent.getStringExtra(SearchFragment.EXTRA_TEXT4);

        TextView v3 = (TextView) findViewById(R.id.sp3);
        TextView v4 = (TextView) findViewById(R.id.sp4);
        v3.setText(sp3);
        v4.setText(sp4);

        hospitalArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase(sp3,sp4);
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (hospitalArrayList.size() > 0)
            hospitalArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewHospitalName);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (hospitalArrayList.size() > 0)
                    hospitalArrayList.clear();
                dbHospital.collection("Hospital")
                        .whereGreaterThanOrEqualTo("Name",s.toUpperCase())
                        .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Hospital hospital = new Hospital(querySnapshot.getString("Name"),
                                            querySnapshot.getString("Email"),
                                            querySnapshot.getString("Description"),
                                            querySnapshot.getString("Hotline"),
                                            querySnapshot.getString("Foundationyear"),
                                            querySnapshot.getString("Hospitaltype"),
                                            querySnapshot.getString("Hospitallocation"));
                                    hospitalArrayList.add(hospital);
                                }
                                adapter = new HospitalAdapter(FindHospital.this,hospitalArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindHospital.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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

    private void loadDataFromFirebase(String sp3, String sp4) {
        if (hospitalArrayList.size() > 0)
            hospitalArrayList.clear();
        if (sp3.contentEquals("No Department/Unit") && sp4.contentEquals("No Location"))
        {
            dbHospital.collection("Hospital")
                    //.whereEqualTo("Hospitalchamnberlocation",sp2)
                    //.whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Hospital hospital = new Hospital(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Hospitaltype"),
                                        querySnapshot.getString("Hospitallocation"));
                                hospitalArrayList.add(hospital);
                            }
                            adapter = new HospitalAdapter(FindHospital.this, hospitalArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindHospital.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

        else if (sp3.contentEquals("No Department/Unit"))
        {
            dbHospital.collection("Hospital")
                    .whereEqualTo("Hospitallocation",sp4)
                    //.whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Hospital hospital = new Hospital(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Hospitaltype"),
                                        querySnapshot.getString("Hospitallocation"));
                                hospitalArrayList.add(hospital);
                            }
                            adapter = new HospitalAdapter(FindHospital.this, hospitalArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindHospital.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

        else if (sp4.contentEquals("No Location"))
        {
            dbHospital.collection("Hospital")
                    //.whereEqualTo("Hospitalchamnberlocation",sp2)
                    .whereArrayContains("Departmentunit",sp3)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Hospital hospital = new Hospital(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Hospitaltype"),
                                        querySnapshot.getString("Hospitallocation"));
                                hospitalArrayList.add(hospital);
                            }
                            adapter = new HospitalAdapter(FindHospital.this, hospitalArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindHospital.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

        else
        {
            dbHospital.collection("Hospital")
                    .whereEqualTo("Hospitallocation",sp4)
                    .whereArrayContains("Departmentunit",sp3)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                Hospital hospital = new Hospital(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Hospitaltype"),
                                        querySnapshot.getString("Hospitallocation"));
                                hospitalArrayList.add(hospital);
                            }
                            adapter = new HospitalAdapter(FindHospital.this, hospitalArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindHospital.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

    }

    private void setUpFireBase() {
        dbHospital = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.hospitalRV);
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