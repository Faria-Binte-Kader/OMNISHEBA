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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Search Doctors by Medical Service Seeker
 * @author Tasmia Binte Sogir
 */
public class FindDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView mRecyclerView;
    FirebaseFirestore dbDoctor;
    ArrayList<Doctor> doctorArrayList;
    DoctorAdapter adapter;

    public static final String EXTRA_TEXT7 = "com.example.application.example.EXTRA_TEXT7";

    String TAG = "TAG FindDoctor";

    /**
     * When created get spinner values from SearchFragment
     * Load all doctors from firebase.
     * Activate the search option.
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
        getSupportActionBar().setTitle("0!");

        Intent intent = getIntent();
        String sp1 = intent.getStringExtra(SearchFragment.EXTRA_TEXT1);
        String sp2 = intent.getStringExtra(SearchFragment.EXTRA_TEXT2);

        TextView v1 = findViewById(R.id.sp1);
        TextView v2 = findViewById(R.id.sp2);
        v1.setText(sp1);
        v2.setText(sp2);

        doctorArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase(sp1, sp2);
        searchDataInFirebase(sp1, sp2);
    }

    /**
     * If they want to book an appointment under a doctor, take them to BookAppointment.class
     * @param s Under the doctor the appointment will be made
     */
    public void appointment(String s) {
        Intent intent = new Intent(FindDoctor.this, BookAppointment.class);
        intent.putExtra(EXTRA_TEXT7, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Search in the firebase firestore using the selected location and specialty from the spinner
     * Query on the doctors that match with the text in the Searchview in the Doctor collection in the Firebase Firestore.
     * Use DoctorAdapter to adapt the doctorArraylist to the recyclerview
     * @param sp1 Selected specialty from spinner
     * @param sp2 Selected location from spinner
     */
    private void searchDataInFirebase(final String sp1, final String sp2) {
        Log.e(TAG, sp1 + sp2);
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewDoctorName);

        if (sp1.contentEquals("No Specialty") && sp2.contentEquals("No Location")) {
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
        } else if (sp1.contentEquals("No Specialty")) {
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
                                        if (location.equals(sp2)) {
                                            Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                            doctorArrayList.add(doctor);
                                        }
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
        } else if (sp2.contentEquals("No Location")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String s) {
                    if (doctorArrayList.size() > 0)
                        doctorArrayList.clear();
                    dbDoctor.collection("Doctor")
                            .whereArrayContains("Specialty", sp1)
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
                                        Log.d(TAG,nam+s);
                                        if (nam.contains(s.toUpperCase())) {
                                            Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                            doctorArrayList.add(doctor);
                                        }
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
        } else {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String s) {
                    if (doctorArrayList.size() > 0)
                        doctorArrayList.clear();
                    dbDoctor.collection("Doctor")
                            .whereArrayContains("Specialty", sp1)
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
                                        if (location.equals(sp2) && nam.contains(s.toUpperCase())) {
                                            Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                            doctorArrayList.add(doctor);
                                        }
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
    }

    /**
     * Search in the firebase firestore using the selected location and specialty from the spinner
     * Use DoctorAdapter to adapt the doctorArraylist to the recyclerview
     * @param sp1 Selected specialty from spinner
     * @param sp2 Selected location from spinner
     */
    private void loadDataFromFirebase(String sp1, String sp2) {
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();
        if (sp1.contentEquals("No Specialty") && sp2.contentEquals("No Location")) {
            dbDoctor.collection("Doctor")
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
        } else if (sp1.contentEquals("No Specialty")) {
            dbDoctor.collection("Doctor")
                    .whereEqualTo("Hospitalchamberlocation", sp2)
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
        } else if (sp2.contentEquals("No Location")) {
            dbDoctor.collection("Doctor")
                    .whereArrayContains("Specialty", sp1)
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
        } else {
            dbDoctor.collection("Doctor")
                    .whereEqualTo("Hospitalchamberlocation", sp2)
                    .whereArrayContains("Specialty", sp1)
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

    /**
     * Set up Firebase Firestore
     */
    private void setUpFireBase() {
        dbDoctor = FirebaseFirestore.getInstance();
    }

    /**
     * Set up Recyclerview
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.doctorRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * When an item is selected, show it using a toast
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