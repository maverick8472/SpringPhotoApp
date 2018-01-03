package com.code_evolve_8472.springphotoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {
    private Button btnChangeEmail,btnChangePassword,btnSendResetEmail,btnRemoveUser,
            changeEmail,changePassword,sendEmail,remove,btnsignOut;
    private EditText oldEmail,newEmail,oldPassword,newPassword;


    //private ProgressBar progressBar;

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    String TAG = "jure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                   startActivity(new Intent(Profile.this,Login.class));
                }else {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
            }
        };

        btnChangeEmail = (Button) findViewById(R.id.btn_profile_change_email);
        btnChangePassword = (Button) findViewById(R.id.btn_profile_change_password);
        btnSendResetEmail = (Button) findViewById(R.id.btn_profile_reset_password);
        btnRemoveUser = (Button) findViewById(R.id.btn_profile_remove_user);

        changeEmail = (Button) findViewById(R.id.profile_change_email);
        changePassword = (Button) findViewById(R.id.profile_change_password);
        sendEmail = (Button) findViewById(R.id.profile_send_email);
        remove = (Button) findViewById(R.id.profile_remove_user);

        oldEmail = (EditText) findViewById(R.id.profile_old_email);
        newEmail = (EditText) findViewById(R.id.profile_new_email);
        oldPassword = (EditText) findViewById(R.id.profile_old_password);
        newPassword = (EditText) findViewById(R.id.profile_new_password);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //if(progressBar != null) progressBar.setVisibility(View.GONE);

        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        oldEmail.setVisibility(View.GONE);
        newEmail.setVisibility(View.GONE);
        oldPassword.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);

        changeEmail.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        sendEmail.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);


        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.VISIBLE);
                oldPassword.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                if(user != null && !newEmail.getText().toString().trim().equals("")){
                    user.updateEmail(newEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Profile.this,"Email address is updated. Please sign in with new email id!",Toast.LENGTH_LONG).show();
                                        signOut();
                                        //progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(Profile.this,"Failed to update email!",Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "FAILED"+ user.getUid());
                                        //progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }else if(newEmail.getText().toString().trim().equals("")){
                    newEmail.setError("Enter email");
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                newEmail.setVisibility(View.GONE);
                oldPassword.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.VISIBLE);
                sendEmail.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                if(user != null && !newPassword.getText().toString().trim().equals("")){
                    if(newPassword.getText().toString().trim().length() < 6){
                        newPassword.setError("Password too short, enter minimum 6 characters!");
                        //progressBar.setVisibility(View.GONE);
                    }else{
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Profile.this,"Password is updated, sign in with new password!",Toast.LENGTH_LONG).show();
                                            signOut();
                                            //progressBar.setVisibility(View.GONE);
                                        }else{
                                            Toast.makeText(Profile.this,"Failed to update password",Toast.LENGTH_LONG).show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                }else if(newPassword.getText().toString().trim().equals("")){
                    newPassword.setError("Enter password!");
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.VISIBLE);
                newEmail.setVisibility(View.GONE);
                oldPassword.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                changeEmail.setVisibility(View.GONE);
                changePassword.setVisibility(View.GONE);
                sendEmail.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                if(!oldEmail.getText().toString().trim().equals("")){
                    auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Profile.this,"Reset password email email is sent!",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.GONE);

                                    }else {
                                        Toast.makeText(Profile.this,"Failed to send reset email",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });
                }else {
                    oldEmail.setError("Enter email!");
                    //progressBar.setVisibility(View.GONE);
                }

            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);
                if(user != null){
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Profile.this,"Your profile is deleted!",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Profile.this,Register.class));
                                        finish();
                                        //progressBar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(Profile.this,"Failed to delete your account!",Toast.LENGTH_LONG).show();
                                        //progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });
                }
            }
        });
    }

    private void signOut(){
        auth.signOut();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

}
