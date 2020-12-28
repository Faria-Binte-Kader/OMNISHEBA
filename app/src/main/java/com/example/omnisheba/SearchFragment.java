package com.example.omnisheba;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private Spinner spinner1, spinner2;
    public Button doctor, hospital, testcenter, findDoctorByNameBtn;

    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";
    public static final String EXTRA_TEXT2 = "com.example.application.example.EXTRA_TEXT2";

    String[] values =
            {"No Specialty", "Anatomical Pathology", "Anesthesiology", "Cardiology", "Cardiovascular/Thoracic Surgery", "Clinical Immunology/Allergy",
                    "Critical Care Medicine", "Dermatology", "Emergency Medicine", "Endocrinology and Metabolism", "Family Medicine",
                    "Gastroenterology", "General Internal Medicine", "General Surgery", "General/Clinical Pathology", "Geriatric Medicine",
                    "Hematology", "Medical Biochemistry", "Medical Genetics", "Medical Microbiology and Infectious Diseases",
                    "Medical Oncology", "Nephrology", "Neurology", "Neurosurgery", "Nuclear Medicine", "Obstetrics/Gynecology",
                    "Occupational Medicine", "Ophthalmology", "Orthopedic Surgery", "Otolaryngology", "Pediatrics",
                    "Physical Medicine and Rehabilitation", "Plastic Surgery", "Psychiatry", "Public Health and Preventive Medicine",
                    "Radiation Oncology", "Respirology", "Rheumatology", "Urology"};
    String[] values2 =
            {"No Location", "Agargaon", "Banani", "Dhaka Cantonment", "Dhanmondi", "Gulshan", "Maghbazar", "Malibag", "Mirpur", "Mohammadpur",
                    "Shahbag", "Tejgaon", "Uttara"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("0!");
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        spinner1 = (Spinner) v.findViewById(R.id.specialty2_type);
        spinner2 = (Spinner) v.findViewById(R.id.location2_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.specialty2, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), R.layout.location2, values2);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.find_doctor_by_name_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.find_doctor_btn).setOnClickListener((View.OnClickListener) this);
    }

    private void findDoctorByName() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindDoctorByName.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void findDoctor() {
        String sp1 = spinner1.getSelectedItem().toString();
        String sp2 = spinner2.getSelectedItem().toString();

        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindDoctor.class);

        intent.putExtra(EXTRA_TEXT1,sp1);
        intent.putExtra(EXTRA_TEXT2,sp2);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.find_doctor_by_name_btn:
                findDoctorByName();
                break;
            case R.id.find_doctor_btn:
                findDoctor();
                break;
        }
    }
}


