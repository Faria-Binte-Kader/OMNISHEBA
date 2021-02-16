package com.example.omnisheba;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Model Medical Service Seeker Class
 * @author Tasmia Binte Sogir
 */
public class MSS extends Person {
    private String dateofbirth;
    private String phone;
    private String gender;

    /**
     * Default constructor
     */
    public MSS() {
        new Person();
        dateofbirth = "";
        phone = "";
        gender = "";
    }

    /**
     * All parameter constructor
     * @param nam
     * @param mail
     * @param pass
     * @param conPass
     * @param des
     * @param doB
     * @param phn
     * @param gen
     */
    public MSS(String nam, String mail, String pass, String conPass, String des, String doB, String phn, String gen) {
        new Person(nam, mail, pass, conPass, des);
        dateofbirth = doB;
        phone = phn;
        gender = gen;
    }

    /**
     * Method to set data to the parameters manually
     * @param nam
     * @param mail
     * @param pass
     * @param conPass
     * @param des
     * @param doB
     * @param phn
     * @param gen
     */
    public void setInfo(String nam, String mail, String pass, String conPass, String des, String doB, String phn, String gen) {
        super.setInfo(nam, mail, pass, conPass, des);
        dateofbirth = doB;
        phone = phn;
        gender = gen;
    }

    /**
     * getter setter functions
     */
    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

}
