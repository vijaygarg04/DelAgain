package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 03/04/18.
 */

public class SchemeModel {
    String imageurl;
    String description;
    String date;
    String title;
Boolean is_active;

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public SchemeModel() {
    }

    public SchemeModel(String imageurl, String description, String date,String title,Boolean is_active) {
        this.imageurl = imageurl;
        this.description = description;
        this.date = date;
        this.title=title;
        this.is_active=is_active;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
