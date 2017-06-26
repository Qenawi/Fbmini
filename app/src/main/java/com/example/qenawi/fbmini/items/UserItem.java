package com.example.qenawi.fbmini.items;

/**
 * Created by QEnawi on 5/13/2017.
 */

public class UserItem {
    private String key;
    private String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    private String pass;

    public UserItem()
    {
    }

    public UserItem(String key, String name, String pass) {
        this.key = key;
        this.name = name;
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
}
