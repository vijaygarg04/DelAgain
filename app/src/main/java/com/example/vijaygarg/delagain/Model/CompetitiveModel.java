package com.example.vijaygarg.delagain.Model;

/**
 * Created by vijaygarg on 04/04/18.
 */

public class CompetitiveModel {
    String hp,lenovo,acer,other;

    public CompetitiveModel() {
    }

    public CompetitiveModel(String hp, String lenovo, String acer, String other) {
        this.hp = hp;
        this.lenovo = lenovo;
        this.acer = acer;
        this.other = other;
    }

    public String getHp() {
        return hp;
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
