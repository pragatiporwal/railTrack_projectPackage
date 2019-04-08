package com.example.dell.railtrack;

public class Two_database {
    String name ;
    String email;
    String pass;
    String cpass;
    String phone;

    public Two_database()
    {

    }
    public Two_database(String name, String email, String pass, String cpass, String phone) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.cpass = cpass;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getCpass() {
        return cpass;
    }

    public String getPhone() {
        return phone;
    }
}
