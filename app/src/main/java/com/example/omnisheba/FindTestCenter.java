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
 * @author Nafisa Hossain Nujat
 */
public class FindTestCenter extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbTestCenter;
    ArrayList<TestCenter> testCenterArrayList;
    TestCenterAdapter adapter;

    String TAG = "TAG FindTestCenter";

    /**
     * Load all test centers from firebase.
     * Activate the search option.
     *
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_test_center);
        getSupportActionBar().setTitle("0!");

        Intent intent = getIntent();
        String sp5 = intent.getStringExtra(SearchFragment.EXTRA_TEXT5);
        String sp6 = intent.getStringExtra(SearchFragment.EXTRA_TEXT6);

        TextView v5 = (TextView) findViewById(R.id.sp5);
        TextView v6 = (TextView) findViewById(R.id.sp6);
        v5.setText(sp5);
        v6.setText(sp6);

        testCenterArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase(sp5, sp6);
        searchDataInFirebase(sp5, sp6);
    }

    /**
     * Search in the firebase firestore using the selected location and test name from the spinner
     * Query on the test centers that match with the text in the Searchview in the TC collection in the Firebase Firestore.
     * Use TestCenterAdapter to adapt the testCenterArraylist to the recyclerview
     */
    private void searchDataInFirebase(final String sp5, final String sp6) {
        if (testCenterArrayList.size() > 0)
            testCenterArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewTestCenterName);

        if (sp5.contentEquals("No Test") && sp6.contentEquals("No Location")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (testCenterArrayList.size() > 0)
                        testCenterArrayList.clear();
                    dbTestCenter.collection("TC")
                            .whereGreaterThanOrEqualTo("Name", s.toUpperCase())
                            .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        TestCenter testCenter = new TestCenter(querySnapshot.getString("Name"),
                                                querySnapshot.getString("Email"),
                                                querySnapshot.getString("Description"),
                                                querySnapshot.getString("Hotline"),
                                                querySnapshot.getString("Foundationyear"),
                                                querySnapshot.getString("Testcentertype"),
                                                querySnapshot.getString("Testcenterlocation"));
                                        testCenterArrayList.add(testCenter);
                                    }
                                    adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                                    mRecyclerView.setAdapter(adapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        } else if (sp5.contentEquals("No Test")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (testCenterArrayList.size() > 0)
                        testCenterArrayList.clear();
                    dbTestCenter.collection("TC")
                            .whereGreaterThanOrEqualTo("Name", s.toUpperCase())
                            .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                String nam, email, des, hotline, fdyear, type, location;

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        nam = querySnapshot.getString("Name");
                                        email = querySnapshot.getString("Email");
                                        des = querySnapshot.getString("Description");
                                        hotline = querySnapshot.getString("Hotline");
                                        fdyear = querySnapshot.getString("Foundationyear");
                                        type = querySnapshot.getString("Testcentertype");
                                        location = querySnapshot.getString("Testcenterlocation");
                                        if (location.equals(sp6)) {
                                            TestCenter testCenter = new TestCenter(nam, email, des, hotline, fdyear, type, location);
                                            testCenterArrayList.add(testCenter);
                                        }
                                    }
                                    adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                                    mRecyclerView.setAdapter(adapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        } else if (sp6.contentEquals("No Location")) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String s) {
                    if (testCenterArrayList.size() > 0)
                        testCenterArrayList.clear();
                    dbTestCenter.collection("TC")
                            .whereArrayContains("Test", sp5)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                String nam, email, des, hotline, fdyear, type, location;

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        nam = querySnapshot.getString("Name");
                                        email = querySnapshot.getString("Email");
                                        des = querySnapshot.getString("Description");
                                        hotline = querySnapshot.getString("Hotline");
                                        fdyear = querySnapshot.getString("Foundationyear");
                                        type = querySnapshot.getString("Testcentertype");
                                        location = querySnapshot.getString("Testcenterlocation");
                                        if (nam.contains(s.toUpperCase())) {
                                            TestCenter testCenter = new TestCenter(nam, email, des, hotline, fdyear, type, location);
                                            testCenterArrayList.add(testCenter);
                                        }
                                    }
                                    adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                                    mRecyclerView.setAdapter(adapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
                    if (testCenterArrayList.size() > 0)
                        testCenterArrayList.clear();
                    dbTestCenter.collection("TC")
                            .whereArrayContains("Test", sp5)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                String nam, email, des, hotline, fdyear, type, location;

                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for (DocumentSnapshot querySnapshot : task.getResult()) {
                                        nam = querySnapshot.getString("Name");
                                        email = querySnapshot.getString("Email");
                                        des = querySnapshot.getString("Description");
                                        hotline = querySnapshot.getString("Hotline");
                                        fdyear = querySnapshot.getString("Foundationyear");
                                        type = querySnapshot.getString("Testcentertype");
                                        location = querySnapshot.getString("Testcenterlocation");
                                        if (location.equals(sp6) && nam.contains(s.toUpperCase())) {
                                            TestCenter testCenter = new TestCenter(nam, email, des, hotline, fdyear, type, location);
                                            testCenterArrayList.add(testCenter);
                                        }
                                    }
                                    adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                                    mRecyclerView.setAdapter(adapter);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
     * Search in the firebase firestore using the selected location and test name from the spinner
     * Load the test centers from the TC collection in the Firebase Firestore.
     * Use TestCenterAdapter to adapt the testCenterArraylist to the recyclerview
     */
    private void loadDataFromFirebase(String sp5, String sp6) {
        if (testCenterArrayList.size() > 0)
            testCenterArrayList.clear();
        if (sp5.contentEquals("No Test") && sp6.contentEquals("No Location")) {
            dbTestCenter.collection("TC")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                TestCenter testCenter = new TestCenter(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Testcentertype"),
                                        querySnapshot.getString("Testcenterlocation"));
                                testCenterArrayList.add(testCenter);
                            }
                            adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        } else if (sp5.contentEquals("No Test")) {
            dbTestCenter.collection("TC")
                    .whereEqualTo("Testcenterlocation", sp6)
                    //.whereArrayContains("Specialty",sp1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                TestCenter testCenter = new TestCenter(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Testcentertype"),
                                        querySnapshot.getString("Testcenterlocation"));
                                testCenterArrayList.add(testCenter);
                            }
                            adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        } else if (sp6.contentEquals("No Location")) {
            dbTestCenter.collection("TC")
                    .whereArrayContains("Test", sp5)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                TestCenter testCenter = new TestCenter(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Testcentertype"),
                                        querySnapshot.getString("Testcenterlocation"));
                                testCenterArrayList.add(testCenter);
                            }
                            adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        } else {
            dbTestCenter.collection("TC")
                    .whereEqualTo("Testcenterlocation", sp6)
                    .whereArrayContains("Test", sp5)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot querySnapshot : task.getResult()) {
                                TestCenter testCenter = new TestCenter(querySnapshot.getString("Name"),
                                        querySnapshot.getString("Email"),
                                        querySnapshot.getString("Description"),
                                        querySnapshot.getString("Hotline"),
                                        querySnapshot.getString("Foundationyear"),
                                        querySnapshot.getString("Testcentertype"),
                                        querySnapshot.getString("Testcenterlocation"));
                                testCenterArrayList.add(testCenter);
                            }
                            adapter = new TestCenterAdapter(FindTestCenter.this, testCenterArrayList);
                            mRecyclerView.setAdapter(adapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindTestCenter.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                            Log.v("---I---", e.getMessage());
                        }
                    });
        }

    }

    /**
     * Set up firebase firestore
     */
    private void setUpFireBase() {
        dbTestCenter = FirebaseFirestore.getInstance();
    }

    /**
     * Set up Recyclerview
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.testCenterRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}