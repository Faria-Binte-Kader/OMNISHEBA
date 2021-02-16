package com.example.omnisheba;

/**
 * Model Person Class
 * @author Tasmia Binte Sogir
 */
public class Person {
    protected String name;
    protected String email;
    protected String password;
    protected String conPassword;
    protected String description;

    /**
     * Default constructor
     */
    public Person() {
        this.name = "";
        this.email = "";
        this.password = "";
        this.conPassword = "";
        this.description = "";
    }

    /**
     * Constructor to set the name
     * @param nam
     */
    public Person(String nam) {
        this.name = nam;
    }

    /**
     * All parameter constructor
     * @param nam
     * @param mail
     * @param pass
     * @param conPass
     * @param des
     */
    public Person(String nam, String mail, String pass, String conPass, String des) {
        this.name = nam;
        this.email = mail;
        this.password = pass;
        this.conPassword = conPass;
        this.description = des;
    }

    /**
     * Method to set data to the parameters manually
     * @param nam
     * @param mail
     * @param pass
     * @param conPass
     * @param des
     */
    public void setInfo(String nam, String mail, String pass, String conPass, String des) {
        this.name = nam;
        this.email = mail;
        this.password = pass;
        this.conPassword = conPass;
        this.description = des;
    }

    /**
     * getter setter functions
     */
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }

    public void setDescription(String description) {
        this.description = description;
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
