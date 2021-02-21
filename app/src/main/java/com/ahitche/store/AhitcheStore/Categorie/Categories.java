package com.ahitche.store.AhitcheStore.Categorie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Adapter.Myadapters;
import com.ahitche.store.AhitcheStore.DB_AHITCHE;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_categorie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Categorie;
import com.ahitche.store.AhitcheStore.Mysingleton;
import com.ahitche.store.AhitcheStore.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Categories extends AppCompatActivity {


    DB_AHITCHE db;
    FloatingActionButton fab_cat;
    ListView listcat;
    String libcat;
    ArrayList<String> listItem;
    ArrayList<DB_categorie> arrayList;
    Myadapters myadapters;
    Button btn_avatar;
    EditText lib_cat;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
Context contexte;
        listcat=findViewById(R.id.listview_cat);
        listItem=new ArrayList<>();
        fab_cat=findViewById(R.id.Fab_cat);
        arrayList=new ArrayList<>();
        db=new DB_AHITCHE(this);
        myadapters=new Myadapters(this,arrayList);



        listcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Cursor cursor=(Cursor)listcat.getItemAtPosition(i);
                //Recuperation de id, le nom ou description de la categorie cliquer
                cursor.getString(cursor.getColumnIndexOrThrow("libcat"));
            }
        });

        fab_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(Categories.this);
                dialog.setContentView(R.layout.dialogcat);
               final EditText lib_cat=dialog.findViewById(R.id.catname);
                Button btn_save=dialog.findViewById(R.id.catsave);



                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//////////////////////////////////////////////////////////////////////////////

                        libcat=lib_cat.getText().toString();
                        servercat(libcat);
                       // insert_categorie(v);
                                dialog.dismiss();
                                recuplocal();
                                db.close();
                        /////////////////////// ///////////////////////////////////////////////////
                    }
                });
                dialog.show();
                // Toast.makeText(Liste_Categorie.this,"this FloatingAction button",Toast.LENGTH_LONG).show();
            }
        });
        recuplocal();
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {

//if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//getting all the unsynced names
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Synchronisation des données...");
                progressDialog.show();
                Cursor cursor = db.getUnsyncecat();
                if (cursor.moveToFirst()) {
                    do {

                       libcat=cursor.getString(cursor.getColumnIndex("libcat"));
                       int idcat=cursor.getInt(cursor.getColumnIndex("idcat"));
                        servercat(libcat);
                        db.updateCatStatus(idcat,DbContract.SYNC_STATUS_OK);
                    } while (cursor.moveToNext());
                }
            }
        }
    }
    public void servercat(final String libecat){

  //  libecat=lib_cat.getText().toString();
        if (siconnection()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if (Response.equals("ok")) {
                            //if (db.silibcatexiste(libecat)>0) {
                            db.insert_cat(libecat, DbContract.SYNC_STATUS_OK);
                            //                            }
                            Toast.makeText(Categories.this, "Enregistrement distant ", Toast.LENGTH_LONG).show();

                            recuplocal();
                                              } else {
                            db.insert_cat(libecat, DbContract.SYNC_STATUS_FAILED);
                            Toast.makeText(Categories.this, "Enregistrement distant  failed", Toast.LENGTH_LONG).show();
                            //dialog.dismiss();
                            recuplocal();
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
                    Toast.makeText(Categories.this,error.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                    //  dialog.dismiss();
                    recuplocal();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("libcat",libcat);
                    return params;
                }
            };
            Mysingleton.getInstance(Categories.this).addToRequestQueue(stringRequest);

        } else {
            db.insert_cat(libcat, DbContract.SYNC_STATUS_FAILED);
            Toast.makeText(Categories.this, "Enregistrement  effectué", Toast.LENGTH_LONG).show();
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_article,menu);
        return true;
    }

    private void chargerCat() {
      //  arrayList=db.recupcatlocal();
       // myadapters=new Myadapters(this,arrayList);
        listcat.setAdapter(myadapters);
        myadapters.notifyDataSetChanged();
    }
public void insert_categorie(View view){
    libcat= lib_cat.getText().toString();

servercat(libcat);
lib_cat.setText("");


}
//private void  servercat(final String libcat){

//}

public void recuplocal()
{
    arrayList.clear();
    SQLiteDatabase database=db.getReadableDatabase();
    Cursor cursor=db.recupcatlocal(database);
    while (cursor.moveToNext())
    {
        String libcat=cursor.getString(cursor.getColumnIndex("libcat"));
        int synstatus=cursor.getInt(cursor.getColumnIndex("syncstatus"));
        arrayList.add(new DB_categorie(libcat,synstatus));
    }
    listcat.setAdapter(myadapters);
    myadapters.notifyDataSetChanged();
    cursor.close();
    db.close();
}

public boolean siconnection(){
    ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
return (networkInfo!=null && networkInfo.isConnected());
}

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver,new IntentFilter(DbContract.UI_UPDATE_BROADCAST));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
