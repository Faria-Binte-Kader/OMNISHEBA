package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Viewholder class to hold the view of the appointments of the medical service seeker of the recycler view
 */
public class ViewHolderAppointmentsMss extends RecyclerView.ViewHolder {

    public TextView date2,day2,time2,docname2,hosname2,hosloc2;
    public ViewHolderAppointmentsMss(@NonNull View itemView) {
        super(itemView);
        date2 = itemView.findViewById(R.id.appDateTextView);
        day2 = itemView.findViewById(R.id.appDayTextView);
        time2 = itemView.findViewById(R.id.appTimeTextView);
        docname2 = itemView.findViewById(R.id.appDocNameTextView);
        hosname2 = itemView.findViewById(R.id.appHosNameTextView);
        hosloc2 = itemView.findViewById(R.id.appHosLocTextView);
    }
}
