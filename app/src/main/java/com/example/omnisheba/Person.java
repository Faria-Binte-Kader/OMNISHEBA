package com.example.omnisheba;

public class Person {
    private static String name;
    private String email;
    private String password;
    private String conPassword;
    private String description;

    public Person() {
        name = "";
        email = "";
        password = "";
        conPassword = "";
        description = "";
    }

    public Person(String nam, String mail, String pass, String conPass, String des) {
        name = nam;
        email = mail;
        password = pass;
        conPassword = conPass;
        description = des;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des) {
        name = nam;
        email = mail;
        password = pass;
        conPassword = conPass;
        description = des;
    }

    public static String getName() {
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
