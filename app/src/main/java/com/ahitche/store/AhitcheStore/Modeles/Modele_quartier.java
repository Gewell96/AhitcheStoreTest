package com.ahitche.store.AhitcheStore.Modeles;

public class Modele_quartier {
    String nomqtr;
    int idqtr,tarif,idville;

    public Modele_quartier(String nomqtr, int idqtr, int tarif, int idville) {
        this.nomqtr = nomqtr;
        this.idqtr = idqtr;
        this.tarif = tarif;
        this.idville = idville;
    }

    public String getNomqtr() {
        return nomqtr;
    }

    public void setNomqtr(String nomqtr) {
        this.nomqtr = nomqtr;
    }

    public int getIdqtr() {
        return idqtr;
    }

    public void setIdqtr(int idqtr) {
        this.idqtr = idqtr;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public int getIdville() {
        return idville;
    }

    public void setIdville(int idville) {
        this.idville = idville;
    }
}
