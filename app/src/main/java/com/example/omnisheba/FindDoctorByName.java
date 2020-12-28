package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class FindDoctorByName extends AppCompatActivity {


    RecyclerView mRecyclerView;
    FirebaseFirestore dbDoctor;
    ArrayList<Doctor> doctorArrayList;
    DoctorNameAdapter adapter;

    String TAG = "FindDoctorByName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor_by_name);

        doctorArrayList=new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();

        EditText mEditText = findViewById(R.id.searchViewDoctorName);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG,"Searchbox has changed to: " + editable.toString());

                if(doctorArrayList.size()>0)
                    doctorArrayList.clear();
                dbDoctor.collection("Doctor")
                        .whereEqualTo("Name",editable.toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot querySnapshot: task.getResult()){
                                    Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                            querySnapshot.getString("Email"),
                                            querySnapshot.getString("Description"),
                                            querySnapshot.getString("Hospitalchambername"),
                                            querySnapshot.getString("Practicesatrtingyear"),
                                            querySnapshot.getString("Hospitalchamnberlocation"));
                                    doctorArrayList.add(doctor);
                                }
                                //adapter = new DoctorNameAdapter(FindDoctorByName.this,doctorArrayList);
                                mRecyclerView.setAdapter(null);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindDoctorByName.this,"Problem ---I---",Toast.LENGTH_SHORT).show();
                                Log.v("---I---",e.getMessage());
                            }
                        });
            }
        });
    }

    private void loadDataFromFirebase() {
        if(doctorArrayList.size()>0)
            doctorArrayList.clear();
        dbDoctor.collection("Doctor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()){
                            Doctor doctor = new Doctor(querySnapshot.getString("Name"),
                                    querySnapshot.getString("Email"),
                                    querySnapshot.getString("Description"),
                                    querySnapshot.getString("Hospitalchambername"),
                                    querySnapshot.getString("Practicesatrtingyear"),
                                    querySnapshot.getString("Hospitalchamnberlocation"));
                            doctorArrayList.add(doctor);
                        }
                        adapter = new DoctorNameAdapter(FindDoctorByName.this,doctorArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindDoctorByName.this,"Problem ---I---",Toast.LENGTH_SHORT).show();
                        Log.v("---I---",e.getMessage());
                    }
                });
    }

    private void setUpFireBase() {
        dbDoctor = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.doctorNameRV);
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