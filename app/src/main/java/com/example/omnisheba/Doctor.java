package com.example.omnisheba;

public class Doctor extends Person {
    private static String hospital;
    private static String practiceYear;
    private static String specialty;
    private static String location;
    private static String[][] shift;

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

    public static String getHospital() {
        return hospital;
    }

    public static String getPracticeYear() {
        return practiceYear;
    }

    public static String getSpecialty() {
        return specialty;
    }

    public static String getLocation() {
        return location;
    }
}
