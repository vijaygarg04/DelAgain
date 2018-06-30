package com.example.vijaygarg.delagain.userdata;

import android.app.ProgressDialog;
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
import com.example.vijaygarg.delagain.Activities.Welcome;
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
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        mAtuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    startActivity(new Intent(LogInActivity.this,Welcome.class));
                    finish();
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
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnlogin) {
            String uname = username.getText().toString().toLowerCase().trim() + "@dell.com";
            String pass = password.getText().toString().trim();
            if(uname.length()<=9){
                username.setError("Enter User Id");
                return;
            }else if(pass.length()<=0){
                password.setError("Enter Password");
                return;
            }
            progressDialog.show();
            progressDialog.setMessage("Logging In...");
            mAuth.signInWithEmailAndPassword(uname, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {

                        startActivity(new Intent(LogInActivity.this, Welcome.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Log In Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
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
