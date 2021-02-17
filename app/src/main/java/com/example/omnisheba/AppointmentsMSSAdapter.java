package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class to Show the appointments in the recyclerview made by a Medical Service Seeker
 * @author Nafisa Hossain Nujat
 */
public class AppointmentsMSSAdapter extends RecyclerView.Adapter<ViewHolderAppointmentsMss> {

    AppointmentsMss appointmentsMss;
    ArrayList<Appointment> appointmentsArrayList;

    /**
     * Constructor
     * @param appointmentsMss The type of view to show
     * @param appointmentsArrayList The views list to show in the Recyclerview
     */
    public AppointmentsMSSAdapter(AppointmentsMss appointmentsMss, ArrayList<Appointment> appointmentsArrayList) {
        this.appointmentsMss = appointmentsMss;
        this.appointmentsArrayList = appointmentsArrayList;
    }

    /**
     * View Holder to hold the appointments
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
    @Override
    public ViewHolderAppointmentsMss onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(appointmentsMss.getBaseContext());
        View view = layoutInflater.inflate(R.layout.appointments_mss_list, parent, false);
        return new ViewHolderAppointmentsMss(view);
    }

    /**
     * Show the date, weekday, time, doctor name, hospital name and location in the views
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderAppointmentsMss holder, int position) {
        holder.date2.setText(appointmentsArrayList.get(position).getDate());
        holder.day2.setText(appointmentsArrayList.get(position).getDay());
        holder.time2.setText(appointmentsArrayList.get(position).getTime());
        holder.docname2.setText(appointmentsArrayList.get(position).getDocname());
        holder.hosname2.setText(appointmentsArrayList.get(position).getHosname());
        holder.hosloc2.setText(appointmentsArrayList.get(position).getHosloc());
    }

    /**
     * count the number of items to show in the Recyclerview
     * @return the appointment array size
     */
    @Override
    public int getItemCount() {
        return appointmentsArrayList.size();
    }
}
