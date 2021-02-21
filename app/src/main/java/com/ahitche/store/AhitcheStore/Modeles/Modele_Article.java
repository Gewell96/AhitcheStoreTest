package com.ahitche.store.AhitcheStore.Modeles;

public class Modele_Article {

    int prixprod,idprod;
    String deprod, imgprod;

    public Modele_Article(int prixprod, int idprod, String deprod, String imgprod) {
        this.prixprod = prixprod;
        this.idprod = idprod;
        this.deprod = deprod;
        this.imgprod = imgprod;
    }

    public int getPrixprod() {
        return prixprod;
    }

    public void setPrixprod(int prixprod) {
        this.prixprod = prixprod;
    }

    public int getIdprod() {
        return idprod;
    }

    public void setIdprod(int idprod) {
        this.idprod = idprod;
    }

    public String getDeprod() {
        return deprod;
    }

    public void setDeprod(String deprod) {
        this.deprod = deprod;
    }

    public String getImgprod() {
        return imgprod;
    }

    public void setImgprod(String imgprod) {
        this.imgprod = imgprod;
    }
}
