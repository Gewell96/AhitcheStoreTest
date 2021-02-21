package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahitche.store.AhitcheStore.ArticleGridItem;
import com.ahitche.store.AhitcheStore.Articles.Article_insert;
import com.ahitche.store.AhitcheStore.Articles.Article_update;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.R;
import com.ahitche.store.AhitcheStore.RecyclerviewClickInterface;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class AdaptPatis extends RecyclerView.Adapter<AdaptPatis.PubViewHolder> {
    Context context;
    ArrayList<Modele_Article> arrayList;
    public AdaptPatis(Context context,ArrayList<Modele_Article> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }


    public class PubViewHolder extends RecyclerView.ViewHolder {

        public TextView idpat,descpat,impat,prixpat;
        public ImageView imgpat;

        public PubViewHolder(View itemView) {
            super(itemView);
            idpat=(TextView)itemView.findViewById(R.id.idprod_pat);
             descpat=(TextView)itemView.findViewById(R.id.deprod_pat);
             impat=(TextView)itemView.findViewById(R.id.imgprod_pat);
             prixpat=(TextView)itemView.findViewById(R.id.prixprod_pat);
             imgpat=(ImageView)itemView.findViewById(R.id.imr_pat);
             itemView.setOnClickListener(new View.OnClickListener() {


                 @Override
                 public void onClick(View v) {
                     int prixpro= Integer.parseInt(prixpat.getText().toString());
                   int idprod=Integer.parseInt(idpat.getText().toString());
                     String imgprod=impat.getText().toString();
                     String deprod=descpat.getText().toString();
                     Context context = v.getContext();
                     Intent intent = new Intent(context, ArticleGridItem.class);
                     intent.putExtra("nom",deprod);
                    intent.putExtra("idp", idprod);
                     intent.putExtra("prix",prixpro);
                     intent.putExtra("image",imgprod);
                     context.startActivity(intent);
                 }
             });
             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     Context context = v.getContext();
                     Intent intent = new Intent(context, Article_insert.class);
                     context.startActivity(intent);
                     return true;
                 }
             });
        }
    }
    public void loadpathimage(String path, ImageView image) {
        File fil = new File(path);
        Picasso.get().load(fil).fit().into(image);
    }
    public void loadimage(String url,ImageView image ){
        Picasso.get().load(url).into(image);
    }

    @NonNull
    @Override
    public PubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.patisserue_row,parent,false);
        return new PubViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PubViewHolder holder, int position) {
        Modele_Article modele_article=arrayList.get(position);

       holder.idpat.setText(String.valueOf(modele_article.getIdprod()));
        holder.descpat.setText(modele_article.getDeprod());
        holder.impat.setText(modele_article.getImgprod());
        holder.prixpat.setText(String.valueOf(modele_article.getPrixprod()));
        // https://assetsnffrgf-a.akamaihd.net/assets/m/502013275/univ/art/502013275_univ_lss_lg.jpg
        loadimage(holder.impat.getText().toString(),holder.imgpat);


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
