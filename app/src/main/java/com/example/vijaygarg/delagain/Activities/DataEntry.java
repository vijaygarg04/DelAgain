package com.example.vijaygarg.delagain.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DataEntry extends AppCompatActivity {
EditText filename;
Button filesubmit;
DatabaseReference databaseReference;
ArrayList<ObjectModel>arr;
ProgressDialog progressDialog;
obj isAvailable[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        filename=findViewById(R.id.filename);
        filesubmit=findViewById(R.id.submitfile);
        arr=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("msa");
        filesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Reading File Data ....");
                progressDialog.show();
                String sfilename=filename.getText().toString()+".xls";
                readExcelFile(DataEntry.this,sfilename);
                progressDialog.setMessage("Uploading Data File ....");
                MyTask myTask=new MyTask();
                myTask.execute();

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
            progressDialog.dismiss();
            int size=arr.size();
            for(int i=0;i<size;i++){
                arr.remove(arr.size()-1);
            }

//            Date date=new Date();
//            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
//            String filename="data_upload_result_"+simpleDateFormat.format(date).toString()+".xls";
//            saveExcelFile(DataEntry.this,filename);
//            finish();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("DATAENTRY",arr.size()+"");
            for( int i=0;i<arr.size();i++){
                 inputdata(arr.get(i),i);
            }

//            Log.e("Data",isAvailable.toString());
            return null;
        }
        private void inputdata(final ObjectModel objectModel,final int i){

            String service_tag=objectModel.getService_tag();
            int idx=service_tag.indexOf('.');
            if(idx>=0&&idx<service_tag.length()){
                service_tag=service_tag.substring(0,idx);
                objectModel.service_tag=service_tag;
            }
            final DatabaseReference db1=databaseReference.child(objectModel.getService_tag());
            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final obj newobj=new obj();
                    newobj.setServicetag(objectModel.getService_tag());
                    if(dataSnapshot.getValue(ObjectModel.class)!=null){
                        newobj.setStaus("Already Uploaded Uploaded");
                    }else{
                        db1.removeEventListener(this);
                        databaseReference.child(objectModel.getService_tag()).setValue(objectModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                newobj.setStaus("Successfully Uploaded");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                newobj.setStaus("Upload Failed");
                            }
                        });
                    }
                   isAvailable[i]=newobj;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

//
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
//            databaseReference.child(objectModel.getService_tag()).setValue(objectModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
////                    Log.e("DATAENTRY","SUCCESS");
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
////                    Log.e("DATAENTRY","FAILED");
//                }
//            });
//
//        }
    }
    private void readExcelFile(Context context, String filename) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly())
        {
            Log.w("FileUtils", "Storage not available or read only");
            return;
        }

        try{
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
//                    Log.w("FileUtils", "Cell Value: " +  myCell.toString());
//                    Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
                Date da=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                String sdate=sdf.format(da).toString().trim();
                String service_tag=values[1];
                int idx=service_tag.indexOf('.');
                if(idx>=0&&idx<service_tag.length()){
                    service_tag=service_tag.substring(0,idx);
                    values[1]=service_tag;
                }

                int idx1=values[0].indexOf('.');
                if(idx1>=0&&idx1<values[0].length()){
                values[0]=values[0].substring(0,idx1);
                }

                ObjectModel objectModel=new ObjectModel(values[1],values[3],true,sdate,values[0],values[2]);
                arr.add(objectModel);
            }

            isAvailable=new obj[arr.size()];

        }catch (Exception e){e.printStackTrace();
        Log.e("Entry",e.toString());
        }

        return;
    }
    private boolean saveExcelFile(Context context, String fileName) {

        if (!GenerateReport.isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return false;
        }

        boolean success = false;

        final Workbook wb = new HSSFWorkbook();
        Cell c=null;
        final Sheet sheet1 =wb.createSheet("Data Upload Report");

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 300));

        Row row1 = sheet1.createRow(0);
        c = row1.createCell(0);
        c.setCellValue("S.No");
        c = row1.createCell(1);
        c.setCellValue("Service Tag");
        c = row1.createCell(2);
        c.setCellValue("Status");

        for(int i=1;i<=isAvailable.length;i++) {

            final Row row = sheet1.createRow(i);
            for(int j=0;j<=2;j++) {
                c = row.createCell(j);
                switch (j) {
                    case 0:
                        c.setCellValue(i + "");
                        break;
                    case 1:
                        c.setCellValue(isAvailable[i-1].getServicetag());
                        break;
                    case 2:
                        c.setCellValue(isAvailable[i-1].getStaus());
                        break;


                }


            }
        }
        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
//        Toast.makeText(DataEntry.this,"Report Saved",Toast.LENGTH_LONG).show();
        return success;
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
class obj{
        String servicetag;
        String staus;

    public obj(String servicetag, String staus) {
        this.servicetag = servicetag;
        this.staus = staus;
    }

    public obj() {
    }

    public String getServicetag() {
        return servicetag;
    }

    public void setServicetag(String servicetag) {
        this.servicetag = servicetag;
    }

    public String getStaus() {
        return staus;
    }

    public void setStaus(String staus) {
        this.staus = staus;
    }
}

}
