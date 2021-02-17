package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder class to hold the view of the test center's information in the recyclerview
 * @author Nafisa Hossain Nujat
 */
public class ViewHolderTestCenter extends RecyclerView.ViewHolder {
    public TextView name, type, description, email, hotline, location, foundationYear;

    public ViewHolderTestCenter(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.testCenterNameTextView);
        type = itemView.findViewById(R.id.testCenterTypeTextView);
        description = itemView.findViewById(R.id.testCenterDescriptionTextView);
        email = itemView.findViewById(R.id.testCenterEmailTextView);
        hotline = itemView.findViewById(R.id.testCenterHotlineTextView);
        location = itemView.findViewById(R.id.testCenterLocationTextView);
        foundationYear = itemView.findViewById(R.id.testCenterFoundationYearTextView);
    }
}
