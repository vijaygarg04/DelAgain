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
import com.example.vijaygarg.delagain.Activities.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name,number,uname,password;
    Button signup;
    TextView login;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.etname);
        login=findViewById(R.id.tvlogin);
        password=findViewById(R.id.etpassword);
        number=findViewById(R.id.etmobilenumber);
        uname=findViewById(R.id.etuserid);
        signup=findViewById(R.id.btnsignup);
        signup.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("admindetails");
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    final String sname=name.getText().toString().trim();
                    final String snumber=number.getText().toString().trim();
                    final String suname=uname.getText().toString().trim();
                    final String spass=password.getText().toString().trim();
                    DatabaseReference mydb=firebaseDatabase.child(suname);
                    UserDetails ud=new UserDetails(suname,sname,snumber,spass);
                    mydb.setValue(ud);
                    Toast.makeText(SignUpActivity.this,"User Acccount Created successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this,Welcome.class));
                    finish();

                }else{
                }
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        final String sname=name.getText().toString().trim();
        final String snumber=number.getText().toString().trim();
        final String suname=uname.getText().toString().toLowerCase().trim()+"@dell.com";
        final String spass=password.getText().toString().trim();
        if(sname.length()<=0){
            name.setError("Enter Name");
            return;
        }else if(snumber.length()!=10){
            number.setError("Enter contact number 10 digits");
            return;
        }else if(suname.length()<=9){
            uname.setError("Enter USER-ID");
            return;
        }else if(spass.length()<=6){
            password.setError("minimum password length 6");
            return;
        }
        mAuth.createUserWithEmailAndPassword(suname,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this,"User Acccount Created Successfully",Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(SignUpActivity.this,"User Acccount Created Failed",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }
}
