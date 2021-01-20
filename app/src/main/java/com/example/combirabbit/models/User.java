package com.example.combirabbit.models;

import java.io.Serializable;

public class User implements Serializable {

    private String sName;
    private String sAge;
    private String sPhone;

    public User() {}

    public User(String newName, String newAge, String newEmail) {
        this.sName  = newName;
        this.sPhone = newEmail;
        this.sAge   = newAge;
    }

    public String getName() {
        return sName;
    }

    public void setName(String newName) {
        this.sName  = newName;
    }

    public String getAge() {
        return sAge;
    }

    public void setAge(String newAge) {
        this.sAge  = newAge;
    }

    public String getPhone() {
        return sPhone;
    }

    public void setPhone(String newEmail) {
        this.sPhone  = newEmail;
    }
}
