package com.ahitche.store.AhitcheStore.Articles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Adapter.AdaptPatis;
import com.ahitche.store.AhitcheStore.DB_AHITCHE;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.ItemClickSupport;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.Panier;
import com.ahitche.store.AhitcheStore.R;
import com.ahitche.store.AhitcheStore.RecyclerviewClickInterface;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Patisserie extends AppCompatActivity {

    RecyclerView list_patis;
    ArrayList<Modele_Article> arrayList;
    DB_AHITCHE db;
    FloatingActionButton fabpat;
    Cursor c;
    int cptpan, pancours,idmax;
    List<AdaptPatis> productList;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patisserie);
//try {

        db=new DB_AHITCHE(this);
        arrayList=new ArrayList<Modele_Article>();
        list_patis=findViewById(R.id.list_patis);
        fabpat=findViewById(R.id.Fab_patis);
        chargerArt();
        pancours=db.panClientencours(1);
        cptpan=db.cptelmtpanier(pancours);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Patisserie.this);
        list_patis.setLayoutManager(layoutManager);
        productList=new ArrayList<>();
        arrayList = new ArrayList<>();
        selectLavelinge();
        idmax=db.selmaxpanier(1);
        Toast.makeText(Patisserie.this, String.valueOf(idmax), Toast.LENGTH_SHORT).show();
        fabpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Patisserie.this,Article_insert.class);
                intent.putExtra("fen","2");
                startActivity(intent);

            }
        });
   // } catch (Exception e) {
    //        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    //
    //    }
    }

    private void chargerArt() {
        c=db.getAllPatisserie(2);

        if (c.getCount()>0){

            if (c.moveToFirst()){

                do {
                    int idpat=c.getInt(3);
                    String descpat=c.getString(0);
                    int prixprod=c.getInt(1);
                    String imgprod=c.getString(2);


                    Modele_Article modele_article=new Modele_Article(prixprod, idpat,descpat,imgprod);

                    arrayList.add(modele_article);
                }while (c.moveToNext());
            }
        }
        AdaptPatis pa=new AdaptPatis(Patisserie.this,arrayList);
        list_patis.setAdapter(pa);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //  int cptpan, pancours;
        //pancours=db.panClientencours(1);
        // cptpan=db.cptelmtpanier(pancours);
        getMenuInflater().inflate(R.menu.menu_panier,menu);
        MenuItem item=menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item,R.layout.action_bar_notification_icon);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        Button tv = (Button) notifCount.findViewById(R.id.actionbar_notifcation_button);
        tv.setText(String.valueOf(cptpan));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Patisserie.this, Panier.class);
                intent.putExtra("idpan",idmax);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void selectLavelinge() {

        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);

        String url = DbContract.SERVER_URL_SELECT_PRODUITT +1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_URL_SELECT_PRODUITT+"7",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            loading.dismiss();
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                arrayList.add(new Modele_Article(
                                        product.getInt("prixprod"),
                                        product.getInt("idprod"),
                                        product.getString("deprod"),
                                        product.getString("imgprod")
                                ));
                                db.insert_Produit(product.getInt("idprod"),product.getString("deprod"),product.getInt("prixprod"),product.getString("imgprod"),product.getString("poidprod"),product.getString("dimprod"),product.getInt("idpart"),DbContract.SYNC_STATUS_OK);
                                //Toast.makeText(getApplicationContext(), "enregistrement effectué", Toast.LENGTH_LONG).show();
                            }

                            //creating adapter object and setting it to recyclerview
                            AdaptPatis adapter = new AdaptPatis(Patisserie.this, arrayList);
                            // recyclerView.setAdapter(adapter);
                          //  AdaptPatis adaptPatis=new AdaptPatis(Diverselectro.this, arrayList);

                            list_patis.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
