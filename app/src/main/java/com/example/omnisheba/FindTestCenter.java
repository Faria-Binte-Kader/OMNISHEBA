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

public class FindTestCenter extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbTestCenter;
    ArrayList<TestCenter> testCenterArrayList;
    TestCenterAdapter adapter;

    String TAG = "FindTestCenter";


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

        testCenterArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase(sp5,sp6);
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (testCenterArrayList.size() > 0)
            testCenterArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewTestCenterName);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (testCenterArrayList.size() > 0)
                    testCenterArrayList.clear();
                dbTestCenter.collection("TC")
                        .whereGreaterThanOrEqualTo("Name",s.toUpperCase())
                        .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff")
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
                                adapter = new TestCenterAdapter(FindTestCenter.this,testCenterArrayList);
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

    private void loadDataFromFirebase(String sp5, String sp6) {
        if (testCenterArrayList.size() > 0)
        testCenterArrayList.clear();
        if (sp5.contentEquals("No Test") && sp6.contentEquals("No Location"))
        {
            dbTestCenter.collection("TC")
                    //.whereEqualTo("Hospitalchamnberlocation",sp2)
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
                            adapter = new TestCenterAdapter(FindTestCenter.this,testCenterArrayList);
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

        else if (sp5.contentEquals("No Test"))
        {
            dbTestCenter.collection("TC")
                    .whereEqualTo("Testcenterlocation",sp6)
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
                            adapter = new TestCenterAdapter(FindTestCenter.this,testCenterArrayList);
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

        else if (sp6.contentEquals("No Location"))
        {
            dbTestCenter.collection("TC")
                    //.whereEqualTo("Hospitalchamnberlocation",sp2)
                    .whereArrayContains("Test",sp5)
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
                            adapter = new TestCenterAdapter(FindTestCenter.this,testCenterArrayList);
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

        else
        {
            dbTestCenter.collection("TC")
                    .whereEqualTo("Testcenterlocation",sp6)
                    .whereArrayContains("Test",sp5)
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
                            adapter = new TestCenterAdapter(FindTestCenter.this,testCenterArrayList);
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

    private void setUpFireBase() {
        dbTestCenter = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.testCenterRV);
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