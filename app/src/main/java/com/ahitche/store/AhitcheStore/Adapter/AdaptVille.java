package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ahitche.store.AhitcheStore.DB_Contract.DB_categorie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Modele_ville;
import com.ahitche.store.AhitcheStore.R;

import java.util.ArrayList;

public class AdaptVille extends BaseAdapter {
    Context context;
    ArrayList<Modele_ville> arrayList;

    public AdaptVille(Context context, ArrayList<Modele_ville> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    public long getItemId(int position) {

        return position;
    }

    public Object getItem(int position) {

        return arrayList.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.villerow,null);
        TextView nomville=(TextView)convertView.findViewById(R.id.nomvilletxt);
        TextView idville=(TextView)convertView.findViewById(R.id.idvil);



        Modele_ville modele_ville=arrayList.get(position);
        nomville.setText(modele_ville.getNomville());
        idville.setText(Integer.toString(modele_ville.getIdville()));
        return convertView;
    }


    public int getCount() {
        return this.arrayList.size();
    }
}
