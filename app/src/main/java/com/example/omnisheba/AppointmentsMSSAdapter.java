package com.example.omnisheba;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentsMSSAdapter extends RecyclerView.Adapter<ViewHolderAppointmentsMss>{

    AppointmentsMss appointmentsMss;
    ArrayList<Appointment> appointmentsArrayList;

    public AppointmentsMSSAdapter(AppointmentsMss appointmentsMss, ArrayList<Appointment> appointmentsArrayList) {
        this.appointmentsMss = appointmentsMss;
        this.appointmentsArrayList = appointmentsArrayList;
    }

    @NonNull
    @Override
    public ViewHolderAppointmentsMss onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(appointmentsMss.getBaseContext());
        View view = layoutInflater.inflate(R.layout.appointments_mss_list,parent,false);
        return new ViewHolderAppointmentsMss(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAppointmentsMss holder, int position) {
        Log.d("TAG", "Viewholder inside");
        holder.date2.setText(appointmentsArrayList.get(position).getDate());
        holder.day2.setText(appointmentsArrayList.get(position).getDay());
        holder.time2.setText(appointmentsArrayList.get(position).getTime());
        holder.docname2.setText(appointmentsArrayList.get(position).getDocname());
        holder.hosname2.setText(appointmentsArrayList.get(position).getHosname());
        holder.hosloc2.setText(appointmentsArrayList.get(position).getHosloc());
    }

    @Override
    public int getItemCount() {
        return appointmentsArrayList.size();
    }
}
