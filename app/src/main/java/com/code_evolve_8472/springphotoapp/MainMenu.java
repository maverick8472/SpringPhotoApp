package com.code_evolve_8472.springphotoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    private Button btn_sign_out;
    private Button btn_profile;
    private Button btn_gallery;
    private Button btn_capture_store_photo;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_capture_store_photo = (Button) findViewById(R.id.btn_Capture_Store_Photo);
        btn_profile = (Button) findViewById(R.id.btn_profile);
        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);

        auth = FirebaseAuth.getInstance();

        btn_capture_store_photo.setOnClickListener(this);
        btn_gallery.setOnClickListener(this);
        btn_sign_out.setOnClickListener(this);
        btn_profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(btn_capture_store_photo == v){
            startActivity(new Intent(MainMenu.this,StoreCapturePhoto.class));
        }
        else if(btn_gallery == v){
            startActivity(new Intent(MainMenu.this,Gallery.class));
        }
        else if(btn_profile == v){
            startActivity(new Intent(MainMenu.this,Profile.class));
        }
        else if(btn_sign_out == v) {
            finish();
            auth.signOut();
            startActivity(new Intent(MainMenu.this,Login.class));
        }
    }
}
