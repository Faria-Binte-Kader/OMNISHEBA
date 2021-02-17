package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder class to hold the view of the test center's hotline information in the recyclerview
 * @author Nafisa Hossain Nujat
 */
public class ViewHolderHotline extends RecyclerView.ViewHolder {

    public TextView name,email,hotline,location;
    public ViewHolderHotline(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.hotlineName);
        email = itemView.findViewById(R.id.hotlineEmail);
        hotline = itemView.findViewById(R.id.hotlineHotline);
        location = itemView.findViewById(R.id.hotlineLocation);
    }
}
