package com.example.qenawi.fbmini.items;

import java.io.Serializable;

/**
 * Created by QEnawi on 5/14/2017.
 */

public class POST implements Serializable {
    private   String date;
    private String mail;
    private String photo_url;
    public String getDate() {
        return date;
    }
    public String getMail() {
        return mail;
    }
    public String getPhoto_url() {
        return photo_url;
    }

    public String getText() {
        return text;
    }

    public String getId()
    {
        return id;
    }

    String text;

    public POST(String date, String mail, String photo_url, String text)
    {
        this.date = date;
        this.mail = mail;
        this.photo_url = photo_url;
        this.text = text;
        this.id=date;
    }

    public POST() {
    }

    public void setId(String id) {

        this.id = id;
    }

    private String id;
}
