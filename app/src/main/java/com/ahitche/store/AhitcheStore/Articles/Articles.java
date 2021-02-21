package com.ahitche.store.AhitcheStore.Articles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.PlaybackState;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Adapter.AdaptPatis;
import com.ahitche.store.AhitcheStore.Adapter.Myadapters;
import com.ahitche.store.AhitcheStore.ArticleGridItem;
import com.ahitche.store.AhitcheStore.Categorie.Categories;
import com.ahitche.store.AhitcheStore.DB_AHITCHE;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_categorie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Categorie;
import com.ahitche.store.AhitcheStore.Mysingleton;
import com.ahitche.store.AhitcheStore.Panier;
import com.ahitche.store.AhitcheStore.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Articles extends AppCompatActivity {

    GridView gridView;
    ArrayList<Modele_Article> arrayList;
    DB_AHITCHE db;
    FloatingActionButton fab, fabmodif,fabsupp;
    TextView optiont;
    List<CustomAdapter> productList;
    int cptpan, pancours,idmax;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles);
        db=new DB_AHITCHE(this);
        gridView=findViewById(R.id.gridview);
        optiont=findViewById(R.id.txtpluS);
        arrayList = new ArrayList<>();
        fabmodif=findViewById(R.id.Fabmod_art);
        fabsupp=findViewById(R.id.Fabsuppr_art);
        fab=findViewById(R.id.Fab_art);
        pancours=db.panClientencours(1);
        cptpan=db.cptelmtpanier(pancours);
       productList=new ArrayList<>();
       selectArticles();
        chargerArt();
          idmax=db.selmaxpanier(1);
        Toast.makeText(Articles.this, String.valueOf(idmax), Toast.LENGTH_SHORT).show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Articles.this,Article_insert.class);
                intent.putExtra("fen","1");
                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView prixp=(TextView)view.findViewById(R.id.txtprixprod);
                TextView vimgprod=(TextView)view.findViewById(R.id.imgprod);
                TextView deprodt=(TextView)view.findViewById(R.id.txtdeprod);
                TextView vidprod=(TextView)view.findViewById(R.id.txt_idprod);




                int prixpro= Integer.parseInt(prixp.getText().toString());
                int idprod=Integer.parseInt(vidprod.getText().toString());
                String imgprod=vimgprod.getText().toString();
                String deprod=deprodt.getText().toString();

                Intent intent=new Intent(getApplicationContext(), ArticleGridItem.class);
                intent.putExtra("nom",deprod);
                intent.putExtra("idp",idprod);
                intent.putExtra("prix",prixpro);
                intent.putExtra("image",imgprod);
                intent.putExtra("nomfen","refrigerateur");
                startActivity(intent);
            }
        });


    }

    private class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<Modele_Article> arrayList;

        public CustomAdapter(Context context, ArrayList<Modele_Article> arrayList){
            this.context=context;
            this.arrayList=arrayList;
        }




        @Override
        public int getCount() {
            return this.arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public void loadpathimage(String path,ImageView image ){
            File fil=new File(path);
            Picasso.get().load(fil).fit().into(image);

        }
        public void loadimage(String url,ImageView image ){
             Picasso.get().load(url).into(image);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.article_row,null);
            TextView prixp=(TextView)convertView.findViewById(R.id.txtprixprod);
            TextView vimgprod=(TextView)convertView.findViewById(R.id.imgprod);
            TextView deprodt=(TextView)convertView.findViewById(R.id.txtdeprod);
            TextView vidprod=(TextView)convertView.findViewById(R.id.txt_idprod);
            ImageView imagart=(ImageView)convertView.findViewById(R.id.imgart);


            Modele_Article modele_article=arrayList.get(position);
            vidprod.setText(Integer.toString(modele_article.getIdprod()));
            prixp.setText(Integer.toString(modele_article.getPrixprod()));
            deprodt.setText(modele_article.getDeprod());
            vimgprod.setText(modele_article.getImgprod());
           loadimage(DbContract.SERVER_IMAGE_PATH+vimgprod.getText().toString(),imagart);


            return convertView;


        }
    }

    private void chargerArt() {
        arrayList=db.getallArt(1);
        CustomAdapter customAdapter=new CustomAdapter(this, arrayList);

        gridView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        // View view=menu.findItem(R.id.cart_menu).getActionView();
        getMenuInflater().inflate(R.menu.menu_panier,menu);
        MenuItem item=menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item,R.layout.action_bar_notification_icon);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        Button tv = (Button) notifCount.findViewById(R.id.actionbar_notifcation_button);
        tv.setText(String.valueOf(cptpan));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Articles.this, Panier.class);
                intent.putExtra("idpan",idmax);
                               startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void selectArticles() {
       // int id = editTextId.getText().toString().trim();
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_URL_SELECT_PRODUITT+"1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            loading.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //Recuperation des données de la bd MYSQL
                                JSONObject product = array.getJSONObject(i);
                                //Enregistrement des données dans la bd SQLITE
                                db.insert_Produit(product.getInt("idprod"),product.getString("deprod"),product.getInt("prixprod"),product.getString("imgprod"),product.getString("poidprod"),product.getString("dimprod"),product.getInt("idpart"),DbContract.SYNC_STATUS_OK);

                                Toast.makeText(Articles.this, product.getString("deprod"), Toast.LENGTH_SHORT).show();}
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
