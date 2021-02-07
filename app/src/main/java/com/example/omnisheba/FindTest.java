package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Search test centers that do covid test
 */
public class FindTest extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseFirestore dbTest;
    ArrayList<Test> testArrayList;
    TestAdapter adapter;

    String TAG = "TAG FindTest";
    String t = "COVID";

    /**
     * Load all test centers from firebase.
     * Activate the search option.
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_test);
        getSupportActionBar().setTitle("0!");

        testArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
        searchDataInFirebase();
    }

    /**
     * Query on the test centers that match with the text in the Searchview in the TC collection in the Firebase Firestore.
     * Use TestAdapter to adapt the testArraylist to the recyclerview
     */
    private void searchDataInFirebase() {
        if (testArrayList.size() > 0)
            testArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewTest);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (testArrayList.size() > 0)
                    testArrayList.clear();
                dbTest.collection("TC")
                        .whereArrayContains("Test", t)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String nam, email, location, hotline;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    nam = querySnapshot.getString("Name");
                                    email = querySnapshot.getString("Email");
                                    hotline = querySnapshot.getString("Hotline");
                                    location = querySnapshot.getString("Testcenterlocation");
                                    if (nam.contains(s.toUpperCase())) {
                                        Test test = new Test(nam, email, hotline, location);
                                        testArrayList.add(test);
                                    }
                                }
                                adapter = new TestAdapter(FindTest.this, testArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindTest.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
     * Load the test centers from the TC collection in the Firebase Firestore.
     * Use TestAdapter to adapt the testArraylist to the recyclerview
     */
    private void loadDataFromFirebase() {
        if (testArrayList.size() > 0)
            testArrayList.clear();
        dbTest.collection("TC")
                .whereArrayContains("Test", t)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Test test = new Test(querySnapshot.getString("Name"),
                                    querySnapshot.getString("Email"),
                                    querySnapshot.getString("Hotline"),
                                    querySnapshot.getString("Testcenterlocation"));
                            testArrayList.add(test);
                        }
                        adapter = new TestAdapter(FindTest.this, testArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindTest.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    /**
     * Set up firebase firestore
     */
    private void setUpFireBase() {
        dbTest = FirebaseFirestore.getInstance();
    }

    /**
     * Set up Recyclerview
     */
    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.testRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}