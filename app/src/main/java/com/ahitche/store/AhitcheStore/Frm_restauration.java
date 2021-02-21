package com.ahitche.store.AhitcheStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahitche.store.AhitcheStore.Articles.Boissons;
import com.ahitche.store.AhitcheStore.Articles.Metbenin;
import com.ahitche.store.AhitcheStore.Articles.Meteurop;
import com.ahitche.store.AhitcheStore.Articles.MetsAfrique;
import com.ahitche.store.AhitcheStore.Articles.Patisserie;
import com.ahitche.store.AhitcheStore.Articles.Petitdej;
import com.ahitche.store.AhitcheStore.Articles.Television;

public class Frm_restauration extends Fragment {
    RelativeLayout layptidej, laypat, laymetafric, laymateu, laybois, laymetben;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fgm_restauration,container,false);
        View rootview = inflater.inflate(R.layout.fgm_restauration, container, false);
        layptidej = rootview.findViewById(R.id.layptitdej);
        laypat = rootview.findViewById(R.id.laypat);
        laymetafric = rootview.findViewById(R.id.laymatafric);
        laymateu = rootview.findViewById(R.id.laymeteurop);
        laybois= rootview.findViewById(R.id.layBOIS);
        laymetben = rootview.findViewById(R.id.laymetBEN);

        layptidej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Petitdej.class);
                startActivity(intent);
            }
        });
        laypat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Patisserie.class);
                startActivity(intent);
            }
        });
        laymetafric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MetsAfrique.class);
                startActivity(intent);
            }
        });
        laymateu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Meteurop.class);
                startActivity(intent);
            }
        });
        laybois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boissons.class);
                startActivity(intent);
            }
        });
        laymetben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Metbenin.class);
                startActivity(intent);
            }
        });

        return rootview;
    }
}
