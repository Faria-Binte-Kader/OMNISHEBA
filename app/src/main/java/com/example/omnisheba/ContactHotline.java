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

public class ContactHotline extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbHotlineHos;
    FirebaseFirestore dbHotlineTC;
    ArrayList<Hotline> hotlineArrayList;
    HotlineAdapter adapter;

    String TAG = "FindHotline";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("0!");
        setContentView(R.layout.activity_contact_hotline);

        Intent intent = getIntent();
        /*String sp3 = intent.getStringExtra(SearchFragment.EXTRA_TEXT3);
        String sp4 = intent.getStringExtra(SearchFragment.EXTRA_TEXT4);

        TextView v3 = (TextView) findViewById(R.id.sp3);
        TextView v4 = (TextView) findViewById(R.id.sp4);
        v3.setText(sp3);
        v4.setText(sp4);*/

        hotlineArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (hotlineArrayList.size() > 0)
            hotlineArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewHotline);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (hotlineArrayList.size() > 0)
                    hotlineArrayList.clear();
                dbHotlineHos.collection("Hospital")
                        .whereGreaterThanOrEqualTo("Name",s.toUpperCase())
                        .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Hotline hotline = new Hotline(querySnapshot.getString("Name"),
                                            querySnapshot.getString("Email"),
                                            querySnapshot.getString("Hotline"),
                                            querySnapshot.getString("Hospitallocation"));
                                    hotlineArrayList.add(hotline);
                                }
                                adapter = new HotlineAdapter(ContactHotline.this,hotlineArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ContactHotline.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("---I---", e.getMessage());
                            }
                        });
                dbHotlineTC.collection("TC")
                        .whereGreaterThanOrEqualTo("Name",s.toUpperCase())
                        .orderBy("Name").startAt(s.toUpperCase()).endAt(s.toUpperCase()+"\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    Hotline hotline = new Hotline(querySnapshot.getString("Name"),
                                            querySnapshot.getString("Email"),
                                            querySnapshot.getString("Hotline"),
                                            querySnapshot.getString("Testcenterlocation"));
                                    hotlineArrayList.add(hotline);
                                }
                                adapter = new HotlineAdapter(ContactHotline.this,hotlineArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ContactHotline.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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

    private void loadDataFromFirebase() {
        if (hotlineArrayList.size() > 0)
            hotlineArrayList.clear();
        dbHotlineHos.collection("Hospital")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Hotline hotline = new Hotline(querySnapshot.getString("Name"),
                                    querySnapshot.getString("Email"),
                                    querySnapshot.getString("Hotline"),
                                    querySnapshot.getString("Hospitallocation"));
                            hotlineArrayList.add(hotline);
                        }
                        adapter = new HotlineAdapter(ContactHotline.this,hotlineArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ContactHotline.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
        dbHotlineTC.collection("TC")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            Hotline hotline = new Hotline(querySnapshot.getString("Name"),
                                    querySnapshot.getString("Email"),
                                    querySnapshot.getString("Hotline"),
                                    querySnapshot.getString("Testcenterlocation"));
                            hotlineArrayList.add(hotline);
                        }
                        adapter = new HotlineAdapter(ContactHotline.this,hotlineArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ContactHotline.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void setUpFireBase() {
        dbHotlineHos = FirebaseFirestore.getInstance();
        dbHotlineTC = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.hotlineRV);
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