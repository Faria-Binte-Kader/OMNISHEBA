package com.example.omnisheba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * To show general Information regarding COVID-19
 * @author
 */
public class CovidInfo extends AppCompatActivity {
    TextView symptom, prec, suggestion;
    String symp = "The most common symptoms of COVID-19 are\n" +
            "\n" +
            "-Fever\n" +
            "-Dry cough\n" +
            "-Fatigue\n" + "\n" +
            "Other symptoms that are less common and may affect some patients include:\n" +
            "\n" +
            "-Loss of taste or smell,\n" +
            "-Nasal congestion,\n" +
            "-Conjunctivitis (also known as red eyes)\n" +
            "-Sore throat,\n" +
            "-Headache,\n" +
            "-Muscle or joint pain,\n" +
            "-Different types of skin rash,\n" +
            "-Nausea or vomiting,\n" +
            "-Diarrhea,\n" +
            "-Chills or dizziness.\n" +
            " \n" +
            "\n" +
            "Symptoms of severe COVID-19 disease include:\n" +
            "\n" +
            "-Shortness of breath,\n" +
            "-Loss of appetite,\n" +
            "-Confusion,\n" +
            "-Persistent pain or pressure in the chest,\n" +
            "-High temperature (above 38 °C).\n" + "\n" +
            "Other less common symptoms are:\n" +
            "\n" +
            "-Irritability,\n" +
            "-Confusion,\n" +
            "-Reduced consciousness (sometimes associated with seizures),\n" +
            "-Anxiety,\n" +
            "-Depression,\n" +
            "-Sleep disorders,\n" +
            "More severe and rare neurological complications such as strokes, brain inflammation, delirium and nerve damage.";
    String pre = "To prevent the spread of COVID-19:\n" +
            "Clean your hands often. Use soap and water, or an alcohol-based hand rub.\n" +
            "Maintain a safe distance from anyone who is coughing or sneezing.\n" +
            "Wear a mask when physical distancing is not possible.\n" +
            "Don’t touch your eyes, nose or mouth.\n" +
            "Cover your nose and mouth with your bent elbow or a tissue when you cough or sneeze.\n" +
            "Stay home if you feel unwell.\n" +
            "If you have a fever, cough and difficulty breathing, seek medical attention.";
    String sug = "If you have any symptoms suggestive of COVID-19, call your health care provider or COVID-19 hotline ( you can seek OMNISHEBA to find hotlines and covid health care services near you) for instructions and find out when and where to get a test, stay at home for 14 days away from others and monitor your health.\n" +
            "\n" +
            "If you have shortness of breath or pain or pressure in the chest, seek medical attention at a health facility immediately. Call your health care provider or hotline in advance for direction to the right health facility.\n" +
            "\n" +
            "If you live in an area with malaria or dengue fever, seek medical care if you have a fever.\n" +
            "\n" +
            "If local guidance recommends visiting a medical centre for testing, assessment or isolation, wear a medical mask while travelling to and from the facility and during medical care. Also keep at least a 1-metre distance from other people and avoid touching surfaces with your hands.  This applies to adults and children.";

    /**
     * When created, show the mentioned texts
     * @param savedInstanceState to save the state of the application so we don't lose this prior information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_info);
        getSupportActionBar().setTitle("0!");

        symptom = findViewById(R.id.symptoms);
        symptom.setText(symp);

        prec = findViewById(R.id.precaution);
        prec.setText(pre);

        suggestion = findViewById(R.id.sug);
        suggestion.setText(sug);
    }
}