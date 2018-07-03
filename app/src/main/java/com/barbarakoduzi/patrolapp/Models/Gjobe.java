package com.barbarakoduzi.patrolapp.Models;

import java.util.Date;

public class Gjobe {

    private String idPolic;
    private String idShofer;
    private String lloji;
    private String piketUlura;
    private String arsyeja;
    private String vlera;
    private Date dataVenies;
    private Date afatiPerfundimtar;
    private String targa;
    private boolean paguar;

    public Gjobe(){

    }

    public Gjobe(String idPolic, String idShofer, String lloji, String piketUlura, String arsyeja, String vlera, Date dataVenies, Date afatiPerfundimtar, String targa, boolean paguar) {
        this.idPolic = idPolic;
        this.idShofer = idShofer;
        this.lloji = lloji;
        this.piketUlura = piketUlura;
        this.arsyeja = arsyeja;
        this.vlera = vlera;
        this.dataVenies = dataVenies;
        this.afatiPerfundimtar = afatiPerfundimtar;
        this.targa = targa;
        this.paguar = paguar;
    }

    public String getIdPolic() {
        return idPolic;
    }

    public void setIdPolic(String idPolic) {
        this.idPolic = idPolic;
    }

    public String getIdShofer() {
        return idShofer;
    }

    public void setIdShofer(String idShofer) {
        this.idShofer = idShofer;
    }

    public String getLloji() {
        return lloji;
    }

    public void setLloji(String lloji) {
        this.lloji = lloji;
    }

    public String getPiketUlura() {
        return piketUlura;
    }

    public void setPiketUlura(String piketUlura) {
        this.piketUlura = piketUlura;
    }

    public String getVlera() {
        return vlera;
    }

    public void setVlera(String vlera) {
        this.vlera = vlera;
    }

    public Date getDataVenies() {
        return dataVenies;
    }

    public void setDataVenies(Date dataVenies) {
        this.dataVenies = dataVenies;
    }

    public Date getAfatiPerfundimtar() {
        return afatiPerfundimtar;
    }

    public void setAfatiPerfundimtar(Date afatiPerfundimtar) {
        this.afatiPerfundimtar = afatiPerfundimtar;
    }

    public boolean isPaguar() {
        return paguar;
    }

    public void setPaguar(boolean paguar) {
        this.paguar = paguar;
    }

    public String getArsyeja() {
        return arsyeja;
    }

    public void setArsyeja(String arsyeja) {
        this.arsyeja = arsyeja;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
}
