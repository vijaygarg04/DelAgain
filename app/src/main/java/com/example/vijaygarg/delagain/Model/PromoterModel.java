package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 23/03/18.
 */

public class PromoterModel {
    String promoter_name ="default";
    String promoter_id="default";
    String promoter_contact="default";
    String date_of_joining="default";
    String store_id="default";
    String password="default";
    String store_name="default";
    boolean is_active=true;
    boolean logged_in =false;

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    public PromoterModel(String promoter_name, String promoter_id, String promoter_contact, String date_of_joining, String store_id) {
        this.promoter_name = promoter_name;
        this.promoter_id = promoter_id;
        this.promoter_contact = promoter_contact;
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


    public String getPromoter_name() {
        return promoter_name;
    }

    public void setPromoter_name(String promoter_name) {
        this.promoter_name = promoter_name;
    }

    public String getPromoter_contact() {
        return promoter_contact;
    }

    public void setPromoter_contact(String promoter_contact) {
        this.promoter_contact = promoter_contact;
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
