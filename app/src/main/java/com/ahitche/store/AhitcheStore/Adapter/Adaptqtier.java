package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ahitche.store.AhitcheStore.Modeles.Model_qtr;
import com.ahitche.store.AhitcheStore.Modeles.Modele_quartier;
import com.ahitche.store.AhitcheStore.R;

import java.util.ArrayList;

public class Adaptqtier extends BaseAdapter {
    Context context;
    ArrayList<Model_qtr> arrayList;

    public Adaptqtier(Context context, ArrayList<Model_qtr> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.quartierrow, null);
        TextView nomqtier = (TextView) convertView.findViewById(R.id.nomqtr);
        TextView nomvil = (TextView) convertView.findViewById(R.id.nomvil);
        Button btn_avatar=(Button)convertView.findViewById(R.id.avatarqtr);

        Model_qtr model_qtr = arrayList.get(position);
        nomqtier.setText(arrayList.get(position).getNomqtr());
        nomvil.setText(arrayList.get(position).getNomvil());


        String str = nomqtier.getText().toString();

        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();

//First name
        if (strArray.length > 0){
            builder.append(strArray[0], 0, 1);
        }
        btn_avatar.setText(builder.toString());

        return convertView;
    }
}
