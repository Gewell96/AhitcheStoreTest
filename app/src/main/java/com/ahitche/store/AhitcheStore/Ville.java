package com.ahitche.store.AhitcheStore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahitche.store.AhitcheStore.Adapter.vilAdapter;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_ville;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class Ville extends AppCompatActivity {
DB_AHITCHE db;
ListView listville;
FloatingActionButton fab;
Dialog dialog;
    ProgressDialog loading;
    String nomvil;
    ArrayList<DB_ville> arrayList;
    vilAdapter viladapter;

    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ville);

        db=new DB_AHITCHE(this);
        listville=findViewById(R.id.listview_ville);
        fab=findViewById(R.id.Fab_ville);

        Context contexte;
        arrayList=new ArrayList<>();
        viladapter=new vilAdapter(this,arrayList);
        selectVilles();
        listville.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                final Dialog dialog=new Dialog(Ville.this);
                dialog.setContentView(R.layout.dialog_ville);
                final EditText nom_ville=dialog.findViewById(R.id.nomville);
                Button btn_save=dialog.findViewById(R.id.vilsave);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//////////////////////////////////////////////////////////////////////////////

                       nomvil =nom_ville.getText().toString();
                     servervil(nomvil);
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

public void servervil(final String nomvil){
    if (siconnection()) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_INSERT_VILLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("response");
                            if (Response.equals("ok")) {
                                Toast.makeText(Ville.this, "Enregistrement distant  ok", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Ville.this, "Enregistrement failed", Toast.LENGTH_LONG).show();
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
                params.put("nomville",nomvil);
                return params;
            }
        };
        Mysingleton.getInstance(Ville.this).addToRequestQueue(stringRequest);

    }
    }
    private void selectVilles() {
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //String url = DbContract.SERVER_URL_SELECT_PRODUITT +1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_SELECT_VILLE,
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
                                JSONObject villes = array.getJSONObject(i);

                                //adding the product to product list
                                arrayList.add(new DB_ville(
                                        villes.getString("nomville"),
                                        villes.getInt("idville")

                                ));
                                  }

                            //creating adapter object and setting it to recyclerview
                            vilAdapter adapter = new vilAdapter(Ville.this, arrayList);
                          listville.setAdapter(adapter);
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


    public boolean siconnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
