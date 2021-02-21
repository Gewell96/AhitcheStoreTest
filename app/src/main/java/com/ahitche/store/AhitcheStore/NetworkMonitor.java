package com.ahitche.store.AhitcheStore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ahitche.store.AhitcheStore.Categorie.Categories;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_categorie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkMonitor extends BroadcastReceiver {
    private Context context;
    private DB_AHITCHE db;
    @Override
    public void onReceive(final   Context context, Intent intent) {
        this.context = context;
        db = new DB_AHITCHE(context);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
//if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//getting all the unsynced names
                Cursor cursor = db.getUnsyncecat();
                if (cursor.moveToFirst()) {
                    do {
                        saveName(cursor.getInt(cursor.getColumnIndex("idcat")),
                                cursor.getString(cursor.getColumnIndex("libcat"))

                        );
                        //Categories categories=new Categories();
                      //  categories.servercat( cursor.getString(cursor.getColumnIndex("libcat")));

                    } while (cursor.moveToNext());
                }
            }
        }
    }
    private void saveName(final int id, final String libcat) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
//updating the status in sqlite
                               // db.updateCatStatus(id, DbContract.SYNC_STATUS_OK);
//sending the broadcast to refresh the list
                               context.sendBroadcast(new Intent(DbContract.UI_UPDATE_BROADCAST));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("libcat", libcat);
                return params;
            }
        };
       Mysingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
          /*  if (siconnection(context)){
                 final DB_AHITCHE db_ahitche= new DB_AHITCHE(context);
                 final SQLiteDatabase database=db_ahitche.getWritableDatabase();
                Cursor cursor=db_ahitche.recupcatlocal(database);
                while (cursor.moveToNext())
                {
             int sync_status=cursor.getInt(cursor.getColumnIndex(DbContract.SYNC_STATUS));
            if (sync_status==DbContract.SYNC_STATUS_FAILED){
                 final String libcat=cursor.getString(cursor.getColumnIndex("libcat"));
                StringRequest stringRequest=new StringRequest(Request.Method.POST, DbContract.SERVER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);

                                    String Response=jsonObject.getString("response");
                                    if (Response.equals("ok")){
                                        db_ahitche.UpdateLocalCategorie(libcat,DbContract.SYNC_STATUS_OK,database);

                                        context.sendBroadcast(new Intent(DbContract.UI_UPDATE_BROADCAST));

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();


                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(NetworkMonitor.this, "Enregistrement  distant", Toast.LENGTH_LONG).show();
                    }
                })

    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String,String> params=new HashMap<>();
            params.put("libcat",libcat);

            return params;
        }
    }
            ;
    Mysingleton.getInstance(context).addToRequestQueue(stringRequest);
}
    }

    db_ahitche.close();
}

    }
    public boolean siconnection(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}*/
