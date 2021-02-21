package com.ahitche.store.AhitcheStore.Modeles;

public class Modele_Categorie {


    int idcat;
    String libcat;

    public Modele_Categorie(int idcat, String libcat) {
        this.idcat = idcat;
        this.libcat = libcat;

    }

    public int getIdcat() {
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public String getLibcat() {
        return libcat;
    }

    public void setLibcat(String libcat) {
        this.libcat = libcat;
    }
}
