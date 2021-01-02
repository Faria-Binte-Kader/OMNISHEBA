package com.example.omnisheba;

import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<ViewHolderDoctor> implements AdapterView.OnItemSelectedListener {

    FindDoctor findDoctor;
    ArrayList<Doctor> doctorArrayList;

    public DoctorAdapter(FindDoctor findDoctor, ArrayList<Doctor> doctorArrayList) {
        this.findDoctor = findDoctor;
        this.doctorArrayList = doctorArrayList;
    }

    @NonNull
    @Override
    public ViewHolderDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findDoctor.getBaseContext());
        View view = layoutInflater.inflate(R.layout.doctor_list,parent,false);
        return new ViewHolderDoctor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDoctor holder, final int position) {
        holder.name.setText(doctorArrayList.get(position).getName());
        holder.description.setText(doctorArrayList.get(position).getDescription());
        holder.location.setText(doctorArrayList.get(position).getHospitalchamnberlocation());
        holder.hospital.setText(doctorArrayList.get(position).getHospitalchambername());
        holder.email.setText(doctorArrayList.get(position).getEmail());
        holder.pracYear.setText(doctorArrayList.get(position).getPracticesatrtingyear());
        holder.bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String an=doctorArrayList.get(position).getDoctorID();
                findDoctor.appointment(an);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
