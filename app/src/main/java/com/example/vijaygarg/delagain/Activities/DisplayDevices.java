package com.example.vijaygarg.delagain.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vijaygarg.delagain.Adapters.DisplayDeviceAdapter;
import com.example.vijaygarg.delagain.Model.DisplayModel;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayDevices extends AppCompatActivity {
RecyclerView rv;
ArrayList<DisplayModel>arr;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_devices);

        arr=new ArrayList<>();
        rv=findViewById(R.id.displaydevice);
        final DisplayDeviceAdapter displayDeviceAdapter=new DisplayDeviceAdapter(this,arr);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(displayDeviceAdapter);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("display_request");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    DisplayModel displayModel=dataSnapshot1.getValue(DisplayModel.class);
                    if(displayModel.getRequest_result().equals("accept")&& displayModel.getIs_sold_out()==false){
                    arr.add(displayModel);
                    }

                }
                displayDeviceAdapter.notifyDataSetChanged();
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //todo databasereference work is pending
    }
}
