package com.ahitche.store.AhitcheStore.Articles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.ahitche.store.AhitcheStore.ArticleGridItem;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Climatiseur extends AppCompatActivity {


    GridView gridView;
    ArrayList<Modele_Article> arrayList;
    DB_AHITCHE db;
    FloatingActionButton fab, fabmodif, fabsupp;
    TextView optiont;
    int cptpan, pancours;
    List<CustomAdapter> productList;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.climatiseur);



        db = new DB_AHITCHE(this);
        gridView = findViewById(R.id.gridclim);
        optiont = findViewById(R.id.txtplusclim);

        pancours=db.panClientencours(1);
        cptpan=db.cptelmtpanier(pancours);
        fab = findViewById(R.id.Fab_clim);
        productList=new ArrayList<>();
        arrayList = new ArrayList<>();
        selectClimatiseurs();
       chargerArt();
        final int idmax=db.selmaxpanier(1);
        Toast.makeText(Climatiseur.this, String.valueOf(idmax), Toast.LENGTH_SHORT).show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Climatiseur.this, Article_insert.class);
                intent.putExtra("fen", "1");
                startActivity(intent);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView prixp = (TextView) view.findViewById(R.id.txtprixclim);
                TextView vimgprod = (TextView) view.findViewById(R.id.imgvclim);
                TextView deprodt = (TextView) view.findViewById(R.id.txtdeclim);
                TextView vidprod = (TextView) view.findViewById(R.id.txt_idclim);


                int prixpro = Integer.parseInt(prixp.getText().toString());
                int idprod = Integer.parseInt(vidprod.getText().toString());
                String imgprod = vimgprod.getText().toString();
                String deprod = deprodt.getText().toString();

                Intent intent = new Intent(getApplicationContext(), ArticleGridItem.class);
                intent.putExtra("nom", deprod);
                intent.putExtra("idp", idprod);
                intent.putExtra("prix", prixpro);
                intent.putExtra("image", imgprod);
                startActivity(intent);
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<Modele_Article> arrayList;

        public CustomAdapter(Context context, ArrayList<Modele_Article> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.clim_row, null);
            // Button btn_avatar=(Button)convertView.findViewById(R.id.avatar);
            TextView prixp = (TextView) convertView.findViewById(R.id.txtprixclim);
            TextView vimgprod = (TextView) convertView.findViewById(R.id.imgvclim);
            TextView deprodt = (TextView) convertView.findViewById(R.id.txtdeclim);
            TextView vidprod = (TextView) convertView.findViewById(R.id.txt_idclim);
            ImageView imagart = (ImageView) convertView.findViewById(R.id.imgclim);


            Modele_Article modele_article = arrayList.get(position);
            vidprod.setText(Integer.toString(modele_article.getIdprod()));
            prixp.setText(Integer.toString(modele_article.getPrixprod()));
            deprodt.setText(modele_article.getDeprod());
            vimgprod.setText(modele_article.getImgprod());
            loadimage(vimgprod.getText().toString(), imagart);
            // loadimage("/storage/emulated/0/Astore/120550224.png",imagart);


            return convertView;

        }

    }
    public void loadpathimage(String path, ImageView image) {
        File fil = new File(path);
        Picasso.get().load(fil).fit().into(image);
    }
    public void loadimage(String url,ImageView image ){
        Picasso.get().load(url).into(image);
    }


    private void chargerArt() {
        arrayList=db.getallArt(3);
        Climatiseur.CustomAdapter customAdapter=new Climatiseur.CustomAdapter(this, arrayList);

        gridView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
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
                Intent intent=new Intent(Climatiseur.this, Panier.class);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void selectClimatiseurs() {
        loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);

        String url = DbContract.SERVER_URL_SELECT_PRODUITT +1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_URL_SELECT_PRODUITT+"3",
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
                                db.insert_Produit(product.getInt("idprod"),product.getString("deprod"),product.getInt("prixprod"),product.getString("imgprod"),product.getString("poidprod"),product.getString("dimprod"),product.getInt("idpart"),DbContract.SYNC_STATUS_OK);
                                Toast.makeText(getApplicationContext(), "enregistrement effectué", Toast.LENGTH_LONG).show();
                                //adding the product to product list
                                arrayList.add(new Modele_Article(
                                        product.getInt("prixprod"),
                                        product.getInt("idprod"),
                                        product.getString("deprod"),
                                        product.getString("imgprod")

                                ));
                               // db.insert_Produit(product.getInt("idprod"),product.getString("deprod"),product.getInt("prixprod"),product.getString("imgprod"),product.getString("poidprod"),product.getString("dimprod"),product.getInt("idpart"),DbContract.SYNC_STATUS_OK);
                            }

                            //creating adapter object and setting it to recyclerview
                         CustomAdapter adapter = new CustomAdapter(Climatiseur.this, arrayList);
                            // recyclerView.setAdapter(adapter);
                            CustomAdapter customAdapter=new CustomAdapter(Climatiseur.this, arrayList);

                          gridView.setAdapter(customAdapter);
                           customAdapter.notifyDataSetChanged();
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


