package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for using recyclerview to view Hospitals with their respective information list
 * @author Nafisa Hossain Nujat
 */

public class HospitalAdapter extends RecyclerView.Adapter<ViewHolderHospital> {

    FindHospital findHospital;
    ArrayList<Hospital> hospitalArrayList;

    /**
     * Constructor
     * @param findHospital The type of view to show
     * @param hospitalArrayList The views list to show in the Recyclerview
     */
    public HospitalAdapter(FindHospital findHospital, ArrayList<Hospital> hospitalArrayList) {
        this.findHospital = findHospital;
        this.hospitalArrayList = hospitalArrayList;
    }

    /**
     *Viewholder to hold the information of the Hospitals
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
    @Override
    public ViewHolderHospital onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findHospital.getBaseContext());
        View view = layoutInflater.inflate(R.layout.hospital_list,parent,false);
        return new ViewHolderHospital(view);
    }

    /**
     * Show the name, hospital type, description, location, email and foundation year of the hospital
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderHospital holder, int position) {
        holder.name.setText(hospitalArrayList.get(position).getName());
        holder.type.setText(hospitalArrayList.get(position).getType());
        holder.description.setText(hospitalArrayList.get(position).getDescription());
        holder.location.setText(hospitalArrayList.get(position).getLocation());
        holder.hotline.setText(hospitalArrayList.get(position).getHotline());
        holder.email.setText(hospitalArrayList.get(position).getEmail());
        holder.foundationYear.setText(hospitalArrayList.get(position).getFoundation());
    }

    /**
     * count the number of items to show in the Recyclerview
     * @return the hospital array size
     */
    @Override
    public int getItemCount() {
        return hospitalArrayList.size();
    }
}
