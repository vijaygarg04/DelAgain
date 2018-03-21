package com.example.vijaygarg.delagain;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.excel.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GenerateReport extends AppCompatActivity {
EditText startdate,enddate;
Button preparesheet;
HashMap<String,ObjectModel> arr;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        preparesheet=findViewById(R.id.btnpreparesheet);
        arr=new HashMap<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("msa");
        preparesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReadFirebaseData readFirebaseData=new ReadFirebaseData();
                try{
                readFirebaseData.execute();
                }catch (Exception e){

                }finally {
                    Log.i("size",arr.size()+"");
                    new InsertDataActivity().execute();
                }
//                DeleteDataActivity deleteDataActivity=new DeleteDataActivity();
//                deleteDataActivity.execute();

            }
        });

    }
    class DeleteDataActivity extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        String result=null;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(Controller.TAG,"IDVALUE");
            JSONObject jsonObject = Controller.deleteAllData();
            Log.i(Controller.TAG, "Json obj "+jsonObject);

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result=jsonObject.getString("result");


                }
            } catch (JSONException je) {
                Log.i(Controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
            Calendar date=Calendar.getInstance();
            String val=sdf.format(date.getTime()).toString();

            Log.i("date",sdf.format(date.getTime()).toString());




        }
    }
    class ReadFirebaseData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        String result=null;
        DatabaseReference mydatabaseReference;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             dialog = new ProgressDialog(GenerateReport.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("Deleting... ");
            dialog.show();
            mydatabaseReference=databaseReference.child("21032018");

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            mydatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        ObjectModel objectModel=dataSnapshot1.getValue(ObjectModel.class);
                        arr.put(objectModel.getService_tag(),objectModel);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyy");
            Calendar date=Calendar.getInstance();
            String val=sdf.format(date.getTime()).toString();

            Log.i("date",sdf.format(date.getTime()).toString());




        }
    }
    class InsertDataActivity extends AsyncTask < Void, Void, Void > {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result = null;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            dialog = new ProgressDialog(GenerateReport.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("Inserting your values..");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void...params) {
            ArrayList<String> mylist= new ArrayList<>(arr.keySet());

            for(int i=0;i<mylist.size();i++) {
                String sno = i + 1 + "";
                JSONObject jsonObject = Controller.insertData(sno, arr.get(mylist.get(i)));


                try {

                    if (jsonObject != null) {

                        result = jsonObject.getString("result");

                    }
                } catch (JSONException je) {
                    Log.i(Controller.TAG, "" + je.getLocalizedMessage());
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }
    }
}
