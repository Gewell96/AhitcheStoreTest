package com.ahitche.store.AhitcheStore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Categorie.Categories;

public class Electromenager extends AppCompatActivity {


    RelativeLayout laycat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fgm_electromenager);

        laycat=findViewById(R.id.laypubvid);
laycat.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(Electromenager.this, "Enregistrement  effectué", Toast.LENGTH_LONG).show();
    }
});


                //Toast.makeText(Electromenager.this, "Enregistrement  effectué", Toast.LENGTH_LONG).show();
              //  Intent intent = new Intent(getApplicationContext(), Categories.class);
                //                startActivity(intent);
                //                finish();

    }
}
