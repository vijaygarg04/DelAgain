package com.example.vijaygarg.delagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.vijaygarg.delagain.Adapters.DisplayRequestAdapter;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Display extends AppCompatActivity {

RecyclerView recyclerView;
ArrayList<ObjectModel>arr;
Button list;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        arr=new ArrayList<>();
        DisplayRequestAdapter displayRequestAdapter=new DisplayRequestAdapter(this,arr);
        recyclerView=findViewById(R.id.rvdisplayrequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(displayRequestAdapter);
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
