package com.ahitche.store.AhitcheStore.DB_Contract;

public class DbContract {
    public static final int SYNC_STATUS_OK=0;
    public  static  final  int SYNC_STATUS_FAILED=1;
    public static final String SERVER_URL="https://dylmarket.com/astore/inscat.php";
    public static final String SERVER_URL_PRODUIT="https://dylmarket.com/astore/insertproduit.php";
    public static final String SERVER_URL_INSERT_PANIER="https://dylmarket.com/astore/insertpanier.php";
    public static final String SERVER_URL_SELECT_PRODUITT="https://dylmarket.com/astore/recuproduit.php?idpart=";
    public static final String SERVER_URL_PANIER_CLIENT_EN_COURS="https://dylmarket.com/astore/sileproduitexiste.php?iduser=";
    public static final String PANIER_CLIENT_EN_COURS_CHARGEMENT="https://dylmarket.com/astore/panClientencours.php?iduser=";
    public static final String PANIER_UPDATE="https://dylmarket.com/astore/updatepanier.php?idPAN=";
    public static final String SERVER_INSERT_VILLE="http://dylmarket.com/astore/insert_ville.php";
    public static final String SERVER_INSERT_QUARTIER="http://dylmarket.com/astore/insertquartier.php";
    public static final String SERVER_INSERT_DEAL="https://dylmarket.com/astore/insert_deal.php";
    public static final String SERVER_URL_INSERT_DEMANDER="https://dylmarket.com/astore/insertdemander.php";
    public static final String SERVER_SELECT_VILLE="http://dylmarket.com/astore/recupville.php";
    public static final String SERVER_RECUP_VILLE="https://dylmarket.com/astore/recupville.php";
   public static final String SERVER_RECUP_QUARTIER="https://dylmarket.com/astore/recupquartier.php";
    public static final String SERVER_IMAGE_PATH="https://dylmarket.com/astore/";
    public static final String SERVER_SELECT_QUARTIER ="http://dylmarket.com/astore/recupqtr.php" ;

    public static final String SERVER_SELECT_DEAL ="http://dylmarket.com/astore/select_deal.php" ;
    //http://localhost/astore/recuproduit.php?idpart=1
    //local ip: 192.168.43.59
    //web ip: 91.234.195.182
    public static final String UI_UPDATE_BROADCAST="SOMEACTION";
public  static  final String [] Etat={"Etat Neuf","Etat 10/10","Etat 9/10","Etat 8/10","Etat 7/10","Etat 6/10","Etat 5/10","Etat 4/10","Etat 3/10","Etat 2/10","Etat 1/10"};

    public static final String DB_NAME ="DB_AHITCHE";
    public static final String SYNC_STATUS="syncstatus";



}
