package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
