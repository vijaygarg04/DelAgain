package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 23/04/18.
 */

public class StoreModel {
    String store_name;
    String store_id;
    Boolean is_store_active=true;

    public StoreModel(String store_name, String store_id) {
        this.store_name = store_name;
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public Boolean getIs_store_active() {
        return is_store_active;
    }

    public void setIs_store_active(Boolean is_store_active) {
        this.is_store_active = is_store_active;
    }
}
