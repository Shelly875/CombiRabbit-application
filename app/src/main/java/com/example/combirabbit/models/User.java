package com.example.combirabbit.models;

import java.io.Serializable;

public class User implements Serializable {

    private String sName;
    private String sAge;
    private String sPhone;

    public User() {}

    public User(String newName, String newAge, String newPhone) {
        this.sName  = newName;
        this.sAge   = newAge;
        this.sPhone = newPhone;
    }

    public String getName() {
        return this.sName;
    }

    public void setName(String newName) {
        this.sName  = newName;
    }

    public String getAge() {
        return this.sAge;
    }

    public void setAge(String newAge) {
        this.sAge  = newAge;
    }

    public String getPhone() {
        return this.sPhone;
    }

    public void setPhone(String newPhone) {
        this.sPhone  = newPhone;
    }
}
