package com.example.omnisheba;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    public TextView keyword2;
    public TextView question2;
    public TextView doctorname, showanswer;
    public EditText answer;
    public Button postanswer;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        keyword2 = itemView.findViewById(R.id.keywordlist);
        question2 = itemView.findViewById(R.id.questionlist);
        doctorname = itemView.findViewById(R.id.doctornameqna);
        showanswer = itemView.findViewById(R.id.Showanswer);
        answer = itemView.findViewById(R.id.Addanswer);
        postanswer = itemView.findViewById(R.id.postButtondoctor);
    }
}
