package com.ahitche.store.AhitcheStore.Modeles;

import android.graphics.Bitmap;

public class Modele_dealimg {
    Bitmap bitmap;

    public Modele_dealimg(Bitmap bitmap) {
        this.bitmap = bitmap;

    }

    public Bitmap getLienimg() {
        return bitmap;
    }

    public void setLienimg(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
