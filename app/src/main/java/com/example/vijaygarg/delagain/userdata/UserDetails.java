package com.example.vijaygarg.delagain.userdata;

/**
 * Created by vijaygarg on 13/03/18.
 */

public class UserDetails {
    String uname,name,number,store;

    public UserDetails() {
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public UserDetails(String uname, String name, String number, String store) {

        this.uname = uname;
        this.name = name;
        this.number = number;
        this.store = store;
    }
}
