package com.example.omnisheba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.util.ArrayList;

public class DoctorsHospitalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] s = {"No Specialty", "Cardiology", "ENT", "General", "Medicine", "Nephrology", "Neurology", "OB/GYN",
            "Oncology", "Opthalmology", "Physiology", "Psychology", "Urology",};

    /*Button specialtyBtn;

    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();*/

    RecyclerView mRecyclerView;
    FirebaseFirestore dbDoctor;
    ArrayList<Doctor> doctorArrayList;
    DoctorsHospitalAdapter adapter;

    FirebaseAuth fAuthHospital;
    FirebaseFirestore fStore;
    String userID;

    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";

    String TAG = "DoctorsHospitalActivity";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_hospital);
        getSupportActionBar().setTitle("0!");

        //Spinner specialty_type3 = (Spinner) findViewById(R.id.specialty3_type);
        //specialty_type3.setOnItemSelectedListener(this);

        Button addBtn = findViewById(R.id.addbtn);

        /*specialtyBtn = findViewById(R.id.btnFilterSpecialty);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);
        listItems = getResources().getStringArray(R.array.specialty_list);
        checkedItems = new boolean[listItems.length];*/

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        /*specialtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DoctorsHospitalActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            mUserItems.add(position);
                        } else {
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });*/

        doctorArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();
        SearchView searchView = findViewById(R.id.searchViewDoctorHospitalName);

        final String[] hosName = new String[1];

        Intent intent = getIntent();
        String hospitalId = intent.getStringExtra(HospitalMainActivity.EXTRA_TEXT2);

        DocumentReference documentReference3 = dbDoctor.collection("Hospital").document(hospitalId);
        documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                hosName[0] = task.getResult().getString("Name");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (doctorArrayList.size() > 0)
                    doctorArrayList.clear();
                dbDoctor.collection("Doctor")
                        //.whereEqualTo("Hospitalchambername","COMBINED MILITARY HOSPITAL")
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
                                    if (hospital.equals(hosName[0])) {
                                        Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                        doctorArrayList.add(doctor);
                                    }
                                }
                                adapter = new DoctorsHospitalAdapter(DoctorsHospitalActivity.this, doctorArrayList);
                                mRecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DoctorsHospitalActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (doctorArrayList.size() > 0)
            doctorArrayList.clear();

        final String[] hosName = new String[1];

        Intent intent = getIntent();
        String hospitalId = intent.getStringExtra(HospitalMainActivity.EXTRA_TEXT2);

        DocumentReference documentReference3 = dbDoctor.collection("Hospital").document(hospitalId);
        documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                hosName[0] = task.getResult().getString("Name");
            }
        });
        //Log.e(TAG, "hospital name"+ hName[0]);
        dbDoctor.collection("Doctor")
                //.whereEqualTo("Hospitalchamnberlocation",sp2)
                //.whereArrayContains("Specialty",sp1)
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
                            if (hospital.equals(hosName[0])) {
                                Doctor doctor = new Doctor(nam, email, des, hospital, pcyear, location, id);
                                doctorArrayList.add(doctor);
                            }
                        }
                        adapter = new DoctorsHospitalAdapter(DoctorsHospitalActivity.this, doctorArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DoctorsHospitalActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });

    }

    private void setUpFireBase() {
        dbDoctor = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.doctorsHospitalRV);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void add() {
        //SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddDoctorToHospital.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        FirebaseUser userHospital;
        FirebaseAuth fAuthHos;
        fAuthHos = FirebaseAuth.getInstance();
        userHospital = fAuthHos.getCurrentUser();
        String hospitalId = userHospital.getUid();
        intent.putExtra(EXTRA_TEXT1, hospitalId);
        startActivity(intent);
    }

    private void view() {
        //SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(DoctorsHospitalActivity.this, ProfileDoctorActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void update() {
        //SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(DoctorsHospitalActivity.this, UpdateDoctorActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void appointment() {
    }
}