package com.example.vijaygarg.delagain;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vijaygarg.delagain.Adapters.ModelAdapter;
import com.example.vijaygarg.delagain.Adapters.StoreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddRemoveModel extends AppCompatActivity {
EditText store;
Button add;
RecyclerView rv;
ArrayList<String> arrayList;
DatabaseReference databaseReference;
ModelAdapter sa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_model);
        store=findViewById(R.id.addremoveetstorename);
        add=findViewById(R.id.btnaddremovebtnmodel);
        rv=findViewById(R.id.addremoverecyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        databaseReference=FirebaseDatabase.getInstance().getReference();
        arrayList=new ArrayList<>();
        sa=new ModelAdapter(arrayList,AddRemoveModel.this);
rv.setAdapter(sa);
        final DatabaseReference mydb=databaseReference.child("models");
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot mysnapsot:dataSnapshot.getChildren()){
                    arrayList.add(mysnapsot.getKey().toString());
                }
                sa.notifyDataSetChanged();
                mydb.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String smodel=store.getText().toString();

                DatabaseReference mydatabase=databaseReference.child("models").child(smodel);
                mydatabase.setValue("true");
                arrayList.add(smodel);
                sa.notifyDataSetChanged();
                store.setText("");
            }
        });


    }

}
