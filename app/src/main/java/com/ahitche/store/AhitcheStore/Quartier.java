package com.ahitche.store.AhitcheStore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ahitche.store.AhitcheStore.Adapter.Adaptqtier;
import com.ahitche.store.AhitcheStore.Adapter.vilAdapter;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_ville;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Model_qtr;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quartier extends AppCompatActivity {
ListView listquartier;
DB_AHITCHE db;
FloatingActionButton fab;
    ArrayList<Model_qtr> arrayList;
    Adaptqtier adaptqtier;
    ProgressDialog loading;
    String nomqtr;
    int tarif, idville;
    ArrayList<String> arrayListville=new ArrayList<>();
    ArrayAdapter<String> adapterville;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quartier);
        listquartier=findViewById(R.id.listview_qtier);
        db=new DB_AHITCHE(this);
 
        fab=findViewById(R.id.Fab_qtier);

        arrayList=new ArrayList<>();
        adaptqtier=new Adaptqtier(this,arrayList);
        selectQuartier();
        listquartier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                Cursor cursor=(Cursor)listville.getItemAtPosition(i);
                //Recuperation de id, le nom ou description de la categorie cliquer
                // cursor.getString(cursor.getColumnIndexOrThrow("nomville"));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Quartier.this);
                dialog.setContentView(R.layout.dialog_quartier);
                final EditText nom_qtr=dialog.findViewById(R.id.dnomqtr);
                final EditText txt_tarif=dialog.findViewById(R.id.dtarif);
                final Spinner sp_idville=dialog.findViewById(R.id.sp_idville);
                Button btn_save=dialog.findViewById(R.id.btnqtrsave);
                //Debut Spinner from Mysql database

                StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_RECUP_VILLE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //converting the string to json array object
                                    loading.dismiss();
                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i < array.length(); i++) {

                                        //Recuperation des données de la bd MYSQL
                                        JSONObject quartier = array.getJSONObject(i);


                                       String villes= quartier.getInt("idville") +" "+ quartier.getString("nomville");
                                       arrayListville.add(villes);
                                       adapterville=new ArrayAdapter<>(Quartier.this, android.R.layout.simple_list_item_1,arrayListville);
                                       adapterville.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                       sp_idville.setAdapter(adapterville);
                                        // Toast.makeText(getApplicationContext(),"Enregistrement reussie qtier!",Toast.LENGTH_LONG).show();

                                    }

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
                Volley.newRequestQueue(Quartier.this).add(stringRequest);


                //Fin Spinner from Mysql database

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//////////////////////////////////////////////////////////////////////////////

                        nomqtr=nom_qtr.getText().toString();
                        tarif= Integer.parseInt(txt_tarif.getText().toString());

                        String recuptxt = sp_idville.getSelectedItem().toString();
                        String[] tabtxt = recuptxt.split(" ");
                       idville= Integer.parseInt(tabtxt[0]);
                       // Toast.makeText(Quartier.this,tabtxt[0],Toast.LENGTH_LONG).show();
                        serverqtr(nomqtr,tarif,idville);
                        // insert_categorie(v);
                        dialog.dismiss();
                        //recuplocal();
                        db.close();
                        /////////////////////// ///////////////////////////////////////////////////
                    }
                });
                dialog.show();
                // Toast.makeText(Liste_Categorie.this,"this FloatingAction button",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void serverqtr(final String nomqtr, final  int tarif, final  int idville){
        if (siconnection()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_INSERT_QUARTIER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String Response = jsonObject.getString("response");
                                if (Response.equals("ok")) {
                                    Toast.makeText(Quartier.this, "Enregistrement distant  ok", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Quartier.this, "Enregistrement failed", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Toast.makeText(Categories.this, response, Toast.LENGTH_LONG).show();
                            }
                            //  Toast.makeText(Categories.this, response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //db.insert_cat(libcat, DbContract.SYNC_STATUS_FAILED);
                    // Toast.makeText(Ville.this,error.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                    //  dialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nomqtr",nomqtr);
                    params.put("tarif", String.valueOf(tarif));
                    params.put("idville", String.valueOf(idville));
                    return params;
                }
            };
            Mysingleton.getInstance(Quartier.this).addToRequestQueue(stringRequest);

        }
    }

    private void selectQuartier() {
        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        if (siconnection()) {
            //String url = DbContract.SERVER_URL_SELECT_PRODUITT +1;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_SELECT_QUARTIER,
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
                                    JSONObject quartiers = array.getJSONObject(i);

                                    //adding the product to product list
                                    arrayList.add(new Model_qtr(
                                            quartiers.getString("nomqtr"),
                                            quartiers.getString("nomville"),
                                            quartiers.getInt("idqtr"),
                                            quartiers.getInt("tarif")

                                    ));
                                }

                                //creating adapter object and setting it to recyclerview
                                Adaptqtier adapter = new Adaptqtier(Quartier.this, arrayList);
                                listquartier.setAdapter(adapter);
                                //   CustomAdapter customAdapter=new CustomAdapter(Articles.this, arrayList);

                                //gridView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Toast.makeText(Ville.this,error.getMessage() , Toast.LENGTH_LONG).show();
                        }
                    });

            //adding our stringrequest to queue
            Volley.newRequestQueue(this).add(stringRequest);

        }



}

    public int split_str( String str){
        int txt=0;
        String recuptxt = str;
        String[] tabtxt = recuptxt.split(" ");
        txt= Integer.parseInt(tabtxt[0]);
        return txt;
    }
    public boolean siconnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    }

