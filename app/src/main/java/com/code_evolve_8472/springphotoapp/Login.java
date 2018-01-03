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

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText login_email,login_password;
    private Button btn_login;
    private TextView sign_up,pass_recover;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        sign_up = (TextView) findViewById(R.id.sign_up);
        pass_recover = (TextView) findViewById(R.id.pass_recover);
        progressDialog = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,MainMenu.class));
        }

        btn_login.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        pass_recover.setOnClickListener(this);
    }

    private void userLogin(){
        String email = login_email.getText().toString().trim();
        final String password = login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter email address!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Loading please wait...");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(!task.isSuccessful()){
                            if(password.length() < 6){
                                login_password.setError("Password too short ,enter minimum 6 characters!");
                            }else {
                                Toast.makeText(Login.this,"Authentication failed,check your email or password or sign again",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            startActivity(new Intent(Login.this,MainMenu.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        if(btn_login == v){
            userLogin();
        }
        else if(sign_up == v){
            startActivity(new Intent(this,Register.class));
            finish();
        }
        else if(pass_recover == v){
            startActivity(new Intent(this,ResetPassword.class));
            finish();
        }

    }
}
