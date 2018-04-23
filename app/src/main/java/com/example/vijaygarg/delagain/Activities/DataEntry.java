package com.example.vijaygarg.delagain.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vijaygarg.delagain.Adapters.DataEntryAdapter;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.formula.functions.LogicalFunction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataEntry extends AppCompatActivity {
EditText serialtag,msaname,modelnumber,bundlecode;
Button addtolist,submitlist;
DatabaseReference databaseReference;
HashMap<String,Boolean>alreadyavailable;
RecyclerView rv;
ArrayList<ObjectModel>arr;


    DataEntryAdapter dataEntryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_entry);

        serialtag=findViewById(R.id.serialtag);
        msaname=findViewById(R.id.msaname);
        modelnumber=findViewById(R.id.modelnumber);
        bundlecode=findViewById(R.id.bundlecode);

        addtolist=findViewById(R.id.addtolist);
        submitlist=findViewById(R.id.submitlist);

        arr=new ArrayList<>();
        alreadyavailable=new HashMap<>();
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        dataEntryAdapter=new DataEntryAdapter(this,arr);
        rv.setAdapter(dataEntryAdapter);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("msa");




        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sserailtag=serialtag.getText().toString();
                String smsaname=msaname.getText().toString();
                String smodelnumber=modelnumber.getText().toString();
                String sbundlecode=bundlecode.getText().toString();
                Date da=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
                String sdate=sdf.format(da).toString().trim();
                ObjectModel objectModel=new ObjectModel(sserailtag,smsaname,true,sdate,smodelnumber,sbundlecode);
                arr.add(objectModel);
                serialtag.setText("");
                msaname.setText("");
                modelnumber.setText("");
                bundlecode.setText("");
                dataEntryAdapter.notifyDataSetChanged();


            }
        });
        submitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask myTask=new MyTask();
                myTask.execute();
            }
        });

    }
    class MyTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddMMyyyy");
            String sdate=simpleDateFormat.format(date).toString().trim();
            databaseReference= FirebaseDatabase.getInstance().getReference().child("msa");

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            int size=arr.size();
            for(int i=0;i<size;i++){
                arr.remove(arr.size()-1);
            }
            dataEntryAdapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            for( int i=0;i<arr.size();i++){
                inputdata(arr.get(i));

            }

            return null;


        }
        private void inputdata(final ObjectModel objectModel){

            final ValueEventListener valueEventListener=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(objectModel.getService_tag())){

                    }else{
                        databaseReference.child(objectModel.getService_tag()).setValue(objectModel);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference.addValueEventListener(valueEventListener);


        }
    }

}
