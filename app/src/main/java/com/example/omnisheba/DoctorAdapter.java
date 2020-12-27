package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DoctorAdapter extends FirebaseRecyclerAdapter<Doctor,DoctorAdapter.myviewholder>
{
    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<Doctor> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Doctor model)
    {
        holder.name.setText(Doctor.getName());
        holder.hospital.setText(Doctor.getHospital());
        holder.location.setText(Doctor.getLocation());
        holder.specialty.setText(Doctor.getSpecialty());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,hospital,location,specialty;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            hospital = (TextView)itemView.findViewById(R.id.hospital);
            location = (TextView)itemView.findViewById(R.id.location);
            specialty = (TextView)itemView.findViewById(R.id.specialty);
        }
    }
}
