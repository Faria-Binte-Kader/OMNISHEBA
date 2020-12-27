package com.example.omnisheba;

public class Person {
    protected String name;
    protected String email;
    protected String password;
    protected String conPassword;
    protected String description;

    public Person() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.conPassword = "";
        this.description = "";
    }

    public Person(String nam) {
        this.name = nam;
    }

    public Person(String nam, String mail, String pass, String conPass, String des) {
        this.name = nam;
        this.email = mail;
        this.password = pass;
        this.conPassword = conPass;
        this.description = des;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des) {
        this.name = nam;
        this.email = mail;
        this.password = pass;
        this.conPassword = conPass;
        this.description = des;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConPassword() {
        return conPassword;
    }

    public String getDescription() {
        return description;
    }
}
