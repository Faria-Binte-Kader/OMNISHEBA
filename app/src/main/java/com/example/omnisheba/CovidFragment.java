package com.example.omnisheba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CovidFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        return inflater.inflate(R.layout.fragment_corona, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.info_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.contacts_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.tests_btn).setOnClickListener((View.OnClickListener) this);
    }

    private void showInfo() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), CovidInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void findHotline() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), ContactHotline.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void findTest() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindTest.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

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
