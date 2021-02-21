package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahitche.store.AhitcheStore.ArticleGridItem;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.Modeles.Model_panier;
import com.ahitche.store.AhitcheStore.Modeles.Modele_Article;
import com.ahitche.store.AhitcheStore.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class Adaptpanier extends RecyclerView.Adapter<Adaptpanier.PanierViewHolder> {

    Context context;
    ArrayList<Model_panier> arrayList;
    public Adaptpanier(Context context,ArrayList<Model_panier> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    public class PanierViewHolder extends RecyclerView.ViewHolder {

        public TextView idpan, idprod, deprod, imgprod,qtprod,prixpan,poidprod;
        public ImageView imgpan;

        public PanierViewHolder(@NonNull View itemView) {
            super(itemView);
            idpan=(TextView)itemView.findViewById(R.id.idpand);
            idprod=(TextView)itemView.findViewById(R.id.idprodp);
            deprod=(TextView)itemView.findViewById(R.id.deprod_pan);
            imgprod=(TextView)itemView.findViewById(R.id.imgpan);
            qtprod=(TextView)itemView.findViewById(R.id.qtdmd);
            prixpan=(TextView)itemView.findViewById(R.id.prixprod_pan);
            poidprod=(TextView)itemView.findViewById(R.id.poidprod);
            imgpan=(ImageView)itemView.findViewById(R.id.img_pand);

            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int id_pan= Integer.parseInt(idpan.getText().toString());
                    int idprod_pan=Integer.parseInt(idprod.getText().toString());
                    String deprod_pan=deprod.getText().toString();
                    String imgprod_pan=imgprod.getText().toString();
                    String qtprod_pan=qtprod.getText().toString();
                    String prix_pan=prixpan.getText().toString();
                   // Context context = v.getContext();
                    //                    Intent intent = new Intent(context, ArticleGridItem.class);
                    //                    intent.putExtra("nom",deprod);
                    //                    intent.putExtra("idp", idprod);
                    //                    intent.putExtra("prix",prixpro);
                    //                    intent.putExtra("image",imgprod);
                    //                    context.startActivity(intent);
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
    public PanierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.panier_row,parent,false);
        return new PanierViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptpanier.PanierViewHolder holder, int position) {
        Model_panier model_panier=arrayList.get(position);

        holder.idpan.setText(String.valueOf(model_panier.getIdpan()));
        holder.idprod.setText(String.valueOf(model_panier.getIdprod()));
        holder.deprod.setText(model_panier.getDeprod());
        holder.imgprod.setText(DbContract.SERVER_IMAGE_PATH+model_panier.getImgprod());
        holder.qtprod.setText(String.valueOf(model_panier.getQtdmd()));
        holder.prixpan.setText(String.valueOf(model_panier.getPrixpan())+" FCFA");
        holder.poidprod.setText(model_panier.getPoidprod());

        // https://assetsnffrgf-a.akamaihd.net/assets/m/502013275/univ/art/502013275_univ_lss_lg.jpg
        loadimage(holder.imgprod.getText().toString(),holder.imgpan);


    }

    public int getItemCount() {
        return arrayList.size();
    }

}
