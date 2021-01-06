package com.example.omnisheba;

public class Appointment {
    private String date;
    private String day;
    private String time;
    private String docname;
    private String hosname;
    private String hosloc;

    public Appointment(){

    }

    public Appointment(String dt, String dy, String t, String dn, String hn, String hl) {
        this.date = dt;
        this.day = dy;
        this.time = t;
        this.docname = dn;
        this.hosname = hn;
        this.hosloc = hl;
    }

    public void setInfo(String dt, String dy, String t, String dn, String hn, String hl) {
        this.date = dt;
        this.day = dy;
        this.time = t;
        this.docname = dn;
        this.hosname = hn;
        this.hosloc = hl;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String val) { date = val; }

    public String getDay()
    {
        return day;
    }

    public void setDay(String val) { day = val; }

    public String getTime()
    {
        return time;
    }

    public void setTime(String val) { time = val; }

    public String getDocname()
    {
        return docname;
    }

    public void setDocname(String val) { docname = val; }

    public String getHosname()
    {
        return hosname;
    }

    public void setHosname(String val) { hosname = val; }

    public String getHosloc()
    {
        return hosloc;
    }

    public void setHosloc(String val) { hosloc = val; }
}
