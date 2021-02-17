package com.example.omnisheba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class to Show the Test center information related to covid in the recyclerview
 * @author Nafisa Hossain Nujat
 */
public class TestAdapter extends RecyclerView.Adapter<ViewHolderTest> {

    FindTest findTest;
    ArrayList<Test> testArrayList;

    /**
     * Constructor
     * @param findTest The type of view to show
     * @param testArrayList The views list to show in the Recyclerview
     */
    public TestAdapter(FindTest findTest, ArrayList<Test> testArrayList) {
        this.findTest = findTest;
        this.testArrayList = testArrayList;
    }

    /**
     * Viewholder to hold the information of the Test centers
     * @param parent
     * @param viewType
     * @return the created views
     */
    @NonNull
    @Override
    public ViewHolderTest onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findTest.getBaseContext());
        View view = layoutInflater.inflate(R.layout.covid_test_list,parent,false);
        return new ViewHolderTest(view);
    }

    /**
     * Show the name, location, hotline, email of the test centers
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderTest holder, int position) {
        holder.name.setText(testArrayList.get(position).getName());
        holder.email.setText(testArrayList.get(position).getEmail());
        holder.hotline.setText(testArrayList.get(position).getHotline());
        holder.location.setText(testArrayList.get(position).getLocation());
    }

    /**
     * count the number of items to show in the Recyclerview
     * @return the test array size
     */
    @Override
    public int getItemCount() {
        return testArrayList.size();
    }
}
