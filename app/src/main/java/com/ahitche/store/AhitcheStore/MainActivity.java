package com.ahitche.store.AhitcheStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ahitche.store.AhitcheStore.Articles.Articles;
import com.ahitche.store.AhitcheStore.Categorie.Categories;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private DrawerLayout drawer;
    TextView txt_iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header=navigationView.getHeaderView(0);
        txt_iduser=(TextView)header.findViewById(R.id.idclrlancemt);
        txt_iduser.setText("1");

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){

            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_electromenager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new frm_electromenager()).commit();

                break;
            case R.id.nav_patisserie:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frm_patisserie()).commit();

                Intent intent=new Intent(getApplicationContext(), Categories.class);
                startActivity(intent);

                break;
            case R.id.nav_restau:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frm_restauration()).commit();
                break;
            case R.id.nav_instal:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frm_installation()).commit();
                Intent i=new Intent(MainActivity.this, Seconnecter.class);
                startActivity(i);
                break;
            case R.id.nav_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frm_profil()).commit();
                break;
            case R.id.nav_deal:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Frm_coin_de_deal()).commit();
                break;
            case R.id.nav_email:
                Intent i2=new Intent(getApplicationContext(), Ville.class);
                startActivity(i2);
                break;
            case R.id.nav_send:
                Intent i3=new Intent(getApplicationContext(), Quartier.class);
                startActivity(i3);
                break;
        }
         drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
