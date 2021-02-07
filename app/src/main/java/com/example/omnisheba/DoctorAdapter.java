package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter to Show the doctors in the recyclerview to a Medical Service Seeker
 */
public class DoctorAdapter extends RecyclerView.Adapter<ViewHolderDoctor> implements AdapterView.OnItemSelectedListener {

    FindDoctor findDoctor;
    ArrayList<Doctor> doctorArrayList;

    /**
     * Constructor
     * @param findDoctor The type of view to show
     * @param doctorArrayList The views list to show in the Recyclerview
     */
    public DoctorAdapter(FindDoctor findDoctor, ArrayList<Doctor> doctorArrayList) {
        this.findDoctor = findDoctor;
        this.doctorArrayList = doctorArrayList;
    }

    /**
     * Viewholder to hold the information of the doctors for the Medical Service Seeker
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
    @Override
    public ViewHolderDoctor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findDoctor.getBaseContext());
        View view = layoutInflater.inflate(R.layout.doctor_list, parent, false);
        return new ViewHolderDoctor(view);
    }

    /**
     * Show the name, description, hospital name and location, email, practice starting year in the views
     * Show the book appointment button in the views
     * @param holder
     * @param position
     */
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
                String an = doctorArrayList.get(position).getDoctorID();
                findDoctor.appointment(an);
            }
        });
    }

    /**
     * count the number of items to show in the Recyclerview
     * @return the doctor array size
     */
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
