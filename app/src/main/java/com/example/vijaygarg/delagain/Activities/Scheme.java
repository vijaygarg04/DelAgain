package com.example.vijaygarg.delagain.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vijaygarg.delagain.Model.SchemeModel;
import com.example.vijaygarg.delagain.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Scheme extends AppCompatActivity {
EditText title,discription;
ImageView image;
Button upload;
Bitmap imagebitmap;
Uri imageuri;
Uri downloaduri;
ProgressDialog progressDialog;
private int PICK_IMAGE_REQUEST = 1;
DatabaseReference databaseReference;
    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
            title=findViewById(R.id.schemetitle);
            discription=findViewById(R.id.schemediscription);
            image=findViewById(R.id.schemeimageview);
            upload=findViewById(R.id.uploadscheme);
            progressDialog=new ProgressDialog(this);
            storage = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference();
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                 intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                   startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                }
            });

upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        final String stitle=title.getText().toString();
        final String sdesc=discription.getText().toString();
        if(stitle.length()==0){
            title.setError("Enter Title here");
            return;
        }
        if(sdesc.length()==0){
            discription.setError("Enter Discrpition Here");
            return;
        }
        if(imageuri==null){
            Toast.makeText(Scheme.this,"Upload Image",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.show();
        StorageReference filepath=storage.child(imageuri.getLastPathSegment());
        filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloaduri=taskSnapshot.getDownloadUrl();
                 Date da=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                String sdate=sdf.format(da).toString().trim();
                DatabaseReference mydr=databaseReference.child("schemes").child(sdate);
                SchemeModel schemeModel=new SchemeModel(downloaduri.toString(),sdesc,sdate,stitle,true);
                mydr.push().setValue(schemeModel);


                Toast.makeText(Scheme.this,"Successfully Uploaded",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Intent i =new Intent(Scheme.this,Welcome.class);
                startActivity(i);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Scheme.this,"Upload Failed Please Try Again",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }
});

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageuri = data.getData();

            try {
                 imagebitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                image.setImageBitmap(imagebitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
