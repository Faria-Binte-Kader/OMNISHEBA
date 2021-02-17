package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder class to hold the view of the test center's information which are related to covid in the recyclerview
 * @author Nafisa Hossain Nujat
 */
public class ViewHolderTest extends RecyclerView.ViewHolder {

    public TextView name,email,hotline,location;
    public ViewHolderTest(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.ctName);
        email = itemView.findViewById(R.id.ctEmail);
        hotline = itemView.findViewById(R.id.ctHotline);
        location = itemView.findViewById(R.id.ctLocation);
    }
}
