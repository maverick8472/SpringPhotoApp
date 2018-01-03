package com.code_evolve_8472.springphotoapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener{

    private EditText reset_email;
    private Button btnReset;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        reset_email = (EditText) findViewById(R.id.reset_email);
        btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }

    private void resetPassword(){
        String email = reset_email.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Enter your registered email id",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Sending verification email please wait ....");
        progressDialog.show();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this,"We have sent you instructions to reset your password!",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ResetPassword.this,"Failed to send reset email",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(btnReset == v){
            resetPassword();
        }
    }
}
