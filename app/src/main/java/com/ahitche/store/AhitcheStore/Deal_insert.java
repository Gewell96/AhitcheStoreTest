package com.ahitche.store.AhitcheStore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahitche.store.AhitcheStore.Adapter.AdaptVille;
import com.ahitche.store.AhitcheStore.Adapter.Adaptdeal;
import com.ahitche.store.AhitcheStore.Adapter.Adaptdealimg;
import com.ahitche.store.AhitcheStore.Articles.Article_insert;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_ville;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Modele_deal;
import com.ahitche.store.AhitcheStore.Modeles.Modele_dealimg;
import com.ahitche.store.AhitcheStore.Modeles.Modele_ville;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import okhttp3.MultipartBody;

public class Deal_insert extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE=1;
    private static final int PERMISSION_CODE=100;
    private static final int CODE_MULTIPLE_IMAGE=2;
    DB_AHITCHE db;
    public static  String nom_tof,nomcpl;

    String[] paths;

    // descdeal,  imgdeal,  datedeal,  telappel,  telwhatapp,  catdeal,  logouser, int idclt
    EditText txt_descdeal,txt_titdeal,txt_imgdeal,txt_datedeal,txt_telappel,txt_telwhatapp,txt_vildeal,txt_prixdeal,txt_idcat;

    ImageView  img_deal;
    Spinner sp_catdeal, sp_etadeal;
    AdaptVille adaptVille;
    Button btn_savedeal;
    RecyclerView listimgdeal;
    ArrayList<Modele_dealimg> arrayList;
    Adaptdealimg adaptdealimg;
    ListView vildealist;
    ArrayList<Modele_ville> arrayListville;
    CheckBox checklivdeal;
    String vtitredeal, vdescdeal,vetatdeal,vlivdeal,vlocdeal,vteldeal,vwhatdeal,vdatdeal,vtofs,vidcat;
    int viduser, vprixdeal,vtopdeal;
     Bitmap bitmap;
    private static final int REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_insert);
        db=new DB_AHITCHE(this);
        txt_descdeal=findViewById(R.id.descdeal);
        txt_imgdeal=findViewById(R.id.imgdeal);
        txt_datedeal=findViewById(R.id.datedeal);
        txt_telappel=findViewById(R.id.telappel);
        txt_telwhatapp=findViewById(R.id.telwhatapp);
        sp_catdeal=findViewById(R.id.idcatdeal);
        sp_etadeal=findViewById(R.id.etadeal);
        vildealist=findViewById(R.id.vildealist);
        txt_vildeal=findViewById(R.id.vildeal);
        arrayListville = new ArrayList<>();
        img_deal=findViewById(R.id.tof1);
        checklivdeal=findViewById(R.id.livdeal);
        txt_titdeal=findViewById(R.id.titdeal);
//////////////////////////////////



        ///////////////////////////////////////////////
        Fresco.initialize(getApplicationContext());

        btn_savedeal=findViewById(R.id.save_deal);

        txt_prixdeal=findViewById(R.id.prixdeal);

        adaptdealimg=new Adaptdealimg(arrayList,this);
        listimgdeal=findViewById(R.id.rvimgdeal);
        adaptVille = new AdaptVille(this, arrayListville);
        arrayList=new ArrayList<>();
        chargerspin();
        chargerspinEtat();
        selectVille();
        viduser=1;
        vtopdeal=1;
        btn_savedeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateactuel= DateFormat.getDateTimeInstance().format(new Date());
                txt_datedeal.setText(dateactuel);
                if (checklivdeal.isChecked()){
                    vlivdeal="oui";
                }else{
                    vlivdeal="non";
                }
                        vtitredeal= txt_titdeal.getText().toString();
                        vprixdeal= Integer.parseInt(txt_prixdeal.getText().toString());
                        vetatdeal=sp_etadeal.getSelectedItem().toString();
                        vdescdeal=txt_descdeal.getText().toString();
                        vlocdeal=txt_vildeal.getText().toString();
                        vteldeal=txt_telappel.getText().toString();
                        vwhatdeal=txt_telwhatapp.getText().toString();
                        vdatdeal=txt_datedeal.getText().toString();
                        vtofs="voici les liens des tofs à publier";
                        vidcat=sp_catdeal.getSelectedItem().toString();

                serverDeal(vtitredeal,vprixdeal,vetatdeal,vlivdeal,vdescdeal,vlocdeal,vteldeal,vwhatdeal,vtopdeal,vdatdeal,vtofs,viduser,split_str(vidcat));

            }
        });




        vildealist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView vnomville=(TextView)view.findViewById(R.id.nomvilletxt);
                TextView vidville=(TextView)view.findViewById(R.id.idvil);
                String nom_ville=vnomville.getText().toString();
                txt_vildeal.setText(nom_ville);
               // txt_idvilqtr.setText(vidville.getText().toString());
                vildealist.setVisibility(vildealist.GONE);
               // selectQuartier();
            }
        });

        txt_vildeal.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") ) {
                    vildealist.setVisibility(vildealist.VISIBLE);
                    recuplocal();
                }
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


        //txt_idclt=findViewById(R.id.idclt);

        final Intent intent=getIntent();

        final String cod=intent.getStringExtra("fen");
        //Toast.makeText(Article_insert.this,cod, Toast.LENGTH_LONG).show();
        // chargerspin();
      /*  img_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }else {
                    pickImageFromGallery();

                }
            }
        });*/
img_deal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        } else {
            Intent intent1 = new Intent();
            intent1.setType("image/*");
            intent1.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent1.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent1, "Selectionner des images"),
                    CODE_MULTIPLE_IMAGE);
        }
    }
});
/*img_deal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Deal_insert.this, AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }
});*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_article,menu);
        return true;
    }
    private void pickImageFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.tof_art:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();

                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint({"MissingSuperCall", "NewApi"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (resultCode == RESULT_OK && data!=null){

            ClipData clipData = data.getClipData();
            if (clipData!=null){
                for (int i=0;i < clipData.getItemCount();i++){
                    //En lieu et place des imageview nus utilserons un reclyclerview
                    try {
                        if (i<=2) {
                            ClipData.Item item = clipData.getItemAt(i);
                            Uri uri = item.getUri();
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                            arrayList.add(new Modele_dealimg(bitmap));
                            listimgdeal.setLayoutManager(new LinearLayoutManager(listimgdeal.getContext(), LinearLayoutManager.HORIZONTAL, false));
                            listimgdeal.setAdapter(new Adaptdealimg(arrayList, this));
                        }else if (i==3){
                            Toast.makeText(Deal_insert.this,"Vous ne pouvez choisir que 3 images!",Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }


        }
    }
    private void chargerspin(){//on doit rediriger l'image vers le domaine
        try{
            ArrayList<String> nomcat=db.getAllCat();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item, nomcat);
            dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_catdeal.setAdapter(dataAdapter);
        } catch (Exception e) {
            Toast.makeText(Deal_insert.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void selectVille() {

       // loading = ProgressDialog.show(this,"Patientez svp...","Chargement...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_RECUP_VILLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            //converting the string to json array object
                          //  loading.dismiss();
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
        Cursor cursor=db.getAllVille2(txt_vildeal.getText().toString());
        while (cursor.moveToNext())
        {
            int idville=cursor.getInt(cursor.getColumnIndex("idville"));
            String nomville=cursor.getString(cursor.getColumnIndex("nomville"));
            arrayListville.add(new Modele_ville(idville,nomville));
        }
        vildealist.setAdapter(adaptVille);
        adaptVille.notifyDataSetChanged();
        cursor.close();
        db.close();
    }

    private void chargerspinEtat(){
        try{
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item, DbContract.Etat);
            dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_etadeal.setAdapter(dataAdapter);
        } catch (Exception e) {
            Toast.makeText(Deal_insert.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    //Enregistrement des donnees sur le serveur
    public void serverDeal(final String titdeal,final int prixdeal,final String etatdeal,final String livdeal,final String descdeal,final String locdeal,final String teldeal,final String whatdeal,final int topdeal,final String datdeal,final String tofs,final int iduser,final int idcat){
        ///////
        //  libecat=lib_cat.getText().toString();
        if (siconnection()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_INSERT_DEAL,
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
                                        public void run() {
                                               Log.d("response",Response);
                                        }
                                    }, 1000 );
                                    //dialog.dismiss();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Log.d("response",Response);
                                        }
                                    });

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
                    Toast.makeText(Deal_insert.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("volleyerror",error.getMessage());

                    //  dialog.dismiss();
                    //  recuplocal();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    try {
//,,,,,,,,,,,,
                       // Date dt = new Date();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {////////////

                            }
                        }, 1000 );
                        Map<String, String> params = new HashMap<>();
                        params.put("titdeal", titdeal);
                        params.put("prixdeal", String.valueOf(prixdeal));
                        params.put("etatdeal", etatdeal);
                        params.put("livdeal", livdeal);
                        params.put("descdeal", descdeal);
                        params.put("locdeal",locdeal);
                        params.put("teldeal",teldeal);
                        params.put("whatdeal",whatdeal);
                        params.put("topdeal",String.valueOf(topdeal));
                        params.put("datdeal",datdeal);
                        params.put("tofs",tofs);
                        params.put("iduser",String.valueOf(iduser));
                        params.put("idcat",String.valueOf(idcat));

                        params.put("image1", imageToString(arrayList.get(0).getLienimg()));
                        params.put("image2", imageToString(arrayList.get(1).getLienimg()));
                        params.put("image3", imageToString(arrayList.get(2).getLienimg()));

                        return params;
                        //dialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(Deal_insert.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    return getParams();
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
            Mysingleton.getInstance(Deal_insert.this).addToRequestQueue(stringRequest);

        } else {
        }

    }

    public boolean siconnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public int split_str( String str){
        int txt=0;
        String recuptxt = str;
        String[] tabtxt = recuptxt.split(" ");
        txt= Integer.parseInt(tabtxt[0]);
        return txt;
    }
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);


    }
}