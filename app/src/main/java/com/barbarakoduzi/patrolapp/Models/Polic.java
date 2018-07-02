package com.barbarakoduzi.patrolapp.Models;

public class Polic {
    private String titulli;
    private String grada;

    public Polic(String titulli, String grada) {
        this.titulli = titulli;
        this.grada = grada;
    }

    public String getTitulli() {
        return titulli;
    }

    public void setTitulli(String titulli) {
        this.titulli = titulli;
    }

    public String getGrada() {
        return grada;
    }

    public void setGrada(String grada) {
        this.grada = grada;
    }
}
