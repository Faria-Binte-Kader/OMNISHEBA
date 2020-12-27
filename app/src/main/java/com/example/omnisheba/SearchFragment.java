package com.example.omnisheba;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private Spinner spinner1, spinner2;
    public Button doctor, hospital, testcenter;
    RecyclerView recview;
    DoctorAdapter adapter;

    String[] values =
            {"No Specialty", "Cardiology", "ENT", "General", "Medicine", "Nephrology", "Neurology", "OB/GYN",
                    "Oncology", "Opthalmology", "Physiology", "Psychology", "Urology",};
    String[] values2 =
            {"No location", "Agargaon", "Banani", "Cantonment", "Gulshan", "Maghbazar", "Malibag", "Mirpur", "Mohammadpur",
                    "Shahbag", "Tejgaon", "Uttara",};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        spinner1 = (Spinner) v.findViewById(R.id.specialty2_type);
        spinner2 = (Spinner) v.findViewById(R.id.location2_type);

        recview = (RecyclerView) v.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        FirebaseRecyclerOptions<Doctor> options =
                new FirebaseRecyclerOptions.Builder<Doctor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor"), Doctor.class)
                        .build();

        adapter = new DoctorAdapter(options);
        recview.setAdapter(adapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.specialty2, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), R.layout.location2, values2);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.find_doctor_btn).setOnClickListener(this);
        view.findViewById(R.id.find_hospital_btn).setOnClickListener(this);
        view.findViewById(R.id.find_test_btn).setOnClickListener(this);
    }

    private void findDoctor()
    {
        final String prefLocation = spinner1.getSelectedItem().toString();
        final String prefSpecialty = spinner2.getSelectedItem().toString();

        /*SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindDoctorsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_doctor_btn:
                findDoctor();
                break;
        }
    }


}


