package com.example.omnisheba;

/**
 * Model Doctor class to hold the functions and information of doctor objects
 * @author Tasmia Binte Sogir
 */
public class Doctor {

    private String Name;
    private String Description;
    private String Email;
    private String Hospitalchamnberlocation;
    private String Hospitalchambername;
    private String Practicesatrtingyear;
    private String DoctorID;


    public Doctor() {
    }

    /**
     * Constructor
     * @param nam Name of the doctor
     * @param mail Email of the doctor
     * @param des Description of the doctor
     * @param hos Which hospital the doctor works in
     * @param py Practice Starting Year of the doctor
     * @param loc Location of the chamber of the doctor
     * @param d DoctorID in the Doctors collection in Firebase Firestore
     */
    public Doctor(String nam, String mail, String des, String hos, String py, String loc, String d) {
        Name = nam;
        Email = mail;
        Description = des;
        Hospitalchambername = hos;
        Practicesatrtingyear = py;
        Hospitalchamnberlocation = loc;
        DoctorID = d;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHospitalchamnberlocation() {
        return Hospitalchamnberlocation;
    }

    public void setHospitalchamnberlocation(String hospitalchamnberlocation) {
        Hospitalchamnberlocation = hospitalchamnberlocation;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public String getHospitalchambername() {
        return Hospitalchambername;
    }

    public void setHospitalchambername(String hospitalchambername) {
        Hospitalchambername = hospitalchambername;
    }

    public String getPracticesatrtingyear() {
        return Practicesatrtingyear;
    }

    public String getName() {
        return Name;
    }
}
