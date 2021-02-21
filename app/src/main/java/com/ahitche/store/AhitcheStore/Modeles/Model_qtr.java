package com.ahitche.store.AhitcheStore.Modeles;

public class Model_qtr {
    String nomqtr,nomvil;
    int idqtr,tarif;

    public Model_qtr(String nomqtr, String nomvil, int idqtr, int tarif) {
        this.nomqtr = nomqtr;
        this.nomvil = nomvil;
        this.idqtr = idqtr;
        this.tarif = tarif;
    }

    public String getNomqtr() {
        return nomqtr;
    }

    public void setNomqtr(String nomqtr) {
        this.nomqtr = nomqtr;
    }

    public String getNomvil() {
        return nomvil;
    }

    public void setNomvil(String nomvil) {
        this.nomvil = nomvil;
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
}
