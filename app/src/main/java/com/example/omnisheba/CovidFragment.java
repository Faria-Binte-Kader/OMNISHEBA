package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Class for showing the information regarding COVID-19 in the Covid fragment of Medical Service Seeker
 * @author
 */
public class CovidFragment extends Fragment implements View.OnClickListener {

    /**
     * When created, open the xml file of fragment_corona
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the inflated layout of fragment_corona
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        return inflater.inflate(R.layout.fragment_corona, container, false);
    }

    /**
     * Setting OnClickListener to the 3 buttons - info_btn, contacts_btn, tests_btn
     * @param view
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.info_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.contacts_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.tests_btn).setOnClickListener((View.OnClickListener) this);
    }

    /**
     * Go to CovidInfo.java to show information regarding COVID_19
     */
    private void showInfo() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), CovidInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Go to ContactHotline.java to find the emergency contact info of the hospitals and Test Centers
     */
    private void findHotline() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), ContactHotline.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Go to FindTest.java to find and search the Test Centers that do COVID-19 test
     */
    private void findTest() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindTest.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Set actions of different buttons when they are pressed.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_btn:
                showInfo();
                break;
            case R.id.contacts_btn:
                findHotline();
                break;
            case R.id.tests_btn:
                findTest();
                break;
        }
    }
}
