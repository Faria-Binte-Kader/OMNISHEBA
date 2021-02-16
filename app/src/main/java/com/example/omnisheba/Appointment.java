package com.example.omnisheba;

/**
 * Model Appointment class to hold the information regarding each appointment made between a medical service seeker and doctor.
 * @author
 */
public class Appointment {
    private String date;
    private String day;
    private String time;
    private String docname;
    private String hosname;
    private String hosloc;

    public Appointment() {
    }

    /**
     * Constructor
     * @param dt the date of the appointment
     * @param dy the weekday of the appointment
     * @param t the time of the appointment
     * @param dn Doctor Name
     * @param hn Hospital Name
     * @param hl Hospital Location
     */
    public Appointment(String dt, String dy, String t, String dn, String hn, String hl) {
        this.date = dt;
        this.day = dy;
        this.time = t;
        this.docname = dn;
        this.hosname = hn;
        this.hosloc = hl;
    }

    /**
     * Set Information in the objects of the Appointment class.
     * @param dt the date of the appointment
     * @param dy the weekday of the appointment
     * @param t the time of the appointment
     * @param dn Doctor Name
     * @param hn Hospital Name
     * @param hl Hospital Location
     */
    public void setInfo(String dt, String dy, String t, String dn, String hn, String hl) {
        this.date = dt;
        this.day = dy;
        this.time = t;
        this.docname = dn;
        this.hosname = hn;
        this.hosloc = hl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String val) {
        date = val;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String val) {
        day = val;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String val) {
        time = val;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String val) {
        docname = val;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String val) {
        hosname = val;
    }

    public String getHosloc() {
        return hosloc;
    }

    public void setHosloc(String val) {
        hosloc = val;
    }
}
