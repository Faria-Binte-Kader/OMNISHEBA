package com.example.omnisheba;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolderMSS extends  RecyclerView.ViewHolder{
    public TextView keyword3;
    public TextView question3;
    public TextView doctorname3,showanswer3;
    public PostViewHolderMSS(@NonNull View itemView) {
        super(itemView);
        keyword3= itemView.findViewById(R.id.keywordlist2);
        question3= itemView.findViewById(R.id.questionlist2);
        doctorname3= itemView.findViewById(R.id.doctornameqna2);
        showanswer3= itemView.findViewById(R.id.Showanswer2);
    }
}
