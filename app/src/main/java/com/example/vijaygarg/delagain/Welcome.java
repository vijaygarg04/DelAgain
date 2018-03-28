package com.example.vijaygarg.delagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {
Button model,dataentry,generatereport,usermanagement,display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        model=findViewById(R.id.welcomebtnmodel);
        usermanagement=findViewById(R.id.usermanagement);
        display=findViewById(R.id.display);
        usermanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,UserManagement.class));
            }
        });
        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,AddRemoveModel.class));
            }
        });
        generatereport=findViewById(R.id.generatereport);
        generatereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,GenerateReport.class));
            }
        });
        dataentry=findViewById(R.id.dataentry);
        dataentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,DataEntry.class));
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(Welcome.this,Display.class));
            }
        });
    }
}
