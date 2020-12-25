package com.example.omnisheba;

public class Doctor extends Person {
    private String hospital;
    private String practiceYear;
    private String specialty;
    private String location;
    private String[][] shift;

    public Doctor() {
        new Person();
        hospital = "";
        practiceYear = "";
        specialty = "";
        location = "";
    }

    public Doctor(String nam, String mail, String pass, String conPass, String des, String hos, String py, String sp, String loc) {
        new Person(nam, mail, pass, conPass, des);
        hospital = hos;
        practiceYear = py;
        specialty = sp;
        location = loc;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des, String hos, String py, String sp, String loc) {
        super.setInfo(nam, mail, pass, conPass, des);
        hospital = hos;
        practiceYear = py;
        specialty = sp;
        location = loc;
    }

    public String getHospital() {
        return hospital;
    }

    public String getPracticeYear() {
        return practiceYear;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getLocation() {
        return location;
    }
}
