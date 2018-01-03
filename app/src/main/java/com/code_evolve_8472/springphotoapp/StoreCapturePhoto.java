package com.code_evolve_8472.springphotoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.code_evolve_8472.springphotoapp.Helper.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class StoreCapturePhoto extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 48;
    private static final int CAMERA_REQUEST_CODE = 52;

    private ImageView imageView;
    private EditText pictureName;
    private Button btnChoosePhoto,btnUploadPhoto,btnCapturePhoto;

    private FirebaseAuth auth;
    private StorageReference storage;
    private DatabaseReference database;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_capture_photo);

        imageView = (ImageView) findViewById(R.id.imageView);
        pictureName = (EditText) findViewById(R.id.editText_pictre_name);
        btnChoosePhoto = (Button) findViewById(R.id.btn_choose_photo);
        btnUploadPhoto = (Button) findViewById(R.id.btn_upload_photo);
        btnCapturePhoto = (Button) findViewById(R.id.btn_capture_photo);

        btnChoosePhoto.setOnClickListener(this);
        btnUploadPhoto.setOnClickListener(this);
        btnCapturePhoto.setOnClickListener(this);

        storage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
    }
    private void fileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);
    }
    private void captureImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int id = requestCode;
        if(requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null){
            filePath = data.getData();
            Log.d("CHOSE", "IMAGE REQUEST!!!!!!!");
            String path = filePath.getPath();
            String fileName = path.substring(path.lastIndexOf(":")+1);
            pictureName.setText(fileName);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_REQUEST_CODE && requestCode == Activity.RESULT_OK){
            Log.d("CAMERA", "REQUEST CODE !!!!!!!!!!");

            //(requestCode == CAMERA_REQUEST_CODE && data != null && data.getData() != null)
            //if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                filePath = data.getData();
            String path = filePath.getPath();
            String fileName = path.substring(path.lastIndexOf(":")+1);
            //String fileName = "photo";
            pictureName.setText(fileName);
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
        }
        else{
            Log.d("REQUEST", "FAILED !!!!!!!!!!");
        }
    }
    private void uploadFile(){
        if(filePath != null){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference reverseRef = storage.child("images/" + pictureName.getText());
            reverseRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            String userId = auth.getCurrentUser().getUid().toString();
                            String pictureUrl = taskSnapshot.getDownloadUrl().toString();
                            String imageName = pictureName.getText().toString().trim();

                            Upload upload = new Upload(imageName,pictureUrl);
                            String uploadId = database.push().getKey();
                            database.child(userId).child(uploadId).setValue(upload);
                            progressDialog.dismiss();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(((int) progress) + "% Uploaded");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
        }

    }
    @Override
    public void onClick(View view) {
        if(btnCapturePhoto == view){
            captureImage();
        }
        else if(btnUploadPhoto == view){
            uploadFile();
        }
        else if(btnChoosePhoto == view){
            fileChooser();
        }
    }
}
