package com.ahitche.store.AhitcheStore.Modeles;

public class Modele_ville {
    int idville;
    String nomville;

    public Modele_ville(int idville, String nomville) {
        this.idville = idville;
        this.nomville = nomville;
    }

    public int getIdville() {
        return idville;
    }

    public void setIdville(int idville) {
        this.idville = idville;
    }

    public String getNomville() {
        return nomville;
    }

    public void setNomville(String nomville) {
        this.nomville = nomville;
    }
}
