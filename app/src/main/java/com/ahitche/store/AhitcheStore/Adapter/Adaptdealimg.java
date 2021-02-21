package com.ahitche.store.AhitcheStore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahitche.store.AhitcheStore.Modeles.Modele_dealimg;
import com.ahitche.store.AhitcheStore.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptdealimg extends RecyclerView.Adapter<Adaptdealimg.DealViewHolder> {
    private Context context;
    Dialog pubdialog;
    Bitmap bitmap;
    private ArrayList<Modele_dealimg> arrayList;
    public Adaptdealimg( ArrayList<Modele_dealimg> arrayList,Context context){
        this.arrayList=arrayList;
        this.context=context;

    }

    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_imgdeal,parent,false);

        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        holder.lienimag.setText(String.valueOf(arrayList.get(position).getLienimg()));
        bitmap = BitmapFactory.decodeFile(holder.lienimag.getText().toString());
       // holder.imgdeal.setImageBitmap(bitmap);
       Glide.with(context)
                      .load(arrayList.get(position).getLienimg()).into(holder.imgdeal);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  static class DealViewHolder extends RecyclerView.ViewHolder{
        public TextView lienimag;
        public ImageView imgdeal;

        private RelativeLayout pub_item;
        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            //pub_item=(RelativeLayout) itemView.findViewById(R.id.pubitem_id);

            lienimag=(TextView)itemView.findViewById(R.id.lienimg);
            imgdeal=(ImageView) itemView.findViewById(R.id.imgitem);



        }
    }
}


