package com.example.omnisheba;

public class Doctor {
    private  String Name;
    private  String Description;
    private  String Email;
    private  String Hospitalchamnberlocation;
    private  String Hospitalchambername;
    private  String Practicesatrtingyear;


    private static String[] Specialty;
    private String Type;

    public Doctor(){}

    public Doctor(String nam, String mail, String des, String hos, String py, String loc) {
        Name = nam;
        Email = mail;
        Description = des;
        Hospitalchambername = hos;
        Practicesatrtingyear = py;
        Hospitalchamnberlocation = loc;
    }

    public  String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public  String getEmail() {
        return Email;
    }

    public  void setEmail(String email) {
        Email = email;
    }

    public  String getHospitalchamnberlocation() {
        return Hospitalchamnberlocation;
    }

    public  void setHospitalchamnberlocation(String hospitalchamnberlocation) {
        Hospitalchamnberlocation = hospitalchamnberlocation;
    }

    public  String getHospitalchambername() {
        return Hospitalchambername;
    }

    public  void setHospitalchambername(String hospitalchambername) {
        Hospitalchambername = hospitalchambername;
    }

    public  String getPracticesatrtingyear() {
        return Practicesatrtingyear;
    }

    public String getName() {
        return Name;
    }
}
