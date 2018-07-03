package com.barbarakoduzi.patrolapp.Models;

public class Shofer {

    private String pikePatente;

    private String targa;

    public Shofer(String pikePatente, String targa) {
        this.pikePatente = pikePatente;
        this.targa = targa;
    }

    public Shofer(){

    }

    public String getPikePatente() {
        return pikePatente;
    }

    public void setPikePatente(String pikePatente) {
        this.pikePatente = pikePatente;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public void zbritPiketEPatentesMe(Integer piketUlur){
        pikePatente = ""+ (Integer.parseInt(pikePatente) - piketUlur);
    }
}
