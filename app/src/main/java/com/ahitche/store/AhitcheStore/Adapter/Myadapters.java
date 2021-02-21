package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ahitche.store.AhitcheStore.DB_AHITCHE;
import com.ahitche.store.AhitcheStore.DB_Contract.DB_categorie;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Categorie;
import com.ahitche.store.AhitcheStore.R;

import java.util.ArrayList;

public class  Myadapters extends BaseAdapter {
Context context;
ArrayList<DB_categorie> arrayList;
    public Myadapters(Context context, ArrayList<DB_categorie> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.catrow,null);
             Button btn_avatar=(Button)convertView.findViewById(R.id.avatar);
            TextView libcat=(TextView)convertView.findViewById(R.id.Row_nomcat);



            DB_categorie db_categorie=arrayList.get(position);
            libcat.setText(arrayList.get(position).getLibcat());
           int sync_status=arrayList.get(position).getSync_status();

///
        String str = libcat.getText().toString();

        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();

//First name
        if (strArray.length > 0){
            builder.append(strArray[0], 0, 1);
        }
        btn_avatar.setText(builder.toString());


        ///

        if (sync_status== DbContract.SYNC_STATUS_OK)
        {
           // sync_status.set

        }

        return convertView;
    }
//    public void clicked(View view) {
//
//        String str = editText.getText().toString();
//
//        String[] strArray = str.split(" ");
//        StringBuilder builder = new StringBuilder();
//
////First name
//        if (strArray.length > 0){
//            builder.append(strArray[0], 0, 1);
//        }
////Middle name
//        if (strArray.length > 1){
//            builder.append(strArray[1], 0, 1);
//        }
////Surname
//        if (strArray.length > 2){
//            builder.append(strArray[2], 0, 1);
//        }
//
//        button.setText(builder.toString());
//    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }
}
