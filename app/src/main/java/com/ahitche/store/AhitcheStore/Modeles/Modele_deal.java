package com.ahitche.store.AhitcheStore.Modeles;

public class Modele_deal {

   String titdeal,imgdeal,datedeal,catdeal,locdeal;
    int idclt, iddeal,prixdeal;

    public Modele_deal(String titdeal, String imgdeal, String datedeal, String catdeal, String locdeal, int prixdeal, int idclt,int iddeal) {
        this.titdeal = titdeal;
        this.imgdeal = imgdeal;
        this.datedeal = datedeal;
        this.catdeal = catdeal;
        this.locdeal = locdeal;
        this.prixdeal=prixdeal;
        this.idclt = idclt;
        this.iddeal=iddeal;
    }

    public String getTitdeal() {
        return titdeal;
    }

    public void setTitdeal(String descdeal) {
        this.titdeal = descdeal;
    }

    public String getImgdeal() {
        return imgdeal;
    }

    public void setImgdeal(String imgdeal) {
        this.imgdeal = imgdeal;
    }

    public String getDatedeal() {
        return datedeal;
    }

    public void setDatedeal(String datedeal) {
        this.datedeal = datedeal;
    }

    public String getCatdeal() {
        return catdeal;
    }

    public void setCatdeal(String catdeal) {
        this.catdeal = catdeal;
    }

    public String getLocdeal() {
        return locdeal;
    }

    public void setLocdeal(String locdeal) {
        this.locdeal = locdeal;
    }

    public int getIdclt() {
        return idclt;
    }

    public void setIdclt(int idclt) {
        this.idclt = idclt;
    }

    public int getIddeal(){return  iddeal;}
    public void setIddeal(int iddeal){this.iddeal=iddeal;}

    public void setPrixdeal(int prixdeal) {
        this.prixdeal = prixdeal;
    }
    public int getPrixdeal(){return prixdeal;}
}
