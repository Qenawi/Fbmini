package com.example.qenawi.fbmini.items;

import java.io.Serializable;

/**
 * Created by QEnawi on 5/13/2017.
 */

public class Post_Sample implements Serializable
{
    String id;

    public Post_Sample() {
    }

    public Post_Sample(String id, String date) {

        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    String date;
}
