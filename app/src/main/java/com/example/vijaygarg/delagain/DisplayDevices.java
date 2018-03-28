package com.example.vijaygarg.delagain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vijaygarg.delagain.Adapters.DisplayDeviceAdapter;
import com.example.vijaygarg.delagain.Model.ObjectModel;

import java.util.ArrayList;

public class DisplayDevices extends AppCompatActivity {
RecyclerView rv;
ArrayList<ObjectModel>arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_devices);

        arr=new ArrayList<>();
        rv=findViewById(R.id.displaydevice);
        DisplayDeviceAdapter displayDeviceAdapter=new DisplayDeviceAdapter(this,arr);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(displayDeviceAdapter);

        //todo databasereference work is pending
    }
}
