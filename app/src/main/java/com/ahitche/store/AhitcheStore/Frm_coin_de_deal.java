package com.ahitche.store.AhitcheStore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahitche.store.AhitcheStore.Adapter.Adaptdeal;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Modele_deal;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Frm_coin_de_deal extends Fragment {
    RecyclerView rv;

    ArrayList<Modele_deal> arrayList;
    DB_AHITCHE db;
    Cursor c;
    List<Adaptdeal> deal_list;
    TextView idclt;
    GridView listdeal;
    private ProgressDialog loading;
Button ajoudeal;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View rootview=inflater.inflate(R.layout.fgm_coin_de_deal,container,false);
  rv=rootview.findViewById(R.id.topdeal);
      arrayList=new ArrayList<>();
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity());
        deal_list=new ArrayList<>();
        rv.setLayoutManager(layoutManager);
        ajoudeal=rootview.findViewById(R.id.btn_newdeal);
selectdeal();
        listdeal=rootview.findViewById(R.id.dealist);

        ajoudeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Deal_insert.class);
                startActivity(intent);
            }
        });
        return rootview;
    }


    private void selectdeal() {

        loading = ProgressDialog.show(getActivity(),"Patientez svp...","Chargement...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_SELECT_DEAL,
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
                                JSONObject deal = array.getJSONObject(i);

                              arrayList.add(new Modele_deal(
                                        deal.getString("titdeal"),
                                        deal.getString("tofs"),
                                        deal.getString("datdeal"),
                                        deal.getString("idcat"),
                                        deal.getString("locdeal"),
                                        deal.getInt("prixdeal"),
                                      deal.getInt("iduser"),
                                      deal.getInt("iddeal")
                                ));
                               // db.insert_Produit(deal.getInt("idprod"),product.getString("deprod"),product.getInt("prixprod"),product.getString("imgprod"),product.getString("poidprod"),product.getString("dimprod"),product.getInt("idpart"),DbContract.SYNC_STATUS_OK);
                                //Toast.makeText(getApplicationContext(), "enregistrement effectué", Toast.LENGTH_LONG).show();
                            }

                            //creating adapter object and setting it to recyclerview
                            Adaptdeal adapter = new Adaptdeal(getActivity(), arrayList);

                            // recyclerView.setAdapter(adapter);
                            //  AdaptPatis adaptPatis=new AdaptPatis(Diverselectro.this, arrayList);

                            listdeal.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    }