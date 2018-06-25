package com.example.vijaygarg.delagain.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vijaygarg.delagain.Model.ChatModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity implements ChatFragment.OnListFragmentInteractionListener{
    EditText editText;
    Button sendmsg;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        databaseReference =  FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String email=firebaseUser.getEmail();
        int idx=email.indexOf('@');

        final String userid=email.substring(0,idx);
        final String adminname[]=new String[1];
        final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("admindetails").child(userid);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminname[0]=dataSnapshot.child("admin_name").getValue(String.class);
                databaseReference1.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ChatFragment chatFragment = new ChatFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,chatFragment);
        fragmentTransaction.commit();
        editText=findViewById(R.id.message_edit_text);
        sendmsg=findViewById(R.id.sendbtn);
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length()>0){
                    ChatModel c = new ChatModel();
                    c.setMessage(editText.getText().toString());
                    c.setSent_by_admin(true);
                    c.setSent_by_name(adminname[0]);
                    c.setSent_by_id(userid);
                    databaseReference.child("chat_room").push().setValue(c);
                    editText.setText("");
                }
            }
        });
    }

    @Override
    public void onListFragmentInteraction(ChatModel item) {

    }
}
