package com.example.vijaygarg.delagain.Activities;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.Table;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.LogicalFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DataEntry extends AppCompatActivity {
EditText filename;
Button filesubmit;
DatabaseReference databaseReference;
HashMap<String,Boolean>alreadyavailable;
ArrayList<ObjectModel>arr;
    DataEntryAdapter dataEntryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        filename=findViewById(R.id.filename);
        filesubmit=findViewById(R.id.submitfile);
        arr=new ArrayList<>();
        alreadyavailable=new HashMap<>();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("msa");
        filesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sfilename=filename.getText().toString()+".xls";
                readExcelFile(DataEntry.this,sfilename);
            }
        });

    }
    class MyTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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

//            final ValueEventListener valueEventListener=new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.hasChild(objectModel.getService_tag())){
//
//                    }else{
//                        databaseReference.child(objectModel.getService_tag()).setValue(objectModel);
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };
//            databaseReference.addValueEventListener(valueEventListener);
            databaseReference.child(objectModel.getService_tag()).setValue(objectModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e("DATAENTRY","SUCCESS");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("DATAENTRY","FAILED");
                }
            });

        }
    }
    private void readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.w("FileUtils", "Storage not available or read only");
            return;
        }

        try{
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(file);
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIter = mySheet.rowIterator();
            rowIter.next();
            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<Cell> cellIter = myRow.cellIterator();
                String values[]=new String[4];
                int i=0;
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    values[i]=myCell.toString();
                    i++;
                    Log.w("FileUtils", "Cell Value: " +  myCell.toString());
                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
                Date da=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                String sdate=sdf.format(da).toString().trim();
                ObjectModel objectModel=new ObjectModel(values[1],values[3],true,sdate,values[0],values[2]);
                arr.add(objectModel);

            }
            MyTask myTask=new MyTask();
            myTask.execute();
        }catch (Exception e){e.printStackTrace(); }

        return;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
