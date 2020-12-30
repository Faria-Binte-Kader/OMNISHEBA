package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<ViewHolderTest> {

    FindTest findTest;
    ArrayList<Test> testArrayList;

    public TestAdapter(FindTest findTest, ArrayList<Test> testArrayList) {
        this.findTest = findTest;
        this.testArrayList = testArrayList;
    }

    @NonNull
    @Override
    public ViewHolderTest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findTest.getBaseContext());
        View view = layoutInflater.inflate(R.layout.covid_test_list,parent,false);
        return new ViewHolderTest(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTest holder, int position) {
        holder.name.setText(testArrayList.get(position).getName());
        holder.email.setText(testArrayList.get(position).getEmail());
        holder.hotline.setText(testArrayList.get(position).getHotline());
        holder.location.setText(testArrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return testArrayList.size();
    }
}
