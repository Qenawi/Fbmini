package com.example.qenawi.fbmini.items;

import java.util.ArrayList;

/**
 * Created by ahmed on 4/27/2017.
 */

public class User {
    String  name, email, password, age, address, mobile;
    ArrayList <String> follower;


    public User()
    {
    }
    public User(String name, String email, String password, String age, String address, String mobile, ArrayList <String> follower) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
        this.mobile = mobile;
        this.follower=follower;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}

