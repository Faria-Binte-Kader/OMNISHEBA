package com.example.omnisheba;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MSS extends Person {
    private String dateofbirth, phone, gender;

    public MSS() {
        new Person();
        dateofbirth = "";
        phone = "";
        gender = "";
    }

    public MSS(String nam, String mail, String pass, String conPass, String des, String doB, String phn, String gen) {
        new Person(nam, mail, pass, conPass, des);
        dateofbirth = doB;
        phone = phn;
        gender = gen;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des, String doB, String phn, String gen) {
        super.setInfo(nam, mail, pass, conPass, des);
        dateofbirth = doB;
        phone = phn;
        gender = gen;
    }

    public String getDOB() {
        return dateofbirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public int calcAge() throws ParseException {
        @SuppressLint("SimpleDateFormat") Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(dateofbirth);
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        int age = 0;
        assert dateOfBirth != null;
        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;
            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }
}
