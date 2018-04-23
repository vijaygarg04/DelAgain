package com.example.vijaygarg.delagain.userdata;

/**
 * Created by vijaygarg on 13/03/18.
 */

public class UserDetails {
    String admin_id,admin_name,admin_number,admin_password;

    public UserDetails() {
    }

    public UserDetails(String admin_id, String admin_name, String admin_number, String admin_password) {
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.admin_number = admin_number;
        this.admin_password = admin_password;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_number() {
        return admin_number;
    }

    public void setAdmin_number(String admin_number) {
        this.admin_number = admin_number;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }
}
