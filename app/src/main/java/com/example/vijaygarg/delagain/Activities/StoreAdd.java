package com.example.vijaygarg.delagain.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vijaygarg.delagain.Model.StoreModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StoreAdd extends AppCompatActivity {
EditText storeid,storename;
Button addstore,liststore;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_add);
        storeid=findViewById(R.id.storeid);
        storename=findViewById(R.id.storename);
        addstore=findViewById(R.id.btnaddstore);
        liststore=findViewById(R.id.btnliststore);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        addstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sstorename,sstoreid;
                sstorename=storename.getText().toString();
                sstoreid=storeid.getText().toString();

                StoreModel sm=new StoreModel(sstorename,sstoreid);
                DatabaseReference mydb=databaseReference.child("storeinfo");
                mydb.child(sstoreid).setValue(sm);

            }
        });
        liststore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StoreAdd.this,StoreList.class));
            }
        });
    }
}
