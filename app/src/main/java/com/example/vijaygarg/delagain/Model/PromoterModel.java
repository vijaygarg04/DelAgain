package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 23/03/18.
 */

public class PromoterModel {
    String promoter_name ="default";
    String promoter_id="default";
    String contact="default";
    String date_of_joining="default";
    String store_id="default";
    String password="default";
    String store_name="default";
    public PromoterModel(String name, String id, String contact, String date_of_joining, String store_id) {
        this.promoter_name = name;
        this.promoter_id = id;
        this.contact = contact;
        this.date_of_joining = date_of_joining;
        this.store_id = store_id;
    }

    public String getPromoter_id() {
        return promoter_id;
    }

    public void setPromoter_id(String promoter_id) {
        this.promoter_id = promoter_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public PromoterModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return promoter_name;
    }

    public void setName(String name) {
        this.promoter_name = name;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}
