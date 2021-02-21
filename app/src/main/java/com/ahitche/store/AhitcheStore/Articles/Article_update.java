package com.ahitche.store.AhitcheStore.Articles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.ahitche.store.AhitcheStore.DB_AHITCHE;
import com.ahitche.store.AhitcheStore.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Article_update extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    DB_AHITCHE db;
    String idcat;
    public static  String nom_tof,nomcpl;
    EditText idprod,deprod,prixprod,imgprod,poidprod,dimprod,idpart;
    ImageView img_prod;
    Spinner sp_idcat;
    Button btn_modifart;
    String vdeprod,vimgprod,vpoidprod,vdimprod;
    int vprixprod,vidcat,vidprod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_update);
        db=new DB_AHITCHE(this);
idprod=findViewById(R.id.ed_idprodM);
        deprod=findViewById(R.id.et_deprodM);
        prixprod=findViewById(R.id.et_prixprodM);
        imgprod=findViewById(R.id.et_imgprodM);
        poidprod=findViewById(R.id.et_poidprodM);
        dimprod=findViewById(R.id.et_dimprodM);
        img_prod=findViewById(R.id.image_artM);
        sp_idcat=findViewById(R.id.idcatArtM);
        btn_modifart=findViewById(R.id.update_art);
        Intent intent=getIntent();
        idprod.setText(String.valueOf(intent.getIntExtra("idp",0)));
        Viewupdate();
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

        btn_modifart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//////////////////////////////////////////////////////////////////////////////
                try {
                     vidprod= (int) Integer.parseInt(idprod.getText().toString());
                    vdeprod = deprod.getText().toString();
                    vprixprod = (int) Integer.parseInt(prixprod.getText().toString());
                    vimgprod = imgprod.getText().toString();
                    vpoidprod = poidprod.getText().toString();
                    vdimprod =dimprod.getText().toString();
                    idcat = sp_idcat.toString();

                    if (vdeprod.equals("")) {

                        Toast.makeText(Article_update.this, "Modification non effectué", Toast.LENGTH_LONG).show();

                    } else {
                        String idc = sp_idcat.getSelectedItem().toString();
                        String img_art = "/storage/emulated/0/Astore/"+nomcpl;
                        String[] idcl = idc.split(" ");
                        String clt = idcl[0] + " " + idcl[1];
                        String idp=idcl[0];
                        //update_Produit(String deprod, Integer prixprod, String imgprod , String poidprod, String dimprod, Integer idpart, Integer idprod)
                        if (db.update_Produit(vdeprod,vprixprod,img_art,vpoidprod,vdimprod, Integer.parseInt(idp),vidprod)==true){
                            Toast.makeText(Article_update.this, "Modification  effectué", Toast.LENGTH_LONG).show();
                        }
                        //db.insert_Produit(vdeprod, vprixprod, img_art , vpoidprod, vdimprod, Integer.parseInt(idp));
                        Toast.makeText(Article_update.this, img_art, Toast.LENGTH_LONG).show();


//Integer.parseInt(cod);

                             //   Intent intent=new Intent(Article_update.this, Articles.class);
                               // startActivity(intent);


                    }

                    /////////////////////// ///////////////////////////////////////////////////
                }   catch (Exception e) {
                    Toast.makeText(Article_update.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }


            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            img_prod.setImageURI(data.getData());

                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                ActivityCompat.requestPermissions(Article_update.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_prod.setImageBitmap(bitmap);
                Uri imageuri = data.getData();
                imgprod.setText(imageuri.getPath());

//File path= Environment.getExternalStorageDirectory();
                String path = "/storage/emulated/0/";
                File dir = new File(path + "/Astore/");
                dir.mkdirs();
                // File file= new File(dir, imageuri.getAuthority());
                Calendar rightNow = Calendar.getInstance();
                Date dt = new Date();
                // int nom_tof=rightNow.get(Calendar.YEAR)+rightNow.get(Calendar.MONTH)+rightNow.get(Calendar.DAY_OF_MONTH)+dt.getHours()+dt.getMinutes()+dt.getSeconds();
                nom_tof = dt.getYear() + "" + dt.getMonth() + "" + dt.getDay() + "" + dt.getHours() + "" + dt.getMinutes() + "" + dt.getSeconds();
                nomcpl = nom_tof + ".png";
                // File file=new File(dir,nomclt.getText().toString()+".png");

                File file = new File(dir, nomcpl);

                OutputStream out = null;

                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                // Toast.makeText(Client.this, result, Toast.LENGTH_LONG).show();

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerspin() {
        try{
            ArrayList<String> nomcat=db.getAllCat();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_dropdown_item, nomcat);
            dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            sp_idcat.setAdapter(dataAdapter);
        } catch (Exception e) {
            Toast.makeText(Article_update.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
    public void Viewupdate(){
        try {
            Cursor cursor = db.selectforupdate(Integer.parseInt(idprod.getText().toString()));
            if (cursor != null) {
                cursor.moveToFirst();
                startManagingCursor(cursor);
                for (int i = 0; i < cursor.getCount(); i++) {
                    String vdeprod = cursor.getString(1);
                    int vprixprod = cursor.getInt(2);
                    String vimgprod = cursor.getString(3);
                    String vpoidprod = cursor.getString(4);
                    String vdimprod = cursor.getString(5);
                    String vidpart = cursor.getString(6);
                    deprod.setText(vdeprod);
                    prixprod.setText(String.valueOf(vprixprod));
                    imgprod.setText(vimgprod);
                    poidprod.setText(vpoidprod);
                    dimprod.setText(vdimprod);
                    loadimage(vimgprod,img_prod);
                    //idpart.setText(vidpart);
                }
            }
        } catch (Exception e) {
            Toast.makeText(Article_update.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
    public void loadimage(String path,ImageView image ){
        //File imgFile = new  File(path);

        File fil=new File(path);

        Picasso.get().load(fil).fit().into(image);

    }
}
