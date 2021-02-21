package com.ahitche.store.AhitcheStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class Seconnecter extends AppCompatActivity {


    SignInButton sign;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN=0;
    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;
    EditText number;
    Button send;


    //Déclaration des elements de Facebook

    TextView info;
    ImageView profilfb;
    LoginButton btn_login;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seconnecter);

        sign=findViewById(R.id.sign_in_button);

        //facebook
        info=findViewById(R.id.info);
        profilfb=findViewById(R.id.login_fc);
        btn_login=findViewById(R.id.login);

        //SMS
        send=findViewById(R.id.send_sms);
        number=findViewById(R.id.phone_number);
send.setEnabled(false);

if (checkPermission(Manifest.permission.SEND_SMS)){
    send.setEnabled(true);
}else {
    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
}
send.setOnClickListener(new Button.OnClickListener(){

    @Override
    public void onClick(View v) {
onSend(v);
    }
});
        callbackManager=CallbackManager.Factory.create();
        btn_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("Id Utilisateur:"+loginResult.getAccessToken().getUserId());
                String imageurl="https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Picasso.get().load(imageurl).into(profilfb);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent=new Intent(Seconnecter.this,Seconnecter2.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("erreur", "authentification:echac code=" + e.getStatusCode());
            
        }
    }
public void onSend(View v){
String phonenumber=number.getText().toString();
String SMS_MESSAGE="Votre code d'authentification DylMarket est";

if (phonenumber==null || phonenumber.length()==0 || SMS_MESSAGE==null|| SMS_MESSAGE.length()==0){

    return;
}
    if (checkPermission(Manifest.permission.SEND_SMS)){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phonenumber,null,SMS_MESSAGE,null,null);
        Toast.makeText(this,"Message envoyé!",Toast.LENGTH_LONG).show();
    }else{
        Toast.makeText(this,"Message non envoyé!",Toast.LENGTH_LONG).show();
    }
}
public boolean checkPermission(String permission){
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
}
}