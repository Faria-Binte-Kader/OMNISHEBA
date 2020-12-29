package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderHospital extends RecyclerView.ViewHolder {

    public TextView name,type,description,email,hotline,location,foundationYear;
    public ViewHolderHospital(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.hospitalNameTextView);
        type = itemView.findViewById(R.id.hospitalTypeTextView);
        description = itemView.findViewById(R.id.hospitalDescriptionTextView);
        email = itemView.findViewById(R.id.hospitalEmailTextView);
        hotline = itemView.findViewById(R.id.hospitalHotlineTextView);
        location = itemView.findViewById(R.id.hospitalLocationTextView);
        foundationYear = itemView.findViewById(R.id.hospitalFoundationYearTextView);

    }
}
