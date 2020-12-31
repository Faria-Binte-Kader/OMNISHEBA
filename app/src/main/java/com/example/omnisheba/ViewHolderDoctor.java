package com.example.omnisheba;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderDoctor extends RecyclerView.ViewHolder {

    public TextView name, description,email,hospital,location,pracYear;
    public Button bookappointment;
    public ViewHolderDoctor(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.doctorNameTextView);
        description = itemView.findViewById(R.id.doctorDescriptionTextView);
        email = itemView.findViewById(R.id.doctorEmailTextView);
        hospital = itemView.findViewById(R.id.doctorHospitalTextView);
        location = itemView.findViewById(R.id.doctorLocationTextView);
        pracYear = itemView.findViewById(R.id.doctorPracYearTextView);
        bookappointment = itemView.findViewById(R.id.appointment_btn);
    }
}
