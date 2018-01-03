package com.code_evolve_8472.springphotoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.code_evolve_8472.springphotoapp.Helper.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {

    private GalleryAdapter adapter;
    private RecyclerView recyclerView;

    private DatabaseReference database;
    private List<Upload> images;

    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        images = new ArrayList<>();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String userId = user.getUid().toString();
        database = FirebaseDatabase.getInstance().getReference("users").child(userId);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    images.add(upload);
                }

                adapter = new GalleryAdapter(getApplicationContext(),images);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
