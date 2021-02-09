package com.example.omnisheba;

/**
 * Model class to show only the test center name,location,email and hotline number in the recycler view
 */
public class Test {
    private String name;
    private String email;
    private String hotline;
    private String location;

    /**
     * default constructor
     */
    public Test() {

    }

    /**
     * All parameter constructor
     * @param nam
     * @param mail
     * @param line
     * @param loc
     */
    public Test(String nam, String mail, String line, String loc) {
        this.name = nam;
        this.email = mail;
        this.hotline = line;
        this.location = loc;
    }

    public void setInfo(String nam, String mail, String pass, String conPass, String des, String line, String found, String typ, String loc, String[] du) {
        this.name = nam;
        this.email = mail;
        this.hotline = line;
        this.location = loc;

    }

    //getter setter methods
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
