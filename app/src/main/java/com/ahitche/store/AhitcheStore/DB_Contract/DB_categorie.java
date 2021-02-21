package com.ahitche.store.AhitcheStore.DB_Contract;

public class DB_categorie {
    private  String libcat;
    private int sync_status;

    public DB_categorie(String libcat, int sync_status){
        this.setLibcat(libcat);
        this.setSync_status(sync_status);
    }

    public String getLibcat() {
        return libcat;
    }

    public void setLibcat(String libcat) {
        this.libcat = libcat;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }
}
