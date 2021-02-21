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
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.Panier;
import com.ahitche.store.AhitcheStore.R;
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

public class Metbenin extends AppCompatActivity {

    RecyclerView list_metben;
    ArrayList<Modele_Article> arrayList;
    DB_AHITCHE db;
    FloatingActionButton fabmetben;
    Cursor c;
    int cptpan, pancours;
    List<AdaptPatis> productList;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metbenin);

        db=new DB_AHITCHE(this);
        arrayList=new ArrayList<Modele_Article>();
        list_metben=findViewById(R.id.list_metben);
        fabmetben=findViewById(R.id.Fab_metben);
        // chargerArt();
        pancours=db.panClientencours(1);
        cptpan=db.cptelmtpanier(pancours);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Metbenin.this);
        list_metben.setLayoutManager(layoutManager);
        // this.configureOnClickRecyclerView();
        c=db.getAllPatisserie(2);
        final int idmax=db.selmaxpanier(1);
        Toast.makeText(Metbenin.this, String.valueOf(idmax), Toast.LENGTH_SHORT).show();

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
        AdaptPatis pa=new AdaptPatis(Metbenin.this,arrayList);
        list_metben.setAdapter(pa);
        fabmetben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Metbenin.this,Article_insert.class);
                intent.putExtra("fen","2");
                startActivity(intent);

            }
        });
        productList=new ArrayList<>();
        arrayList = new ArrayList<>();
        selectLavelinge();
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
                Intent intent=new Intent(Metbenin.this, Panier.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void selectLavelinge() {
        // int id = editTextId.getText().toString().trim();
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);

        String url = DbContract.SERVER_URL_SELECT_PRODUITT +1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_URL_SELECT_PRODUITT+"11",
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
                            }

                            //creating adapter object and setting it to recyclerview
                            AdaptPatis adapter = new AdaptPatis(Metbenin.this, arrayList);
                            // recyclerView.setAdapter(adapter);
                            //  AdaptPatis adaptPatis=new AdaptPatis(Diverselectro.this, arrayList);

                            list_metben.setAdapter(adapter);
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
