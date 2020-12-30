package com.example.omnisheba;

public class Hotline {
    private String name;
    private String email;
    private String hotline;
    private String location;

    public Hotline() {
        /*this.name = "";
        this.email = "";
        this.password = "";
        this.conPassword = "";
        this.description = "";
        this.hotline = "";
        this.foundation = "";
        this.type = "";
        this.location = "";
        this.deptUnit = new String[]{""};*/
    }

    public Hotline(String nam, String mail, String line, String loc) {
        this.name = nam;
        this.email = mail;
        //this.password = pass;
        //this.conPassword = conPass;
        //this.description = des;
        this.hotline = line;
        //this.foundation = found;
        //this.type = typ;
        this.location = loc;
        //this.deptUnit = du;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des, String line, String found, String typ, String loc, String[] du) {
        this.name = nam;
        this.email = mail;
        //this.password = pass;
        //this.conPassword = conPass;
        //this.description = des;
        this.hotline = line;
        //this.foundation = found;
        //this.type = typ;
        this.location = loc;
        //this.deptUnit = du;
    }

    public String getName() {
        return name;
    }

    public void setName(String val) {
        name = val;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String val) {
        email = val;
    }

    /*public String getDescription() {
        return description;
    }

    public void setDescription(String val) {
        description = val;
    }*/

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String val) {
        hotline = val;
    }

    /*public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String val) {
        foundation = val;
    }

    public String getType() {
        return type;
    }

    public void setType(String val) {
        type = val;
    }*/

    public String getLocation() {
        return location;
    }

    public void setLocation(String val) {
        location = val;
    }

    /*public String[] getDeptUnit() {
        return deptUnit;
    }

    public void setDeptUnit(String[] val) {
        deptUnit = val;
    }*/
}
