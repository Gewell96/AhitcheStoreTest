package com.ahitche.store.AhitcheStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import com.ahitche.store.AhitcheStore.Articles.Article_insert;
import com.ahitche.store.AhitcheStore.Articles.Articles;
import com.ahitche.store.AhitcheStore.Articles.Climatiseur;
import com.ahitche.store.AhitcheStore.Articles.Diverselectro;
import com.ahitche.store.AhitcheStore.Articles.Fer_a_repasser;
import com.ahitche.store.AhitcheStore.Articles.Patisserie;
import com.ahitche.store.AhitcheStore.Articles.Television;
import com.ahitche.store.AhitcheStore.Categorie.Categories;

public class frm_electromenager extends Fragment {

    RelativeLayout laycat,layart,laypubimg, laylave,layfer,laydiverelect;
  @Nullable
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          //return inflater.inflate(R.layout.fgm_electromenager,container,false);

          View rootview=inflater.inflate(R.layout.fgm_electromenager,container,false);
          laycat=rootview.findViewById(R.id.laypubvid);
      layart=rootview.findViewById(R.id.layoutcat);
      laypubimg=rootview.findViewById(R.id.laypubimg);
      laylave=rootview.findViewById(R.id.laylave);
      layfer=rootview.findViewById(R.id.layfer);
      laydiverelect=rootview.findViewById(R.id.laydiverelct);
          laycat.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(getActivity(), Television.class);
                   startActivity(intent);
              }
          });
      layart.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), Articles.class);
              startActivity(intent);
          }
      });
      laypubimg.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), Climatiseur.class);
              startActivity(intent);
          }
      });

      laylave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), Lavelinge.class);
              startActivity(intent);
          }
      });
      layfer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), Fer_a_repasser.class);
              startActivity(intent);
          }
      });
      laydiverelect.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(getActivity(), Diverselectro.class);
              startActivity(intent);
          }
      });
          return rootview;
     }
// @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fgm_electromenager);
//        laycat=findViewById(R.id.laypubvid);
//
//         Intent intent = new Intent(getApplicationContext(), Categories.class);
//                      startActivity(intent);
//                     finish();
//    }


}
