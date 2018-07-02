package com.barbarakoduzi.patrolapp.Models;

public class PerdoruesShofer extends Perdorues {
    private Shofer shofer;

    public PerdoruesShofer(String emer, String mbiemer, Integer rol, String profileId, String email, Shofer shofer){
        super(emer, mbiemer,rol,profileId,email);
        this.shofer = shofer;
    }

    public Shofer getShofer() {
        return shofer;
    }

    public void setShofer(Shofer shofer) {
        this.shofer = shofer;
    }
}
