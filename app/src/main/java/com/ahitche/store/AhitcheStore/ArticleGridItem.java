package com.ahitche.store.AhitcheStore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Articles.Article_insert;
import com.ahitche.store.AhitcheStore.Articles.Article_update;
import com.ahitche.store.AhitcheStore.Articles.Articles;
import com.ahitche.store.AhitcheStore.Articles.Patisserie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArticleGridItem extends AppCompatActivity {
    DB_AHITCHE db;
    TextView vprixart,vnomart,lien,vidprod,qtdmd, mttpan;
    ImageView ima_art;
    FloatingActionButton fab_modifart;
    Button btn_moins,btn_plus,btnajoutpan;
    int txt_qtdmd,txt_mttpan,prixp,vprixunit;
    String str_prixunit,str_mttpan,strmonart,nomfen;
    public  int num_panier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_grid_item);

        db=new  DB_AHITCHE(this);
        vidprod=findViewById(R.id.txtidprod);
        vprixart=findViewById(R.id.txt_prixprod);
        vnomart=findViewById(R.id.txt_deprod);
        lien=findViewById(R.id.imaprod);
        ima_art=findViewById(R.id.imagvart);
        fab_modifart=findViewById(R.id.FabModif_art);
        qtdmd=findViewById(R.id.vqtdmd);
        btn_moins=findViewById(R.id.avatar1);
        btn_plus=findViewById(R.id.avatar2);
        mttpan=findViewById(R.id.vmttpan);
        btnajoutpan=findViewById(R.id.ajpanier);
        txt_qtdmd= Integer.parseInt(qtdmd.getText().toString());

        Intent intent=getIntent();
        vnomart.setText(intent.getStringExtra("nom"));
        vprixart.setText(String.valueOf(intent.getIntExtra("prix",0))+" F CFA");
        mttpan.setText(String.valueOf(intent.getIntExtra("prix",0))+" F CFA");
        vidprod.setText(String.valueOf(intent.getIntExtra("idp",0)));
        lien.setText(intent.getStringExtra("image"));
        nomfen=intent.getStringExtra("nomfen");
     //   Toast.makeText(ArticleGridItem.this,lien.getText().toString(), Toast.LENGTH_LONG).show();

        str_prixunit=vprixart.getText().toString();
        str_mttpan=mttpan.getText().toString();
        String[] prix = str_prixunit.split(" ");
        prixp= Integer.parseInt(prix[0]);
        String[] mtt = str_mttpan.split(" ");
        txt_mttpan= Integer.parseInt(mtt[0]);
        loadimage(DbContract.SERVER_IMAGE_PATH+lien.getText().toString(),ima_art);

        fab_modifart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ArticleGridItem.this, Article_update.class);
                intent.putExtra("idp",Integer.valueOf(vidprod.getText().toString()));
                startActivity(intent);
            }
        });
        btn_moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_qtdmd==1) {

                }else if (txt_qtdmd>1){
                    txt_qtdmd--;
                    vprixunit=prixp*txt_qtdmd;
                    qtdmd.setText(String.valueOf(txt_qtdmd));
                    mttpan.setText(String.valueOf(vprixunit)+" F CFA");
                }
            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_qtdmd++;
                vprixunit=prixp*txt_qtdmd;
                qtdmd.setText(String.valueOf(txt_qtdmd));
                mttpan.setText(String.valueOf(vprixunit)+" F CFA");
            }
        });
//debut insert to table panier

        btnajoutpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//////////////////////////////////////////////////////////////////////////////
                try {
                    int idprod= Integer.parseInt(vidprod.getText().toString());
                    strmonart =vnomart.getText().toString();
                    // int prixart= Integer.parseInt(vprixart.getText().toString());
                    // int mttlpan= Integer.parseInt(mttpan.getText().toString());
                    final int idmax=db.selmaxpanier(1);

                    str_prixunit=vprixart.getText().toString();
                    str_mttpan=mttpan.getText().toString();
                    String[] prix = str_prixunit.split(" ");
                    prixp= Integer.parseInt(prix[0]);
                    String[] mtt = str_mttpan.split(" ");
                    txt_mttpan= Integer.parseInt(mtt[0]);

                    num_panier++;

                   Toast.makeText(ArticleGridItem.this, String.valueOf(idmax), Toast.LENGTH_SHORT).show();
                    //on teste voir sil nya pas un pannier en cours
                    int dbpancltencours=db.panClientencours(1);
                    Toast.makeText(ArticleGridItem.this,String.valueOf(dbpancltencours), Toast.LENGTH_LONG).show();
                    if (dbpancltencours==0) {
                        //s'il y en a pas alors on crée
                        if (db.insert_Pannier(idmax, txt_mttpan, txt_qtdmd, 1, 0) == true) {
                            serverPanier(0,0,1,0);
                            Toast.makeText(ArticleGridItem.this, "panier crée!", Toast.LENGTH_LONG).show();
                            if (db.insert_Demander(idprod, 1, txt_qtdmd, prixp, txt_mttpan) == true) {
                            Toast.makeText(ArticleGridItem.this, "Article ajouté au panier avec succès!", Toast.LENGTH_LONG).show();
                        }else{
                                Toast.makeText(ArticleGridItem.this, "Cet article a déjà été ajouté au panier", Toast.LENGTH_LONG).show();
                            }

                        }
                        // sil y en, on ajoute les produits choisis
                    } else{
                        //if (db.panClientencours(1)>0) {
                        int testpan=db.panClientencours(1);
                        if (db.siprodexiste(testpan,idprod)>0) {

                            Toast.makeText(ArticleGridItem.this, "Cet article a déjà été ajouté au panier2", Toast.LENGTH_LONG).show();

                        }else{
                            if (db.siprodexiste(testpan,idprod)==0){
                                if (db.insert_Demander(idprod, testpan,txt_qtdmd, prixp, txt_mttpan) == true) {
                                    Toast.makeText(ArticleGridItem.this,"Article ajouté au panier avec succès!", Toast.LENGTH_LONG).show();
                                    //    finish();

                                }else{

                                    Toast.makeText(ArticleGridItem.this,"Article non ajouté au panier!", Toast.LENGTH_LONG).show();
                                    if (nomfen.equals("refrigerateur") ){
                                        Intent intent=new Intent(ArticleGridItem.this,Articles.class);
                                        startActivity(intent);
                                    }
                                }}
                        }
                        // }
                    }




                    /////////////////////// ///////////////////////////////////////////////////
                }   catch (Exception e) {
                    Toast.makeText(ArticleGridItem.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }


            }
        });

//fin insert to table panier


    }
    public void loadpathimage(String path,ImageView image ){
        File fil=new File(path);
        Picasso.get().load(fil).fit().into(image);
    }
    public void loadimage(String url,ImageView image ){
        Picasso.get().load(url).into(image);

    }


    public void serverPanier(final int mttot,final int transport,final int iduser,final int statpan){
        ///////
        //  libecat=lib_cat.getText().toString();
        if (siconnection()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL_INSERT_PANIER,
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
                                            Toast.makeText(ArticleGridItem.this, Response, Toast.LENGTH_LONG).show();
                                        }
                                    }, 1000 );
                                    //dialog.dismiss();
                                } else {
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ArticleGridItem.this, Response, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ArticleGridItem.this,"error" + error.toString() , Toast.LENGTH_LONG).show();
                    //  dialog.dismiss();
                    //  recuplocal();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    try {

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {////////////
                              //  Toast.makeText(ArticleGridItem.this,spin_split(), Toast.LENGTH_LONG).show();
                            }
                        }, 1000 );

                        Map<String, String> params = new HashMap<>();
                        params.put("mttpan",String.valueOf(mttot));
                        params.put("nbprod", String.valueOf(transport));
                        params.put("iduser",String.valueOf(iduser));
                        params.put("statpan",String.valueOf(statpan));
                        params.put("syncstatus",String.valueOf(DbContract.SYNC_STATUS_OK));

                        return params;
                        //dialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(ArticleGridItem.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    return getParams();
                }
            };
            Mysingleton.getInstance(ArticleGridItem.this).addToRequestQueue(stringRequest);

        } else {
            //  db.insert_Produit(deprod,prixprod,imgprod,poidprod,dimprod,idpart, DbContract.SYNC_STATUS_FAILED);
            //Toast.makeText(Article_insert.this, "Enregistrement  effectué", Toast.LENGTH_LONG).show();
        }

    }
    public boolean siconnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
