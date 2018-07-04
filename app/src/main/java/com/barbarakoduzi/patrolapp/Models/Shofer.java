package com.barbarakoduzi.patrolapp.Models;

public class Shofer {

    private String pikePatente;

    private String targa;

    private String fcm;

    private String vleraPara;

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

    public void zbritVlerenBankareTeShoferitMe(Integer vlera){
        vleraPara = ""+ (Integer.parseInt(vleraPara) - vlera);
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getVleraPara() {
        return vleraPara;
    }

    public boolean mundTePaguajeGjoben(Integer vlera){
        return Integer.parseInt(vleraPara) > vlera;
    }

    public void setVleraPara(String vleraPara) {
        this.vleraPara = vleraPara;
    }
}
