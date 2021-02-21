package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ahitche.store.AhitcheStore.Modeles.Modele_quartier;
import com.ahitche.store.AhitcheStore.R;

import java.util.ArrayList;

public class AdaptQuartier extends BaseAdapter {
    Context context;
    ArrayList<Modele_quartier> arrayList;

    public AdaptQuartier(Context context, ArrayList<Modele_quartier> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
        public long getItemId ( int position){

            return position;
        }

        public Object getItem ( int position){

            return arrayList.get(position);
        }

        public View getView ( int position, View convertView, ViewGroup parent){


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.quartier_row, null);
            TextView idqtier = (TextView) convertView.findViewById(R.id.idqtrtxt);
            TextView nomqtier = (TextView) convertView.findViewById(R.id.nomqtiertxt);
            TextView tarif = (TextView) convertView.findViewById(R.id.tariftxt);
            TextView idville = (TextView) convertView.findViewById(R.id.idvilletxt);


            Modele_quartier modele_quartier = arrayList.get(position);
            idqtier.setText(Integer.toString(modele_quartier.getIdqtr()));
            nomqtier.setText(arrayList.get(position).getNomqtr());
            tarif.setText(Integer.toString(modele_quartier.getTarif()));
            idville.setText(Integer.toString(modele_quartier.getIdville()));
            return convertView;
        }


        public int getCount () {
            return this.arrayList.size();
        }
    }

