package com.example.vijaygarg.delagain.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.vijaygarg.delagain.Adapters.DisplayRequestAdapter;
import com.example.vijaygarg.delagain.Model.DisplayModel;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display extends AppCompatActivity {

RecyclerView recyclerView;
ArrayList<DisplayModel>arr;
Button list;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        arr=new ArrayList<>();
        recyclerView=findViewById(R.id.rvdisplayrequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference= FirebaseDatabase.getInstance().getReference().child("display_request");

        final DisplayRequestAdapter displayRequestAdapter=new DisplayRequestAdapter(this,arr);

        recyclerView.setAdapter(displayRequestAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    DisplayModel displayModel=dataSnapshot1.getValue(DisplayModel.class);
                    if(displayModel.getRequest_result().equals("pending")&&displayModel.getIs_sold_out()==false) {
                        arr.add(displayModel);
                    }
                }
                displayRequestAdapter.notifyDataSetChanged();
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        list=findViewById(R.id.btnshowdisplaydevice);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Display.this,DisplayDevices.class));
            }
        });
        //todo databasereference work is pending

    }
}
