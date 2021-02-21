package com.ahitche.store.AhitcheStore.DB_Contract;

public class DB_ville {

    String nomville;
    int syncstatus;

    public DB_ville(String nomville, int syncstatus) {
        this.nomville = nomville;
        this.syncstatus = syncstatus;
    }

    public String getNomville() { return nomville; }

    public void setNomville(String nomville) { this.nomville = nomville; }

    public int getSyncstatus() {
        return syncstatus;
    }

    public void setSyncstatus(int syncstatus) {
        this.syncstatus = syncstatus;
    }
}
