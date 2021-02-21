package com.ahitche.store.AhitcheStore.Articles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Categorie.Categories;
import com.ahitche.store.AhitcheStore.DB_AHITCHE;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Mysingleton;
import com.ahitche.store.AhitcheStore.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Article_insert extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    DB_AHITCHE db;
    String idcat;
    public static  String nom_tof,nomcpl;
    EditText deprod,prixprod,imgprod,poidprod,dimprod,idpart;
    ImageView img_prod;
    Spinner sp_idcat;
    Button btn_saveart;
    String vdeprod,vimgprod,vpoidprod,vdimprod;
    int vprixprod,vidcat;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_insert);
        db=new DB_AHITCHE(this);
        deprod=findViewById(R.id.et_deprod);
        prixprod=findViewById(R.id.et_prixprod);
        imgprod=findViewById(R.id.et_imgprod);
        poidprod=findViewById(R.id.et_dimprod);
        dimprod=findViewById(R.id.et_dimprod);
        img_prod=findViewById(R.id.image_art);
        sp_idcat=findViewById(R.id.idcatArt);
        btn_saveart=findViewById(R.id.save_art);
        Intent intent=getIntent();

         final String cod=intent.getStringExtra("fen");
        //Toast.makeText(Article_insert.this,cod, Toast.LENGTH_LONG).show();
        chargerspin();

        img_prod.setOnClickListener(new View.OnClickListener() {
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
        });

        btn_saveart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//////////////////////////////////////////////////////////////////////////////
                try {

                    vdeprod = deprod.getText().toString();
                    vprixprod = (int) Integer.parseInt(prixprod.getText().toString());
                    vimgprod = imgprod.getText().toString();
                    vpoidprod = poidprod.getText().toString();
                    vdimprod =dimprod.getText().toString();
                    idcat = sp_idcat.toString();
                  //  Toast.makeText(Article_insert.this, "failed", Toast.LENGTH_LONG).show();
                    if (vdeprod.equals("")) {

                        Toast.makeText(Article_insert.this, "Enregistrement non effectué", Toast.LENGTH_LONG).show();

                    } else {

                       serverProduit(vdeprod,vprixprod,vimgprod,vpoidprod,vdimprod, Integer.parseInt(spin_split()),DbContract.SYNC_STATUS_OK);
switch (cod){
    case "1":
        Intent intent=new Intent(Article_insert.this, Articles.class);
        startActivity(intent);
      cod.equals("");
      break;
    case "2":
         intent=new Intent(Article_insert.this, Patisserie.class);
        startActivity(intent);
        cod.equals("");
        break;
        default:

}

                    }

                    /////////////////////// ///////////////////////////////////////////////////
                }   catch (Exception e) {
                    Toast.makeText(Article_insert.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }


            }
        });

    /*    ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
        }*/

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


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            img_prod.setImageURI(data.getData());
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                ActivityCompat.requestPermissions(Article_insert.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
               // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               // img_prod.setImageBitmap(bitmap);
                Uri imageuri = data.getData();
                imgprod.setText(imageuri.getPath());
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                img_prod.setImageBitmap(bitmap);

//File path= Environment.getExternalStorageDirectory();
                String path = "/storage/emulated/0/";
                File dir = new File(path + "/Astore/");
                dir.mkdirs();
                // File file= new File(dir, imageuri.getAuthority());
                Calendar rightNow = Calendar.getInstance();
                Date dt = new Date();
                // int nom_tof=rightNow.get(Calendar.YEAR)+rightNow.get(Calendar.MONTH)+rightNow.get(Calendar.DAY_OF_MONTH)+dt.getHours()+dt.getMinutes()+dt.getSeconds();
                nom_tof = dt.getYear() + "" + dt.getMonth() + "" + dt.getDay() + "" + dt.getHours() + "" + dt.getMinutes() + "" + dt.getSeconds();
                nomcpl = nom_tof;
                //+ ".JPEG";
                // File file=new File(dir,nomclt.getText().toString()+".png");

                File file = new File(dir, nomcpl);

                OutputStream out = null;

                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                // Toast.makeText(Client.this, result, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chargerspin(){
        try{
            ArrayList<String> nomcat=db.getAllCat();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item, nomcat);
            dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_idcat.setAdapter(dataAdapter);
        } catch (Exception e) {
            Toast.makeText(Article_insert.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
    public void serverProduit(final String deprod,final int prixprod,final String imgprod,final String poidprod,final String dimprod,final int idpart,final int syncstatus){
      ///////
        //  libecat=lib_cat.getText().toString();
        if (siconnection()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL_PRODUIT,
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
                                    Toast.makeText(Article_insert.this, Response, Toast.LENGTH_LONG).show();
                                        }
                                    }, 1000 );
                                    //dialog.dismiss();
                                } else {
                       Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Article_insert.this, Response, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Article_insert.this,"error" + error.toString() , Toast.LENGTH_LONG).show();
                    //  dialog.dismiss();
                  //  recuplocal();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    try {

                        Date dt = new Date();
                        nom_tof = dt.getYear() + "" + dt.getMonth() + "" + dt.getDay() + "" + dt.getHours() + "" + dt.getMinutes() + "" + dt.getSeconds();
                        nomcpl = nom_tof;
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {////////////
                        Toast.makeText(Article_insert.this,spin_split(), Toast.LENGTH_LONG).show();
                            }
                        }, 1000 );
                    Map<String, String> params = new HashMap<>();
                    params.put("deprod",deprod);
                    params.put("prixprod", String.valueOf(prixprod));
                    params.put("imgprod",imgprod);
                    params.put("poidprod",poidprod);
                    params.put("dimprod",dimprod);
                    params.put("idpart",spin_split());
                    params.put("syncstatus",String.valueOf(DbContract.SYNC_STATUS_OK));
                        params.put("name",nomcpl);
                        params.put("image",imageToString(bitmap));
                        return params;
                        //dialog.dismiss();
                } catch (Exception e) {
               Toast.makeText(Article_insert.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }

                    return getParams();
                }
            };
          Mysingleton.getInstance(Article_insert.this).addToRequestQueue(stringRequest);

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
    public String spin_split(){
        String idc = sp_idcat.getSelectedItem().toString();
      // String img_art = "/storage/emulated/0/Astore/"+nomcpl;
        String[] idcl = idc.split(" ");
      //  String clt = idcl[0] + " " + idcl[1];
        String idp=idcl[0];
        return idp;
    }

    private  void uploadImage(){

    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT); 


    }

}
