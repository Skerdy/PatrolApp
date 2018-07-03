package com.barbarakoduzi.patrolapp.Models;

public class PerdoruesPolic extends Perdorues {
    private Polic polic;

    public PerdoruesPolic(String emer, String mbiemer, Integer rol, String idProfil, String email, Polic polic ){
        super(emer, mbiemer,rol,idProfil,email);
        this.polic = polic;
    }

    public Polic getPolic() {
        return polic;
    }

    public void setPolic(Polic polic) {
        this.polic = polic;
    }
}
