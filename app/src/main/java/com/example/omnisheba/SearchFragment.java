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

/**
 * Class to show the search fragment in the medical service seeker's main activity
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    public Button doctor, hospital, testcenter, findDoctorByNameBtn;

    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";
    public static final String EXTRA_TEXT2 = "com.example.application.example.EXTRA_TEXT2";

    public static final String EXTRA_TEXT3 = "com.example.application.example.EXTRA_TEXT3";
    public static final String EXTRA_TEXT4 = "com.example.application.example.EXTRA_TEXT4";

    public static final String EXTRA_TEXT5 = "com.example.application.example.EXTRA_TEXT5";
    public static final String EXTRA_TEXT6 = "com.example.application.example.EXTRA_TEXT6";

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

    String[] values3 =
            {"No Department/Unit", "Anesthetics", "Breast Screening", "Cardiology", "Ear, Nose and Throat(ENT)", "Elderly Services Department",
                    "Gastroenterology", "General Surgery", "Gynecology", "Hematology", "Neonatal", "Neurology", "Nutrition and Dietics", "Obstetrics",
                    "Oncology", "Ophthalmology", "Orthopedics", "Physiotherapy", "Renal", "Sexual Health", "Urology", "Neonatal intensive care unit(NICU)",
                    "Pediatric intensive care unit(PICU)", "Coronary care and cardiothoracic unit(CCU/CTU)", "Surgical intensive care unit(SICU)",
                    "Medical intensive care unit(MICU)", "Long term intensive care unit(LTAC ICU)"};

    String[] values4 =
            {"No Test", "Angiography", "Autopsy", "Biopsy", "Blood Analysis", "Cholecystography", "COVID", "Endoscopy", "Gastric Fluid Analysis",
                    "Gynecological Examination", "Kidney Function Test", "Liver Function Test", "Lumbar Puncture", "Lung Ventilation/Perfusion Scan",
                    "Magnetic Resonance Imaging(MRI)", "Mammography", "Pregnancy Test", "Skin Test", "Thyroid Function Test", "Ultrasound", "Urinalysis"};

    /**
     * When created, set layout to fragment_search
     * Setup FirebaseAuth and FirebaseFirestore
     * @param inflater
     * @param container
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     * @return
     */
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

        spinner3 = (Spinner) v.findViewById(R.id.dept2_type);
        spinner4 = (Spinner) v.findViewById(R.id.location4_type);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(), R.layout.specialty2, values3);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner3.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this.getActivity(), R.layout.location2, values2);
        adapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner4.setAdapter(adapter4);

        spinner5 = (Spinner) v.findViewById(R.id.test2_type);
        spinner6 = (Spinner) v.findViewById(R.id.location5_type);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this.getActivity(), R.layout.specialty2, values4);
        adapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner5.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this.getActivity(), R.layout.location2, values2);
        adapter6.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner6.setAdapter(adapter6);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.find_doctor_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.find_hospital_btn).setOnClickListener((View.OnClickListener) this);
        view.findViewById(R.id.find_test_btn).setOnClickListener((View.OnClickListener) this);
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

    private void findHospital() {
        String sp3 = spinner3.getSelectedItem().toString();
        String sp4 = spinner4.getSelectedItem().toString();

        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindHospital.class);

        intent.putExtra(EXTRA_TEXT3,sp3);
        intent.putExtra(EXTRA_TEXT4,sp4);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void findTestCenter() {
        String sp5 = spinner5.getSelectedItem().toString();
        String sp6 = spinner6.getSelectedItem().toString();

        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), FindTestCenter.class);

        intent.putExtra(EXTRA_TEXT5,sp5);
        intent.putExtra(EXTRA_TEXT6,sp6);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.find_doctor_btn:
                findDoctor();
                break;
            case R.id.find_hospital_btn:
                findHospital();
                break;
            case R.id.find_test_btn:
                findTestCenter();
                break;
        }
    }
}


