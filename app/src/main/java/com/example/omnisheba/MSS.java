package com.example.omnisheba;

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
}
