package com.code_evolve_8472.springphotoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText register_email,register_password;
    private Button btn_register;
    private TextView sign_in;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private FirebaseUser userId;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        register_email = (EditText) findViewById(R.id.register_email);
        register_password = (EditText) findViewById(R.id.register_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        sign_in = (TextView) findViewById(R.id.sign_in);

        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(this);

    }
    private void registerUser(){
        String email = register_email.getText().toString().trim();
        String password = register_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter email address",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Enter password!",Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length() < 6){
            Toast.makeText(getApplicationContext(),"Password too short, enter minimum 6 characters!",Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Registering User Please wait...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,MainMenu.class));
                            //userInformation();
                            finish();
                        }else {
                            Toast.makeText(Register.this,"Could not register.please try again",Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void userInformation(){
        String user = auth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference("users").child(user);

    }

    @Override
    public void onClick(View v) {
        if(btn_register == v){
            registerUser();
        }
        else if(sign_in == v){
            startActivity(new Intent(this,Login.class));
        }
    }
}
