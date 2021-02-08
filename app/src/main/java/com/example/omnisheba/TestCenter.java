package com.example.omnisheba;

public class TestCenter {
    private String name;
    private String email;
    private String password;
    private String conPassword;
    private String description;
    private String hotline;
    private String foundation;
    private String type;
    private String location;
    private String[] test;

    public TestCenter() {

    }

    public TestCenter(String nam, String mail, String des, String line, String found, String typ, String loc) {
        this.name = nam;
        this.email = mail;
        this.description = des;
        this.hotline = line;
        this.foundation = found;
        this.type = typ;
        this.location = loc;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des, String line, String found, String typ, String loc, String[] tes) {
        this.name = nam;
        this.email = mail;
        this.password = pass;
        this.conPassword = conPass;
        this.description = des;
        this.hotline = line;
        this.foundation = found;
        this.type = typ;
        this.location = loc;
        this.test = tes;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String val) {
        password = val;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String val) {
        conPassword = val;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String val) {
        description = val;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String val) {
        hotline = val;
    }

    public String getFoundation() {
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
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String val) {
        location = val;
    }

    public String[] getTest() {
        return test;
    }

    public void setTest(String[] val) {
        test = val;
    }
}
