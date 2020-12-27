package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorNameAdapter extends RecyclerView.Adapter<ViewHolderDoctorName> {

    FindDoctorByName findDoctorByName;
    ArrayList<Doctor> doctorArrayList;

    public DoctorNameAdapter(FindDoctorByName findDoctorByName, ArrayList<Doctor> doctorArrayList) {
        this.findDoctorByName = findDoctorByName;
        this.doctorArrayList = doctorArrayList;
    }

    @NonNull
    @Override
    public ViewHolderDoctorName onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findDoctorByName.getBaseContext());
        View view = layoutInflater.inflate(R.layout.doctor_list,parent,false);
        return new ViewHolderDoctorName(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDoctorName holder, int position) {
        holder.name.setText(doctorArrayList.get(position).getName());
        holder.description.setText(doctorArrayList.get(position).getDescription());
        holder.location.setText(doctorArrayList.get(position).getHospitalchamnberlocation());
        holder.hospital.setText(doctorArrayList.get(position).getHospitalchambername());
        holder.email.setText(doctorArrayList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }
}
