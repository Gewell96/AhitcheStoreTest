package com.ahitche.store.AhitcheStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Adapter.AdaptPatis;
import com.ahitche.store.AhitcheStore.Adapter.AdaptQuartier;
import com.ahitche.store.AhitcheStore.Adapter.AdaptVille;
import com.ahitche.store.AhitcheStore.Adapter.Adaptpanier;
import com.ahitche.store.AhitcheStore.Articles.Patisserie;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_categorie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Model_panier;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.Modeles.Modele_quartier;
import com.ahitche.store.AhitcheStore.Modeles.Modele_ville;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.WatchKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Panier extends AppCompatActivity {
    RecyclerView list_panier;
    ArrayList<Model_panier> arrayList;
    DB_AHITCHE db;
    Cursor c;
    List<Adaptpanier> panierList;
    AdaptVille adaptVille;
    AdaptQuartier adaptQuartier;
    Button btn_cmder;
    int cptpan,idpan;
    TextView totpanier,nap_panier,transport,txt_idqtr, txt_tarifqtr, txt_idvilqtr,poidtotal;;
    int nap;
    ListView listville,listqtr;
    ArrayList<Modele_ville> arrayListville;
    ArrayList<Modele_quartier> arrayListqtier;
    ArrayList<String> listItem;
    EditText et_ville,et_qtier;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panier);
        Intent intent = getIntent();
        idpan = intent.getIntExtra("idpan", 0);
        db = new DB_AHITCHE(this);
        listville = findViewById(R.id.villelist);
        arrayListville = new ArrayList<>();
        arrayListqtier=new ArrayList<>();
        et_ville = findViewById(R.id.villiv);
        et_qtier = findViewById(R.id.qtrliv);
        txt_idqtr=findViewById(R.id.idqtr);
        txt_tarifqtr=findViewById(R.id.tarifqtr);
        txt_idvilqtr=findViewById(R.id.idvilleqtr);


        poidtotal=findViewById(R.id.poidstot);
        adaptVille = new AdaptVille(this, arrayListville);
        adaptQuartier=new AdaptQuartier(this,arrayListqtier);
        listItem = new ArrayList<>();
        listqtr = findViewById(R.id.qtrlist);
        listville.setVisibility(listville.GONE);
        listqtr.setVisibility(listqtr.GONE);
        arrayList = new ArrayList<Model_panier>();
        list_panier = findViewById(R.id.list_panier);
        totpanier = findViewById(R.id.totalpan);
        nap_panier = findViewById(R.id.nappan);
        transport = findViewById(R.id.mttransport);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Panier.this);
        list_panier.setLayoutManager(layoutManager);
        panierList = new ArrayList<>();
        arrayList = new ArrayList<>();
        //Toast.makeText(Panier.this, String.valueOf(idpan), Toast.LENGTH_SHORT).show();
        chargerpanier();
        selectVille();


        totpanier.setText(String.valueOf(db.getTotPanier(idpan)) + " FCFA");
        nap = split_txt(totpanier) + split_txt(transport);
        nap_panier.setText(String.valueOf(nap) + " FCFA");
        btn_cmder = findViewById(R.id.btncmder);
        recuplocal();

        btn_cmder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paniercltencours();
            }
        });
      //  et_ville.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        //            @Override
        //            public void onFocusChange(View v, boolean hasFocus) {
        //            }
        //        });
        listqtr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vidqtier=(TextView)view.findViewById(R.id.idqtrtxt);
                TextView vnomqtier=(TextView)view.findViewById(R.id.nomqtiertxt);
                TextView vtarif=(TextView)view.findViewById(R.id.tariftxt);
                TextView vidville=(TextView)view.findViewById(R.id.idvilletxt);

                int id_qtier= Integer.parseInt(vidqtier.getText().toString());
                String nom_qtier=vnomqtier.getText().toString();
                int v_tarif= Integer.parseInt(vtarif.getText().toString());
                int id_ville= Integer.parseInt(vidville.getText().toString());

              txt_idqtr.setText(Integer.toString(id_qtier));
                et_qtier.setText(nom_qtier);
              txt_tarifqtr.setText(Integer.toString(v_tarif));
            txt_idvilqtr.setText(Integer.toString(id_ville));
                listqtr.setVisibility(listville.GONE);
            int tarifc= Integer.parseInt(txt_tarifqtr.getText().toString());
             int  poids =Integer.parseInt(poidtotal.getText().toString());
             if (poids<=10){
                 transport.setText(tarifc+" FCFA");
                 nap = split_txt(totpanier) + split_txt(transport);
                 nap_panier.setText(String.valueOf(nap) + " FCFA");
             }else {
                 if (poids <= 20) {
                     transport.setText(tarifc * 1.5 + " FCFA");
                     nap = split_txt(totpanier) + split_txt(transport);
                     nap_panier.setText(String.valueOf(nap) + " FCFA");
                 }else{
                     if (poids <= 30){
                         transport.setText(tarifc * 2 + " FCFA");
                         nap = split_txt(totpanier) + split_txt(transport);
                         nap_panier.setText(String.valueOf(nap) + " FCFA");
                     }else{
                         if (poids <= 40){
                             transport.setText(tarifc * 2.5 + " FCFA");
                             nap = split_txt(totpanier) + split_txt(transport);
                             nap_panier.setText(String.valueOf(nap) + " FCFA");
                         }else{
                             if (poids <= 50){
                                 transport.setText(tarifc * 3+ " FCFA");
                                 nap = split_txt(totpanier) + split_txt(transport);
                                 nap_panier.setText(String.valueOf(nap) + " FCFA");
                             }else{
                                 if (poids <= 60){
                                     transport.setText(tarifc * 3.5 + " FCFA");
                                     nap = split_txt(totpanier) + split_txt(transport);
                                     nap_panier.setText(String.valueOf(nap) + " FCFA");
                                 }else{
                                     if (poids <= 70){
                                         transport.setText(tarifc * 4 + " FCFA");
                                         nap = split_txt(totpanier) + split_txt(transport);
                                         nap_panier.setText(String.valueOf(nap) + " FCFA");
                                     }else{
                                         if (poids <= 80){
                                             transport.setText(tarifc * 4.5 + " FCFA");
                                             nap = split_txt(totpanier) + split_txt(transport);
                                             nap_panier.setText(String.valueOf(nap) + " FCFA");
                                         }else{
                                             if (poids <= 90){
                                                 transport.setText(tarifc * 5 + " FCFA");
                                                 nap = split_txt(totpanier) + split_txt(transport);
                                                 nap_panier.setText(String.valueOf(nap) + " FCFA");
                                             }else{
                                                 if (poids <= 100){
                                                     transport.setText(tarifc * 5.5 + " FCFA");
                                                     nap = split_txt(totpanier) + split_txt(transport);
                                                     nap_panier.setText(String.valueOf(nap) + " FCFA");
                                                 }
                                             }
                                         }
                                     }
                                 }
                             }
                         }
                     }
                 }
             }}
        });
        listville.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vnomville=(TextView)view.findViewById(R.id.nomvilletxt);
                TextView vidville=(TextView)view.findViewById(R.id.idvil);
                String nom_ville=vnomville.getText().toString();
                et_ville.setText(nom_ville);
                txt_idvilqtr.setText(vidville.getText().toString());
                listville.setVisibility(listville.GONE);
                selectQuartier();
            }
        });

        et_ville.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") ) {
                    listville.setVisibility(listville.VISIBLE);
                    recuplocal();
                }
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        et_qtier.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") ) {
                    listqtr.setVisibility(listqtr.VISIBLE);
                   // Toast.makeText(Panier.this,txt_idvilqtr.getText().toString(),Toast.LENGTH_LONG).show();
                    recuplocalqtier(Integer.parseInt(txt_idvilqtr.getText().toString()));
                    //txt_idvilqtr.getText().toString()
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void chargerpanier() {
        c=db.getAllPanier(1);// a creer dans bd
        int poids;
        if (c.getCount()>0){

            if (c.moveToFirst()){

                do {

                    int idpan=c.getInt(0);
                    int idprod=c.getInt(1);
                    String deprod=c.getString(2);
                    String imgprod=c.getString(3);
                    int prixpan=c.getInt(4);
                    int qtdmd=c.getInt(5);
                    String vpoidprod=c.getString(6);

               //  Toast.makeText(Panier.this,imgprod, Toast.LENGTH_SHORT).show();
                    Model_panier model_panier=new Model_panier( idpan, idprod,deprod,imgprod,prixpan,qtdmd,vpoidprod);
                     poids=+split_str(vpoidprod);
                    arrayList.add(model_panier);
                }while (c.moveToNext());
             poidtotal.setText(Integer.toString(poids));
            }
        }
        Adaptpanier pa=new Adaptpanier(Panier.this,arrayList);
        list_panier.setAdapter(pa);

    }

  /*  public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panier,menu);
        MenuItem item=menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item,R.layout.action_bar_notification_icon);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        Button tv = (Button) notifCount.findViewById(R.id.actionbar_notifcation_button);
        tv.setText(String.valueOf(cptpan));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Panier.this, Panier.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
*/
    public void serverPannier(final int idprod, final int idpan, final int qtdmd, final int prixpan, final int mtlpan, final int syncstatus, final Date datpan){
        ///////
        //  libecat=lib_cat.getText().toString();
        if (siconnection()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL_INSERT_DEMANDER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final String Response = jsonObject.getString("response");
                                if (Response.equals("ok")) {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {////////////
                                           // Toast.makeText(Panier.this, Response, Toast.LENGTH_LONG).show();
                                        }
                                    }, 1000 );
                                    //dialog.dismiss();
                                } else {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                          //  Toast.makeText(Panier.this, Response, Toast.LENGTH_LONG).show();
                                        }
                                    }, 1000 );

                                    //dialog.dismiss();
                                    // recuplocal();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Toast.makeText(Article_insert.this, response, Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //db.insert_cat(libcat, DbContract.SYNC_STATUS_FAILED);
                    Toast.makeText(Panier.this,"error" + error.toString() , Toast.LENGTH_LONG).show();
                    //  dialog.dismiss();
                    //  recuplocal();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    try {

                        Date dt = new Date();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {////////////

                            }
                        }, 1000 );
                        Map<String, String> params = new HashMap<>();
                        params.put("idprod", String.valueOf(idprod));
                        params.put("idpan", String.valueOf(idpan));
                        params.put("qtdmd", String.valueOf(qtdmd));
                        params.put("prixpan", String.valueOf(prixpan));
                        params.put("mtlpan", String.valueOf(mtlpan));
                        params.put("syncstatus",String.valueOf(syncstatus));
                        params.put("datpan",String.valueOf(datpan));
                        return params;
                        //dialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(Panier.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    return getParams();
                }
            };
            Mysingleton.getInstance(Panier.this).addToRequestQueue(stringRequest);

        } else {
        }

    }

    public void updatepanier(final int idpan, final int mttpan, final int fraislivr, final int nbprod, final int statpan, final String datpan){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.PANIER_UPDATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final String Response = jsonObject.getString("response");
                                if (Response.equals("ok")) {

                                          //  Toast.makeText(Panier.this, Response+"modif", Toast.LENGTH_LONG).show();

                                } else {

                                          //  Toast.makeText(Panier.this, Response, Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Toast.makeText(Article_insert.this, response, Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //db.insert_cat(libcat, DbContract.SYNC_STATUS_FAILED);
                    Toast.makeText(Panier.this,"error" + error.toString() , Toast.LENGTH_LONG).show();
                    //  dialog.dismiss();
                    //  recuplocal();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    try {

                        Date dt = new Date();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {////////////

                            }
                        }, 1000 );
                        Map<String, String> params = new HashMap<>();
                        params.put("idpan", String.valueOf(idpan));
                        params.put("mttpan", String.valueOf(mttpan));
                        params.put("fraislivr", String.valueOf(fraislivr));
                        params.put("nbprod", String.valueOf(nbprod));
                        params.put("statpan", String.valueOf(statpan));
                        params.put("datpan",String.valueOf(datpan));
                        return params;
                        //dialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(Panier.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    return getParams();
                }
            };
            Mysingleton.getInstance(Panier.this).addToRequestQueue(stringRequest);


    }

    public boolean siconnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public int split_txt( TextView textView){
        int txt=0;
        String recuptxt = textView.getText().toString();
        String[] tabtxt = recuptxt.split(" ");
        txt=Math.round(Float.parseFloat(tabtxt[0]));
        return txt;
    }
    public int split_str( String str){
        int txt=0;
        String recuptxt = str;
        String[] tabtxt = recuptxt.split(" ");
        txt= Integer.parseInt(tabtxt[0]);
        return txt;
    }


    private void selectQuartier() {

        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_RECUP_QUARTIER,
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

                                //Enregistrement des données dans la bd SQLITE
                                //int idqtr,String nomqtr,int tarif,int idville
                                db.insert_Quartier(quartier.getInt("idqtr"),quartier.getString("nomqtr"),quartier.getInt("tarif"),quartier.getInt("idville"));
                               // Toast.makeText(getApplicationContext(),"Enregistrement reussie qtier!",Toast.LENGTH_LONG).show();

                            }
                          recuplocal();
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

    private void selectVille() {

        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);
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
                                JSONObject ville = array.getJSONObject(i);

                                //Enregistrement des données dans la bd SQLITE
                                db.insert_Ville(ville.getInt("idville"),ville.getString("nomville"));
                               // Toast.makeText(getApplicationContext(),"Enregistrement reussie!",Toast.LENGTH_LONG).show();

                            }
                            recuplocal();
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

    public void recuplocal()
    {
        arrayListville.clear();
        Cursor cursor=db.getAllVille2(et_ville.getText().toString());
        while (cursor.moveToNext())
        {
            int idville=cursor.getInt(cursor.getColumnIndex("idville"));
            String nomville=cursor.getString(cursor.getColumnIndex("nomville"));
            arrayListville.add(new Modele_ville(idville,nomville));
        }
        listville.setAdapter(adaptVille);
        adaptVille.notifyDataSetChanged();
        cursor.close();
        db.close();
    }
    private void recuplocalqtier(int vidville) {
        arrayListqtier.clear();
        Cursor cursor=db.getAllQuartier(et_qtier.getText().toString(),vidville);
        while (cursor.moveToNext())
        {

          //  String query= "select idqtr, nomqtr,tarif,idville from QUARTIER where idville="+ idville +" and nomqtr like '%"+search+"%'";

            int idqtr=cursor.getInt(cursor.getColumnIndex("idqtr"));
            String nomqtr=cursor.getString(cursor.getColumnIndex("nomqtr"));
            int tarif=cursor.getInt(cursor.getColumnIndex("tarif"));
            int idville=cursor.getInt(cursor.getColumnIndex("idville"));
            arrayListqtier.add(new Modele_quartier(nomqtr,idqtr,tarif,idville));
        }
        listqtr.setAdapter(adaptQuartier);
        adaptQuartier.notifyDataSetChanged();
        cursor.close();
        db.close();
    }

    private void paniercltencours() {

        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.PANIER_CLIENT_EN_COURS_CHARGEMENT+"1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            loading.dismiss();
                            JSONArray array = new JSONArray(response);

                            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date date = new Date();
                            System.out.println(format.format(date));
                           // Toast.makeText(getApplicationContext(),String.valueOf(date),Toast.LENGTH_LONG).show();
                                //Recuperation des données de la bd MYSQL
                                JSONObject idpanier = array.getJSONObject(0);
                               int idpann= idpanier.getInt("idpan");
                               Toast.makeText(Panier.this,String.valueOf(idpann),Toast.LENGTH_LONG).show();
                               Cursor c=db.localPanierToServer(idpan);
                            if (c.getCount() > 0) {
                                while (c.moveToNext()) {
                                    Integer s_idprod =Integer.parseInt(c.getString(c.getColumnIndex("idprod")));
                                    Integer s_idpan =Integer.parseInt(c.getString(c.getColumnIndex("idpan")));
                                    Integer s_qtdmd =Integer.parseInt(c.getString(c.getColumnIndex("qtdmd")));
                                    Integer s_prixpan =Integer.parseInt(c.getString(c.getColumnIndex("prixpan")));
                                    Integer s_mtlpan =Integer.parseInt(c.getString(c.getColumnIndex("mtlpan")));
                                //Integer s_syncstatus =Integer.parseInt(c.getString(c.getColumnIndex("syncstatus")));
                                 serverPannier(s_idprod,idpann,s_qtdmd,s_prixpan,s_mtlpan,0,date);


                                }
                                updatepanier(idpann,nap,split_txt(transport),arrayList.size(),1,String.valueOf(date));
                            }


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void creerproforma(){
        PdfDocument proforma=new PdfDocument();
        Paint proformapaint=new Paint();
        PdfDocument.PageInfo proformainfo=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page mapage1=proforma.startPage(proformainfo);
        Canvas canvas=mapage1.getCanvas();

        proforma.finishPage(mapage1);
        File file =new File(Environment.getExternalStorageDirectory(),"/proforma.pdf");

        try {
            proforma.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        proforma.close();
    }
}
