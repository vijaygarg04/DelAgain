package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 04/04/18.
 */

public class CompetitiveModel {
    String hp,lenovo,acer,other,dell,storename;

    public CompetitiveModel() {
    }

    public CompetitiveModel(String hp, String lenovo, String acer, String other,String dell,String storename) {
        this.hp = hp;
        this.lenovo = lenovo;
        this.acer = acer;
        this.dell=dell;
        this.other = other;
        this.storename=storename;

    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getHp() {
        return hp;
    }

    public String getDell() {
        return dell;
    }

    public void setDell(String dell) {
        this.dell = dell;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getLenovo() {
        return lenovo;
    }

    public void setLenovo(String lenovo) {
        this.lenovo = lenovo;
    }

    public String getAcer() {
        return acer;
    }

    public void setAcer(String acer) {
        this.acer = acer;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
