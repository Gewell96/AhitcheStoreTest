package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahitche.store.AhitcheStore.ArticleGridItem;
import com.ahitche.store.AhitcheStore.Articles.Article_insert;
import com.ahitche.store.AhitcheStore.Frm_coin_de_deal;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.Modeles.Modele_deal;
import com.ahitche.store.AhitcheStore.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Adaptdeal extends BaseAdapter {

    Context context;
    ArrayList<Modele_deal> arrayList;
    public TextView titdeal,imgdeal,duredeal,prixdeal,locdeal,iddeal,datdeal;
    public ImageView img_deal,img_profil;
    public Adaptdeal(Context context, ArrayList<Modele_deal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
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
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_deal,null);
        titdeal=(TextView)convertView.findViewById(R.id.vtitdeal);
        imgdeal=(TextView)convertView.findViewById(R.id.vimgdeal);
        duredeal=(TextView)convertView.findViewById(R.id.vdatdeal);
        prixdeal=(TextView)convertView.findViewById(R.id.vprixdeal);
        locdeal=(TextView) convertView.findViewById(R.id.vlocdeal);
        iddeal=(TextView)convertView.findViewById(R.id.viddeal);
        datdeal=(TextView)convertView.findViewById(R.id.vdatdeal);
        img_deal=(ImageView) convertView.findViewById(R.id.imgdeal);
        convertView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // int prixpro= Integer.parseInt(prixpat.getText().toString());
                //                    int idprod=Integer.parseInt(idpat.getText().toString());
                //                    String imgprod=impat.getText().toString();
                //                    String deprod=descpat.getText().toString();
                //                    Context context = v.getContext();
                //                    Intent intent = new Intent(context, ArticleGridItem.class);
                //                    intent.putExtra("nom",deprod);
                //                    intent.putExtra("idp", idprod);
                //                    intent.putExtra("prix",prixpro);
                //                    intent.putExtra("image",imgprod);
                //                    context.startActivity(intent);

            }
        });
                Modele_deal modele_deal  =arrayList.get(position);
                 titdeal.setText(String.valueOf(modele_deal.getTitdeal()));
                imgdeal.setText(split_str(modele_deal.getImgdeal()));
                datdeal.setText(modele_deal.getDatedeal());
                locdeal.setText(String.valueOf(modele_deal.getLocdeal()));
                iddeal.setText(String.valueOf(modele_deal.getIddeal()));
                prixdeal.setText(String.valueOf(modele_deal.getPrixdeal()));
                // https://assetsnffrgf-a.akamaihd.net/assets/m/502013275/univ/art/502013275_univ_lss_lg.jpg
                // loadimage(holder.imgdeal.getText().toString(),holder.img_deal);
                Glide.with(context).
                        load(imgdeal.getText().toString()).
                        into(img_deal);

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Context context = v.getContext();
                //                    Intent intent = new Intent(context, Article_insert.class);
                //                    context.startActivity(intent);
                return true;
                     }


        });
        return convertView;
    }
    public void loadimage(String url,ImageView image ){
        Picasso.get().load(url).into(image);
    }
    public String split_str( String str){
        String txt="";
        String recuptxt = str;
        String[] tabtxt = recuptxt.split(" ");
        txt= tabtxt[0];
        return txt;
    }
}
