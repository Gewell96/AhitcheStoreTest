package com.ahitche.store.AhitcheStore.Modeles;

public class Model_panier {
    int  idpan,idprod,prixpan,qtdmd;
   String deprod,imgprod,poidprod;

   public Model_panier(int idpan, int idprod,String deprod,String imgprod,int prixpan,int qtdmd,String poidprod){
    this.idpan = idpan;
        this.idprod = idprod;
        this.deprod = deprod;
        this.imgprod = imgprod;
        this.prixpan=prixpan;
        this.qtdmd=qtdmd;
        this.poidprod=poidprod;
}

    public String getPoidprod() {
        return poidprod;
    }

    public void setPoidprod(String poidprod) {
        this.poidprod = poidprod;
    }

    public int getIdpan() {
        return idpan;
    }

    public void setIdpan(int idpan) {
        this.idpan = idpan;
    }

    public int getIdprod() {
        return idprod;
    }

    public void setIdprod(int idprod) {
        this.idprod = idprod;
    }

    public int getPrixpan() {
        return prixpan;
    }

    public void setPrixpan(int prixpan) {
        this.prixpan = prixpan;
    }

    public int getQtdmd() {
        return qtdmd;
    }

    public void setQtdmd(int qtdmd) {
        this.qtdmd = qtdmd;
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
