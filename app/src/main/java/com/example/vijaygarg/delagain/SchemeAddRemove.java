package com.example.vijaygarg.delagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SchemeAddRemove extends AppCompatActivity {
Button schemeadd,schemeremove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_add_remove);
        schemeadd=findViewById(R.id.newscheme);
        schemeremove=findViewById(R.id.deletescheme);
        schemeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SchemeAddRemove.this,Scheme.class));
            }
        });
        schemeremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SchemeAddRemove.this,RemoveModel.class));
            }
        });

    }
}
