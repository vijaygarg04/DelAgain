package com.example.vijaygarg.delagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vijaygarg.delagain.Model.CompetitiveModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class CompetitiveReportingData extends AppCompatActivity {

    DatabaseReference databaseReference;
    Date startdate,enddate;
    ArrayList<CompetitiveModel> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitive_reporting_data);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("competitive_report");
        Intent i=getIntent();
        Bundle b=i.getExtras();
        startdate=(Date)b.get("startdate");
        enddate=(Date)b.get("enddate");
        arr=new ArrayList<>();


    }
}
