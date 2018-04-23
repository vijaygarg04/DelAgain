package com.example.vijaygarg.delagain.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vijaygarg.delagain.Model.ChatModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity implements ChatFragment.OnListFragmentInteractionListener{
    EditText editText;
    Button sendmsg;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        databaseReference =  FirebaseDatabase.getInstance().getReference();
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
                    c.setSent_by_name("Vinayak Shenoy");
                    c.setSent_by_id("0000");
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
