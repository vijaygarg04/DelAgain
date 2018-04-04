package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 23/03/18.
 */

public class PromoterModel {
    String name;
    String id;
    String contact;
    String date;
    String store;
    String password="default";
    public PromoterModel(String name, String id, String contact, String date, String store) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.date = date;
        this.store = store;
    }

    public PromoterModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
