package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
 * Search Hospitals by Medical Service Seeker
 */
public class FindHospital extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseFirestore dbHospital;
    ArrayList<Hospital> hospitalArrayList;
    HospitalAdapter adapter;

    String TAG = "TAG FindHospital";

    /**
     * When created get spinner values from SearchFragment
     * Load all hospitals from firebase.
     * Activate the search option.
     *
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hospital);

        Intent intent = getIntent();
        String sp3 = intent.getStringExtra(SearchFragment.EXTRA_TEXT3);
        String sp4 = intent.getStringExtra(SearchFragment.EXTRA_TEXT4);

        TextView v3 = findViewById(R.id.sp3);
        TextView v4 = findViewById(R.id.sp4);
        v3.setText(sp3);
        v4.setText(sp4);

        hospitalArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase(sp3, sp4);
        searchDataInFirebase(sp3, sp4);
    }

    /**
     * Search in the firebase firestore using the selected location and department/unit from the spinner
     * Query on the hospitals that match with the text in the Searchview in the Hospital collection in the Firebase Firestore.
     * Use HospitalAdapter to adapt the hospitalArraylist to the recyclerview
     *
     * @param sp3 Selected department/unit from spinner
     * @param sp4 Selected location from spinner
     */
    private void searchDataInFirebase(final String sp3, final String sp4) {
        if (hospitalArrayList.size() > 0)
            hospitalArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewHospitalName);

        if (sp3.contentEquals("No Department/Unit") && sp4.contentEquals("No Location")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (hospitalArrayList.size() > 0)
                        hospitalArrayList.clear();
                    dbHospital.collection("Hospital")
                            .whereGreaterThanOrEqualTo("Name", s.toUpperCase())
                            .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
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
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        } else if (sp3.contentEquals("No Department/Unit")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (hospitalArrayList.size() > 0)
                        hospitalArrayList.clear();
                    dbHospital.collection("Hospital")
                            .whereGreaterThanOrEqualTo("Name", s.toUpperCase())
                            .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                String nam, email, location, hotline, fdyear, des, type;

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        nam = querySnapshot.getString("Name");
                                        email = querySnapshot.getString("Email");
                                        des = querySnapshot.getString("Description");
                                        hotline = querySnapshot.getString("Hotline");
                                        fdyear = querySnapshot.getString("Foundationyear");
                                        type = querySnapshot.getString("Hospitaltype");
                                        location = querySnapshot.getString("Hospitallocation");
                                        if (location.equals(sp4)) {
                                            Hospital hospital = new Hospital(nam, email, des, hotline, fdyear, type, location);
                                            hospitalArrayList.add(hospital);
                                        }
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
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        } else if (sp4.contentEquals("No Location")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String s) {
                    if (hospitalArrayList.size() > 0)
                        hospitalArrayList.clear();
                    dbHospital.collection("Hospital")
                            .whereArrayContains("Departmentunit", sp3)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                String nam, email, location, hotline, fdyear, des, type;

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        nam = querySnapshot.getString("Name");
                                        email = querySnapshot.getString("Email");
                                        des = querySnapshot.getString("Description");
                                        hotline = querySnapshot.getString("Hotline");
                                        fdyear = querySnapshot.getString("Foundationyear");
                                        type = querySnapshot.getString("Hospitaltype");
                                        location = querySnapshot.getString("Hospitallocation");
                                        if (nam.contains(s.toUpperCase())) {
                                            Hospital hospital = new Hospital(nam, email, des, hotline, fdyear, type, location);
                                            hospitalArrayList.add(hospital);
                                        }
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
                    if (hospitalArrayList.size() > 0)
                        hospitalArrayList.clear();
                    dbHospital.collection("Hospital")
                            .whereArrayContains("Departmentunit", sp3)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                String nam, email, location, hotline, fdyear, des, type;

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        nam = querySnapshot.getString("Name");
                                        email = querySnapshot.getString("Email");
                                        des = querySnapshot.getString("Description");
                                        hotline = querySnapshot.getString("Hotline");
                                        fdyear = querySnapshot.getString("Foundationyear");
                                        type = querySnapshot.getString("Hospitaltype");
                                        location = querySnapshot.getString("Hospitallocation");
                                        if (location.equals(sp4) && nam.contains(s.toUpperCase())) {
                                            Hospital hospital = new Hospital(nam, email, des, hotline, fdyear, type, location);
                                            hospitalArrayList.add(hospital);
                                        }
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
     * Search in the firebase firestore using the selected location and department/unit from the spinner
     * Use HospitalAdapter to adapt the hospitalArraylist to the recyclerview
     *
     * @param sp3 Selected Department/Unit from spinner
     * @param sp4 Selected location from spinner
     */
    private void loadDataFromFirebase(String sp3, String sp4) {
        if (hospitalArrayList.size() > 0)
            hospitalArrayList.clear();
        if (sp3.contentEquals("No Department/Unit") && sp4.contentEquals("No Location")) {
            dbHospital.collection("Hospital")
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
        } else if (sp3.contentEquals("No Department/Unit")) {
            dbHospital.collection("Hospital")
                    .whereEqualTo("Hospitallocation", sp4)
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
        } else if (sp4.contentEquals("No Location")) {
            dbHospital.collection("Hospital")
                    .whereArrayContains("Departmentunit", sp3)
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
        } else {
            dbHospital.collection("Hospital")
                    .whereEqualTo("Hospitallocation", sp4)
                    .whereArrayContains("Departmentunit", sp3)
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

    /**
     * Set up Firebase Firestore
     */
    private void setUpFireBase() {
        dbHospital = FirebaseFirestore.getInstance();
    }

    /**
     * Set up Recyclerview
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.hospitalRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}