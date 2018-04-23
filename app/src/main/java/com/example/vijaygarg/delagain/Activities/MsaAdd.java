package com.example.vijaygarg.delagain.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MsaAdd extends AppCompatActivity {

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msa_add);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("msa");


    }
}
