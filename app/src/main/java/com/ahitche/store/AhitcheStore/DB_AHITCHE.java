package com.ahitche.store.AhitcheStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Categorie;

import java.util.ArrayList;

public class DB_AHITCHE extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AStoredb ";
    private static final int DATABASE_VERSION =6;

    String Crea_cat= "create table CATEGORIE ("
            + "     idcat integer primary key autoincrement,"
            + "     libcat text not null,"
            + "     syncstatus integer"
            + ")";

    String Crea_User = "create table USER ("
            + "     iduser integer primary key autoincrement,"
            + "     nomuser text not null,"
            + "     preuser integer not null,"
            + "     teluser text not null,"
            + "     emailuser text  null,"  // Spinner
            + "     imguser text not null,"   // a indiquer dans un hint
            + "     datinsuser integer not null,"    // Spinner
            + "     syncstatus integer"
            + ")";


    String Crea_Produit = "create table PRODUIT ("
            + "     idprod integer primary key,"
            + "     deprod text not null,"
            + "     prixprod integer not null,"
            + "     imgprod text not null,"
            + "     poidprod text not null,"// Spinner
            + "     dimprod text not null,"// a indiquer dans un hint
            + "     idpart integer not null,"// Spinner
            + "     syncstatus integer"
            + ")";

    String Crea_Partenaire = "create table PARTENAIRE ("
            + "     idpart integer primary key autoincrement,"
            + "     depart text not null,"// Spinner
            + "     villpart text not null," // Spinner
            + "     quapart text not null,"
            + "     raispart text not null,"
            + "     emailpart text not null,"
            + "     telpart text not null,"
            + "     houvert text not null,"//Spinner
            + "     hferme text not null,"//Spinner
            + "     syncstatus integer"
            + ")";

    String Crea_Demander = "create table DEMANDER ("
            + "     idprod integer not null,"
            + "     idpan integer not null,"
            + "     qtdmd integer not null,"
            + "     prixpan integer not null,"
            + "     mtlpan integer not null,"
            + "     syncstatus integer, PRIMARY KEY(idprod,idpan)"
            + ")";

    String Crea_Pannier = "create table PANNIER ("
            + "     idpan integer primary key,"
            + "     mttpan integer not null,"
            + "     nbprod integer not null,"
            + "     iduser integer not null,"
            + "     statpan integer not null,"
            + "     syncstatus integer"
            + ")";

    String Crea_Proforma = "create table PROFORMA ("
            + "     idpro integer primary key autoincrement,"
            + "     nbpro integer not null,"
            + "     transpro integer null,"
            + "     mttpro integer not null,"
            + "     iduser integer not null,"
            + "     syncstatus integer"
            + ")";

    String Crea_Commander = "create table COMMANDER ("
            + "     idpro integer not null,"
            + "     idprod text not null,"
            + "     qtcmd integer not null,"
            + "     prixcmd integer not null,"
            + "     mtlcmd integer not  null,"
            + "     syncstatus integer"
            + ")";

    String Crea_Deal = "create table DEAL ("
            + "     iddeal integer primary key autoincrement,"
            + "     descdeal text not null,"
            + "     imgdeal text not null,"
            + "     datedeal text not null,"
            + "     telappel text  null,"
            + "     telwhatapp text not null,"
            + "     catdeal text not null,"
            + "     idclt integer"
            + ")";
    String Crea_Ville = "create table VILLE ("
            + "     idville integer primary key,"
            + "     nomville text not null"
            + ")";
    String Crea_Quartier = "create table QUARTIER ("
            + "     idqtr integer primary key autoincrement,"
            + "     nomqtr text not null,"
            + "     tarif integer not null,"
            + "    idville integer not null"
            + ")";

    public DB_AHITCHE(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Crea_User);
        db.execSQL(Crea_cat);
        db.execSQL(Crea_Partenaire);
        db.execSQL(Crea_Produit);
        db.execSQL(Crea_Demander);
        db.execSQL(Crea_Pannier);
        db.execSQL(Crea_Proforma);
        db.execSQL(Crea_Commander);

        db.execSQL(Crea_Deal );
        db.execSQL(Crea_Ville);
        db.execSQL(Crea_Quartier);
    }

    public void UpdateLocalCategorie(String libcat,int sync_status,SQLiteDatabase database){
        ContentValues contentValues=new ContentValues();
        contentValues.put("syncstatus",sync_status);
        String selection="libcat LIKE ?";
        String[] selection_args={libcat};
        database.update("CATEGORIE",contentValues,selection,selection_args);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIE");
        db.execSQL("DROP TABLE IF EXISTS PARTENAIRE");
        db.execSQL("DROP TABLE IF EXISTS PRODUIT");
        db.execSQL("DROP TABLE IF EXISTS DEMANDER");
        db.execSQL("DROP TABLE IF EXISTS PANNIER");
        db.execSQL("DROP TABLE IF EXISTS PROFORMA");
        db.execSQL("DROP TABLE IF EXISTS COMMANDER");

        db.execSQL("DROP TABLE IF EXISTS DEAL");
        db.execSQL("DROP TABLE IF EXISTS VILLE");
        db.execSQL("DROP TABLE IF EXISTS QUARTIER");
        onCreate(db);

    }

    //Procedure d'enregistrement d'une Ville
   public boolean insert_Ville(int idville,String nomville){
           try {
                    ContentValues contentValues=new ContentValues();
               contentValues.put("idville",idville);
                    contentValues.put("nomville",nomville);
                    this.getWritableDatabase().insertOrThrow("VILLE","",contentValues);
                }catch (Exception e){
                }
                return true;
            }

    //Procedure d'enregistrement d'une Quartier
    public boolean insert_Quartier(int idqtr,String nomqtr,int tarif,int idville) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("idqtr", idqtr);
            contentValues.put("nomqtr", nomqtr);
            contentValues.put("tarif", tarif);
            contentValues.put("idville", idville);
            this.getWritableDatabase().insertOrThrow("QUARTIER", "", contentValues);
        } catch (Exception e) {
        }
        return true;
    }
    // Procedure d'enregistrent d'utilisateur
    public boolean insert_User(String nomuser, String preuser, String teluser , String emailuser, String imguser, String datinsuser){
      try {


        ContentValues contentValues=new ContentValues();
        contentValues.put("nomuser",nomuser);
        contentValues.put("preuser",preuser);
        contentValues.put("teluser",teluser);
        contentValues.put("emailuser",emailuser);
        contentValues.put("imguser",imguser);
        contentValues.put("datinsuser", datinsuser);

        this.getWritableDatabase().insertOrThrow("USER","",contentValues);
      }catch (Exception e){

      }
        return true;
    }
    //Procedure de Modification de l'utilisateur
    public boolean  update_User(String nomuser, String preuser, String teluser , String emailuser, String imguser, String datinsuser, Integer iduser) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("nomuser",nomuser);
            contentValues.put("preuser",preuser);
            contentValues.put("teluser",teluser);
            contentValues.put("emailuser",emailuser);
            contentValues.put("imguser",imguser);
            contentValues.put("datinsuser", datinsuser);
            db.update("USER", contentValues, "iduser=?", new String[]{String.valueOf(iduser)});

        } catch (SQLiteException ex) {

            //  Toast.makeText(SQLiteDATABASE.this, , Toast.LENGTH_LONG).show();
        }
        return true;
    }

    //Procedure d'enregistrement d'un produit
    public boolean insert_Produit(int idprod, String deprod, Integer prixprod, String imgprod , String poidprod, String dimprod, Integer idpart,Integer syncstatus) {
        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put("idprod", idprod);
            contentValues.put("deprod", deprod);
            contentValues.put("prixprod", prixprod);
            contentValues.put("imgprod", imgprod);
            contentValues.put("poidprod", poidprod);
            contentValues.put("dimprod", dimprod);
            contentValues.put("idpart", idpart);
            contentValues.put("syncstatus", syncstatus);

            this.getWritableDatabase().insertOrThrow("PRODUIT", "", contentValues);
        } catch (Exception e) {

        }
        return true;
    }

//Procedure pour selectionner l'item à modifier dans la table produit
    public Cursor selectforupdate( int idprod){
        try {
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select p.*,idprod as _id  from PRODUIT p WHERE _id="+ idprod +"";
            Cursor cursor= db.rawQuery(query,null);
            return cursor;
        } catch (SQLiteException ex) {
            return null;
        }
    }


    //Procedure de Modification d'un Produit
    public boolean  update_Produit(String deprod, Integer prixprod, String imgprod , String poidprod, String dimprod, Integer idpart, Integer idprod) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("deprod", deprod);
            contentValues.put("prixprod", prixprod);
            contentValues.put("imgprod", imgprod);
            contentValues.put("poidprod", poidprod);
            contentValues.put("dimprod", dimprod);
            contentValues.put("idpart", idpart);
            db.update("PRODUIT", contentValues, "idprod=?", new String[]{String.valueOf(idprod)});

        } catch (SQLiteException ex) {

            //  Toast.makeText(SQLiteDATABASE.this, , Toast.LENGTH_LONG).show();
        }
        return true;
    }

    //Procedure d'enregistrement d'un produit
    public boolean insert_Partenaire(String depart, String villpart, String quapart , String raispart, String emailpart, String telpart,String houvert,String hferme){
       try {

        ContentValues contentValues=new ContentValues();
        contentValues.put("depart",depart);
        contentValues.put("villpart",villpart);
        contentValues.put("quapart",quapart);
        contentValues.put("raispart",raispart);
        contentValues.put("emailpart",emailpart);
        contentValues.put("telpart", telpart);
        contentValues.put("houvert", houvert);
        contentValues.put("hferme", hferme);

        this.getWritableDatabase().insertOrThrow("PARTENAIRE","",contentValues);
       } catch (Exception e) {

       }
        return true;
    }
    //Procedure de Modification d'un Partenaire
    public boolean  update_Partenaire(String depart, String villpart, String quapart , String raispart, String emailpart, String telpart,String houvert,String hferme, Integer idpart) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("depart",depart);
            contentValues.put("villpart",villpart);
            contentValues.put("quapart",quapart);
            contentValues.put("raispart",raispart);
            contentValues.put("emailpart",emailpart);
            contentValues.put("telpart", telpart);
            contentValues.put("houvert", houvert);
            contentValues.put("hferme", hferme);
            db.update("PARTENAIRE", contentValues, "idpart=?", new String[]{String.valueOf(idpart)});

        } catch (SQLiteException ex) {

            //  Toast.makeText(SQLiteDATABASE.this, , Toast.LENGTH_LONG).show();
        }
        return true;
    }

    //Procedure d'enregistrement d'une demande(pannier)
    public boolean insert_Demander(Integer idprod, Integer idpan,Integer qtdmd, Integer prixpan , Integer mtlpan){
       try {
// nbprod integer not null
        ContentValues contentValues=new ContentValues();
        contentValues.put("idprod",idprod);
        contentValues.put("idpan",idpan);
            contentValues.put("qtdmd",qtdmd);
        contentValues.put("prixpan",prixpan);
        contentValues.put("mtlpan",mtlpan);

        this.getWritableDatabase().insertOrThrow("DEMANDER","",contentValues);
       } catch (Exception e) {
                    return false;
                 }
        return true;
    }

    //Procedure d'enregistrement d'un panier
    public boolean insert_Pannier(Integer idpan ,Integer mttpan, Integer nbprod , Integer iduser,Integer statpan){
        try {


    ContentValues contentValues=new ContentValues();
   contentValues.put("idpan",idpan);
        contentValues.put("mttpan",mttpan);
        contentValues.put("nbprod",nbprod);
        contentValues.put("iduser",iduser);
            contentValues.put("statpan",statpan);

        this.getWritableDatabase().insertOrThrow("PANNIER","",contentValues);
        } catch (Exception e) {

        }
        return true;
    }

   //Procedure d'enregistrement d'une proforma
    public boolean insert_Proforma(Integer ipro, Integer nbpro, Integer transpro , Integer mttpro, Integer iduser){
        try {


        ContentValues contentValues=new ContentValues();
        contentValues.put("ipro",ipro);
        contentValues.put("nbpro",nbpro);
        contentValues.put("transpro",transpro);
        contentValues.put("mttpro",mttpro);
        contentValues.put("iduser",iduser);

        this.getWritableDatabase().insertOrThrow("PROFORMA","",contentValues);
        } catch (Exception e) {

        }
        return true;
    }

    //Procedure d'enregistrement d'une proforma
    public boolean insert_Commander(Integer ipro, Integer idprod, Integer qtcmd , Integer prixcmd, Integer mtlcmd){
        try {

        ContentValues contentValues=new ContentValues();
        contentValues.put("ipro",ipro);
        contentValues.put("idprod",idprod);
        contentValues.put("qtcmd",qtcmd);
        contentValues.put("prixcmd",prixcmd);
        contentValues.put("mtlcmd",mtlcmd);

        this.getWritableDatabase().insertOrThrow("COMMANDER","",contentValues);
        } catch (Exception e) {

        }
        return true;
    }

    //Recuperation des données de la table CATEGORIE
    public ArrayList<Modele_Categorie> getallcat(){

        ArrayList<Modele_Categorie> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery( "select libcat, idcat as _id from CATEGORIE",null);
        while (cursor.moveToNext()){

            String nomcat=cursor.getString(0);
          //  String descat=cursor.getString(1);
            int idcat=cursor.getInt(1);
            Modele_Categorie modele_categorie=new Modele_Categorie(idcat,nomcat);
            arrayList.add(modele_categorie);

        }
        return arrayList;
    }

    //Recuperation des données de la table PRODUIT
    public ArrayList<Modele_Article> getallArt(Integer idpart){

        ArrayList<Modele_Article> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery( "select DISTINCT deprod, prixprod,imgprod,idprod from PRODUIT where idpart="+idpart,null);
        while (cursor.moveToNext()){

            String deprod=cursor.getString(0);
            //  String descat=cursor.getString(1);
            int prixprod=cursor.getInt(1);
            String imgprod =cursor.getString(2);
            int idprod=cursor.getInt(3);
            Modele_Article modele_article=new Modele_Article(prixprod,idprod,deprod,imgprod);
            arrayList.add(modele_article);

        }
        return arrayList;
    }
    public  Cursor getAllPatisserie(Integer idp){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "select deprod, prixprod,imgprod,idprod from PRODUIT WHERE idpart="+ idp;
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }
    //Enregistrement dans la table CATEGORIE
    public void  insert_cat(String libcat,int syncstatus) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("libcat", libcat);
        contentValues.put("syncstatus",syncstatus);

        this.getWritableDatabase().insertOrThrow("CATEGORIE", "", contentValues);
}

public Cursor recupcatlocal(SQLiteDatabase db){
        String[] projection={"libcat","syncstatus"};
        return (db.query("CATEGORIE",projection,null,null,null,null,null));
}
    public Cursor recupprodlocal(SQLiteDatabase db){
        String[] projection={"deprod","prixprod","imgprod","poidprod","dimprod","idpart","syncstatus"};
        return (db.query("PRODUIT",projection,null,null,null,null,null));
    }
    public Cursor getUnsyncecat() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM CATEGORIE WHERE syncstatus = 1;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Integer silibcatexiste(String libcat) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT Count(*) FROM CATEGORIE WHERE libcat ="+ libcat + ";";
        Cursor c = db.rawQuery(sql, null);
        int nblib=c.getInt(1);
        return nblib;
    }
    public boolean updateCatStatus(int idcat, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("syncstatus", status);
        db.update("CATEGORIE", contentValues, "idcat" + "=" + idcat, null);
        db.close();
        return true;
    }

    public ArrayList<String> getAllCat(){
        ArrayList<String> nomcat=new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {

            String query = "SELECT idcat,libcat FROM CATEGORIE";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Integer id_cat =Integer.parseInt(cursor.getString(cursor.getColumnIndex("idcat")));
                    String nom_cat = cursor.getString(cursor.getColumnIndex("libcat"));

                    String nomcategorie =id_cat +" "+ nom_cat;
                    nomcat.add(nomcategorie);
                }
            }
            db.setTransactionSuccessful();
            //db.close();
        } catch (Exception e) {
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }finally {
            db.endTransaction();
            db.close();
        }
        return nomcat;
    }


    public int selmaxpanier(int iduser) {
        Integer max = 0;
       // try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select max(idpan) from PANNIER where statpan=0 and iduser="+iduser;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() >0 && cursor.moveToFirst()) {
                max = cursor.getInt(0);
            }
            if (max==0){
                return max+1;
            }else {
                return max;
            }
    }
    //Fonction de recuperation du panier en cours
    public int panClientencours(int iduser) {
        Integer idp = 0;
        // try {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select count(idpan) from PANNIER WHERE statpan=0 and iduser="+iduser;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() >0 && cursor.moveToFirst()) {
            idp = cursor.getInt(0);
        }
        // } catch (SQLiteException ex) {
        //            return 0;
        //        }

        return idp;
    }
    public int siprodexiste(int idpan,int idprod) {
        Integer idp = 0;
        // try {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select count(idprod) from DEMANDER WHERE idpan="+idpan +" and idprod="+idprod;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() >0 && cursor.moveToFirst()) {
            idp = cursor.getInt(0);

        }
        // } catch (SQLiteException ex) {
        //            return 0;
        //        }

        return idp;
    }
   // Fonction de comptage des articles du panier en cours
    public int cptelmtpanier(int idpan) {
        Integer idp = 0;
        // try {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select count(idprod) from DEMANDER WHERE idpan="+idpan;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() >0 && cursor.moveToFirst()) {
            idp = cursor.getInt(0);

        }
        // } catch (SQLiteException ex) {
        //            return 0;
        //        }

        return idp;
    }

    public  Cursor getAllPanier(Integer idpan){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "select idpan, p.idprod,deprod,imgprod,prixpan,qtdmd,poidprod from PRODUIT p,DEMANDER d WHERE p.idprod=d.idprod and idpan="+ idpan;
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }
    public  Cursor localPanierToServer(Integer idpan){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "select * from DEMANDER WHERE idpan="+ idpan;
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }
    public int getTotPanier(Integer idpan){
        int totpan=0;
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "select sum(prixpan*qtdmd) from DEMANDER WHERE idpan="+ idpan;
        Cursor cursor= db.rawQuery(query,null);
        if (cursor.getCount() >0 && cursor.moveToFirst()) {
            totpan = cursor.getInt(0);

        }
        return totpan;
    }
    public  Cursor getAllVille(SQLiteDatabase db){
        String[] projection={"idville","nomville"};
        return (db.query("VILLE",projection,null,null,null,null,null));
    }
    public  Cursor getAllVille2(String search){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "select idville, nomville from VILLE where nomville like '%"+search+"%'";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }
    public  Cursor getAllQuartier(String search,int idville){
        SQLiteDatabase db=this.getReadableDatabase();
        String query= "select idqtr, nomqtr,tarif,idville from QUARTIER where idville="+ idville +" and nomqtr like '%"+search+"%'";
        Cursor cursor= db.rawQuery(query,null);
        return cursor;
    }
}