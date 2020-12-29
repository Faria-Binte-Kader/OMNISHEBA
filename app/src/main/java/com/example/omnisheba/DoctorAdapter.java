package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<ViewHolderDoctor> {

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
    public void onBindViewHolder(@NonNull ViewHolderDoctor holder, int position) {
        holder.name.setText(doctorArrayList.get(position).getName());
        holder.description.setText(doctorArrayList.get(position).getDescription());
        holder.location.setText(doctorArrayList.get(position).getHospitalchamnberlocation());
        holder.hospital.setText(doctorArrayList.get(position).getHospitalchambername());
        holder.email.setText(doctorArrayList.get(position).getEmail());
        holder.pracYear.setText(doctorArrayList.get(position).getPracticesatrtingyear());
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }
}
