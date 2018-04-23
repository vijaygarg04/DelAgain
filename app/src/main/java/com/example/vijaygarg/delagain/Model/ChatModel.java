package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 23/04/18.
 */

public class ChatModel {

    public String sent_by_id;
    public String message;
    public String sent_by_name;
    public boolean sent_by_admin= false;

    public ChatModel() {
        // Firebase
    }

    public String getSent_by_id() {
        return sent_by_id;
    }

    public void setSent_by_id(String sent_by_id) {
        this.sent_by_id = sent_by_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSent_by_name() {
        return sent_by_name;
    }

    public void setSent_by_name(String sent_by_name) {
        this.sent_by_name = sent_by_name;
    }

    public boolean isSent_by_admin() {
        return sent_by_admin;
    }

    public void setSent_by_admin(boolean sent_by_admin) {
        this.sent_by_admin = sent_by_admin;
    }
}
