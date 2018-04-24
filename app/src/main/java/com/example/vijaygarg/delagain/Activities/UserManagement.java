package com.example.vijaygarg.delagain.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vijaygarg.delagain.Model.PromoterModel;
import com.example.vijaygarg.delagain.Model.StoreModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserManagement extends AppCompatActivity {

EditText name,id,store,contact,date;
Button add,update;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        name=findViewById(R.id.promotername);
        id=findViewById(R.id.userid);
        store=findViewById(R.id.storeassigned);
        contact=findViewById(R.id.contactnumber);
        date=findViewById(R.id.dateofjoin);
        add=findViewById(R.id.btnadd);
        update=findViewById(R.id.btnUpdate);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("promoterinfo");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference myb=databaseReference.child(id.getText().toString());
                final PromoterModel promoterModel=new PromoterModel(name.getText().toString(),id.getText().toString(),contact.getText().toString(),date.getText().toString(),store.getText().toString());
                final DatabaseReference mydb2=FirebaseDatabase.getInstance().getReference().child("storeinfo").child(store.getText().toString());

                mydb2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                            promoterModel.setStore_name(dataSnapshot.child("store_name").getValue(String.class));
                            myb.setValue(promoterModel);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserManagement.this,DeleteUpdate.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
