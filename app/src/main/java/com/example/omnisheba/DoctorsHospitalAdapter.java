package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorsHospitalAdapter extends RecyclerView.Adapter<ViewHolderDoctorsHospital> implements AdapterView.OnItemSelectedListener {

    DoctorsHospitalActivity findDoctorsHospital;
    ArrayList<Doctor> doctorArrayList;

    public DoctorsHospitalAdapter(DoctorsHospitalActivity findDoctorsHospital, ArrayList<Doctor> doctorArrayList) {
        this.findDoctorsHospital = findDoctorsHospital;
        this.doctorArrayList = doctorArrayList;
    }

    @NonNull
    @Override
    public ViewHolderDoctorsHospital onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findDoctorsHospital.getBaseContext());
        View view = layoutInflater.inflate(R.layout.doctors_hospital_list,parent,false);
        return new ViewHolderDoctorsHospital(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDoctorsHospital holder, int position) {
        holder.name.setText(doctorArrayList.get(position).getName());
        holder.description.setText(doctorArrayList.get(position).getDescription());
        holder.email.setText(doctorArrayList.get(position).getEmail());
        holder.pracYear.setText(doctorArrayList.get(position).getPracticesatrtingyear());
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
