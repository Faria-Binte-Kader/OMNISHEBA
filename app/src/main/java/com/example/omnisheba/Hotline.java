package com.example.omnisheba;

/**
 *  Model Hotline class
 *  @author Nafisa Hossain Nujat
 */
public class Hotline {
    private String name;
    private String email;
    private String hotline;
    private String location;

    /**
     * No parameter constructor
     */
    public Hotline() {
    }

    /**
     * Constructor with all the parameters
     * @param nam
     * @param mail
     * @param line
     * @param loc
     */
    public Hotline(String nam, String mail, String line, String loc) {
        this.name = nam;
        this.email = mail;
        this.hotline = line;
        this.location = loc;
    }

    /**
     * Method to set value to to class members
     * @param nam
     * @param mail
     * @param pass
     * @param conPass
     * @param des
     * @param line
     * @param found
     * @param typ
     * @param loc
     * @param du
     */
    public void setInfo(String nam, String mail, String pass, String conPass, String des, String line, String found, String typ, String loc, String[] du) {
        this.name = nam;
        this.email = mail;
        this.hotline = line;
        this.location = loc;
    }

    /**
     * setters and getters function for Hotline class
     */
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

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String val) {
        hotline = val;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String val) {
        location = val;
    }

}
