package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
