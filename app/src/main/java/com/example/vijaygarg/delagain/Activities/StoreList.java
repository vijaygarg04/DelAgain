package com.example.vijaygarg.delagain.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vijaygarg.delagain.Adapters.StoreAdapter;
import com.example.vijaygarg.delagain.Model.StoreModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreList extends AppCompatActivity {
RecyclerView rv;
StoreAdapter storeAdapter;
DatabaseReference databaseReference;
ArrayList<StoreModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        rv=findViewById(R.id.rvliststore);
        arrayList=new ArrayList<>();
        rv=findViewById(R.id.rvliststore);
        rv.setLayoutManager(new LinearLayoutManager(this));
        storeAdapter=new StoreAdapter(arrayList,this);
        rv.setAdapter(storeAdapter);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("storeinfo");




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("is_store_active").getValue(Boolean.class)) {
                        arrayList.add(dataSnapshot1.getValue(StoreModel.class));
                    }
                }
                storeAdapter.notifyDataSetChanged();
                databaseReference.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
