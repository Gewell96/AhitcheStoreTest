package com.ahitche.store.AhitcheStore;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ahitche.store.AhitcheStore.Articles.Patisserie;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class Seconnecter2 extends AppCompatActivity {
    ImageView imageView;
    TextView name, email, id;
    Button signout;
    GoogleSignInClient mGoogleSignInClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seconnecter2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    imageView= findViewById(R.id.imageview);
    name=findViewById(R.id.textName);
    email=findViewById(R.id.textEmail);
    id=findViewById(R.id.textID);
    signout=findViewById(R.id.btn_connect);

    signout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // ...
                case R.id.btn_connect:
                    signOut();
                    break;
                // ...
            }
        }
    });



        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
           // String personGivenName = acct.getGivenName();
          //  String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }

}

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Seconnecter2.this, String.valueOf("Authentification réussie!"), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
