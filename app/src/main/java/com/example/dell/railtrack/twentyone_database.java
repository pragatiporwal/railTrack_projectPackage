package com.example.dell.railtrack;

public class twentyone_database {
    String sname_of_restaurant;
    String susername;
    String semail;
    String spassword;
    String sphone;
    String saddress;
    String scity;

    public twentyone_database() {

    }

    public twentyone_database(String sname_of_restaurant, String susername, String semail, String spassword, String sphone, String saddress, String scity) {
        this.sname_of_restaurant = sname_of_restaurant;
        this.susername = susername;
        this.semail = semail;
        this.spassword = spassword;
        this.sphone = sphone;
        this.saddress = saddress;
        this.scity = scity;
    }

    public String getSname_of_rest() {
        return sname_of_restaurant;
    }

    public String getSusername() {
        return susername;
    }

    public String getSemail() { return semail; }

    public String getSpassword() {
        return spassword;
    }

    public String getSphone() {
        return sphone;
    }

    public String getSaddress() {
        return saddress;
    }

    public String getScity() {
        return scity;
    }

}
