package com.barbarakoduzi.patrolapp.Models;

public class Perdorues {

    private String emer;
    private String mbiemer;
    private Integer rol;
    private String IdProfil;
    private String email;

    public Perdorues(String emer, String mbiemer, Integer rol, String idProfil, String email) {
        this.emer = emer;
        this.mbiemer = mbiemer;
        this.rol = rol;
        IdProfil = idProfil;
        this.email = email;
    }

    public Perdorues(Integer rol, String email) {
        this.rol = rol;
        this.email = email;
    }

    public String getEmer() {
        return emer;
    }

    public void setEmer(String emer) {
        this.emer = emer;
    }

    public String getMbiemer() {
        return mbiemer;
    }

    public void setMbiemer(String mbiemer) {
        this.mbiemer = mbiemer;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getIdProfil() {
        return IdProfil;
    }

    public void setIdProfil(String idProfil) {
        IdProfil = idProfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
