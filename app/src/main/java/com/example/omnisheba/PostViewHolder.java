package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends  RecyclerView.ViewHolder{
    public TextView keyword2;
    public TextView question2;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        keyword2= itemView.findViewById(R.id.keywordlist);
        question2= itemView.findViewById(R.id.questionlist);

    }
}
