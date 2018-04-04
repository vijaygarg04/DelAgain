package com.example.vijaygarg.delagain.userdata;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vijaygarg.delagain.R;
import com.example.vijaygarg.delagain.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username,password;
    TextView signup;
    Button login;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAtuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth=FirebaseAuth.getInstance();
        mAtuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    startActivity(new Intent(LogInActivity.this,Welcome.class));
                }else{

                }
            }
        };
        username=findViewById(R.id.etuserid);
        password=findViewById(R.id.etpassword);
        login=findViewById(R.id.btnlogin);
        signup=findViewById(R.id.tvcreateaccount);
        login.setOnClickListener(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        String uname=username.getText().toString().trim()+"@dell.com";
        String pass=password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LogInActivity.this,Welcome.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Log In Failed",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAtuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAtuthStateListener);
    }
}
