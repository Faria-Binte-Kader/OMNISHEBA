package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder class to hold the view of the doctor's information under certain hospital in the recyclerview
 */
public class ViewHolderDoctorsHospital extends RecyclerView.ViewHolder{
    public TextView name, description, email, pracYear;

    public ViewHolderDoctorsHospital(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.doctorHospitalNameTextView);
        description = itemView.findViewById(R.id.doctorHospitalDescriptionTextView);
        email = itemView.findViewById(R.id.doctorHospitalEmailTextView);
        pracYear = itemView.findViewById(R.id.doctorHospitalPracYearTextView);
    }
}
