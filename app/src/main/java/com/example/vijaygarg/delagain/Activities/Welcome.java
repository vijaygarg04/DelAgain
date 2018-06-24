package com.example.vijaygarg.delagain.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.vijaygarg.delagain.*;
import com.example.vijaygarg.delagain.R;
import com.example.vijaygarg.delagain.userdata.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Welcome extends AppCompatActivity {

Button model,dataentry,generatereport,usermanagement,display,scheme,comp,storeadd,chat_button;
FirebaseAuth firebaseAuth;
private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vijaygarg.delagain.R.layout.activity_welcome);
        if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);


        }
        chat_button = (Button)findViewById(R.id.chat_button);
        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(Welcome.this,ChatActivity.class);
            startActivity(intent);
            }
        });
        scheme=findViewById(R.id.btnscheme);
        model=findViewById(R.id.welcomebtnmodel);
        usermanagement=findViewById(R.id.usermanagement);
        display=findViewById(R.id.display);
        comp=findViewById(R.id.comp);
        storeadd=findViewById(R.id.store_add);

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,CompetitiveReporting.class));
            }
        });
        usermanagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,UserManagement.class));
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
        scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Welcome.this,SchemeAddRemove.class));
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId()==R.id.logoutmenu){
     firebaseAuth.signOut();
     startActivity(new Intent(Welcome.this, LogInActivity.class));
     finish();

    }

return true;
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
