package com.example.vijaygarg.delagain.Model;

public class DisplayModel {
    String request_result="default";
    boolean request_active=true;
    String store_name;
    String store_id;
    String service_tag;
    String model_number;
    boolean is_sold_out=false;

    public DisplayModel() {
    }

    public DisplayModel(String request_result, boolean request_active, String store_name, String store_id, String service_tag,String model_number) {
        this.request_result = request_result;
        this.request_active = request_active;
        this.store_name = store_name;
        this.store_id = store_id;
        this.service_tag = service_tag;
        this.model_number=model_number;
    }

    public boolean getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(boolean is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }

    public String getService_tag() {
        return service_tag;
    }

    public void setService_tag(String service_tag) {
        this.service_tag = service_tag;
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


    public String getRequest_result() {
        return request_result;
    }

    public void setRequest_result(String request_result) {
        this.request_result = request_result;
    }

    public boolean isRequest_active() {
        return request_active;
    }

    public void setRequest_active(boolean request_active) {
        this.request_active = request_active;
    }
}
