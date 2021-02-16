package com.example.omnisheba;

/**
 *  Model Hospital class
 *  @author Tasmia Binte Sogir
 */

public class Hospital {
    private String name;
    private String email;
    private String password;
    private String conPassword;
    private String description;
    private String hotline;
    private String foundation;
    private String type;
    private String location;
    private String[] deptUnit;

    /**
     * No parameter constructor
     */
    public Hospital() { }

    /**
     * Constructor with all the parameters
     * @param nam
     * @param mail
     * @param des
     * @param line
     * @param found
     * @param typ
     * @param loc
     */
    public Hospital(String nam, String mail, String des, String line, String found, String typ, String loc) {
        this.name = nam;
        this.email = mail;
        this.description = des;
        this.hotline = line;
        this.foundation = found;
        this.type = typ;
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
        this.password = pass;
        this.conPassword = conPass;
        this.description = des;
        this.hotline = line;
        this.foundation = found;
        this.type = typ;
        this.location = loc;
        this.deptUnit = du;
    }

    /**
     *
     * setters and getters function for Hosptal class
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

    public String[] getDeptUnit() {
        return deptUnit;
    }

    public void setDeptUnit(String[] val) {
        deptUnit = val;
    }
}
