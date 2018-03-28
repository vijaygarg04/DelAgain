package com.example.vijaygarg.delagain;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;



import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GenerateReport extends Activity implements View.OnClickListener
{
    EditText startdate,enddate;
    Button writeExcelButton;
    static String TAG = "ExelLog";
    DatabaseReference databaseReference;
    HashMap<String,ObjectModel> data;
    HashMap<String,Boolean>arr;
    HashMap<String ,Boolean>servicetag;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        data=new HashMap<>();
        arr=new HashMap<>();
        servicetag=new HashMap<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("sell_out");
        writeExcelButton = (Button) findViewById(R.id.btnpreparesheet);
        writeExcelButton.setOnClickListener(this);



    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v)
    {


        switch (v.getId())
        {
            case R.id.btnpreparesheet:
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddMMyyyy");

                String sstartdate=startdate.getText().toString();
                String senddate=enddate.getText().toString();

                LocalDate start= LocalDate.of(Integer.parseInt(sstartdate.substring(4,8)), Integer.parseInt(sstartdate.substring(2,4)), Integer.parseInt(sstartdate.substring(0,2)));
                LocalDate end= LocalDate.of(Integer.parseInt(senddate.substring(4,8)), Integer.parseInt(senddate.substring(2,4)), Integer.parseInt(senddate.substring(0,2)));

                datesBetween(start,end);
                DatabaseReference mydatabase=databaseReference;

                mydatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                if(arr.containsKey(dataSnapshot2.getKey())){
                                    for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren()){
                                        servicetag.put(dataSnapshot3.getKey(),true);
                                    }

                                }
                            }
                        }
                        retrivedatanow();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

        }
    }

    private void retrivedatanow() {

        DatabaseReference mydb=FirebaseDatabase.getInstance().getReference().child("msa");
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(servicetag.containsKey(dataSnapshot1.getKey())){
                        data.put(dataSnapshot1.getKey(),dataSnapshot1.getValue(ObjectModel.class));
                    }
                }
                saveExcelFile(GenerateReport.this,"myExcel2.xls");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void datesBetween(LocalDate start, LocalDate end) {
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            String sdate=date.toString();
            String year=sdate.substring(0,4);
            String month=sdate.substring(5,7);
            String day=sdate.substring(8,10);
            arr.put(day+month+year,true);
            System.out.println(date.toString());
        }

    }


    private boolean saveExcelFile(Context context, String fileName) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        boolean success = false;

        Workbook wb = new HSSFWorkbook();

        Cell c = null;



        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Reporting");


        // Generate column headings
        ArrayList<String> keys=new ArrayList<>(data.keySet());
        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));
        sheet1.setColumnWidth(3, (15 * 500));
        sheet1.setColumnWidth(4, (15 * 500));
        sheet1.setColumnWidth(5, (15 * 500));
        sheet1.setColumnWidth(6, (15 * 500));
        sheet1.setColumnWidth(7, (15 * 500));
        sheet1.setColumnWidth(8, (15 * 500));
        sheet1.setColumnWidth(9, (15 * 500));

        Row row1 = sheet1.createRow(0);
        c = row1.createCell(0);
        c.setCellValue("S.No");
        c = row1.createCell(1);
        c.setCellValue("Store Name");
        c = row1.createCell(2);
        c.setCellValue("Promoter Name");
        c = row1.createCell(3);
        c.setCellValue("Model Number");
        c = row1.createCell(4);
        c.setCellValue("Service Tag");
        c = row1.createCell(5);
        c.setCellValue("Bundle Code");
        c = row1.createCell(6);
        c.setCellValue("MSA Name");
        c = row1.createCell(7);
        c.setCellValue("MSA DATE");
        c = row1.createCell(8);
        c.setCellValue("Sell In DATE");
        c = row1.createCell(9);
        c.setCellValue("Sell Out DATE");

        for(int i=1;i<=keys.size();i++) {

            Row row = sheet1.createRow(i);
            for(int j=0;j<10;j++) {
             c = row.createCell(j);
             switch (j) {
                 case 0:
                     c.setCellValue(i + "");
                     break;
                 case 1:
                     c.setCellValue(data.get(keys.get(i - 1)).getStore_name());
                     break;
                 case 2:

                     c.setCellValue(data.get(keys.get(i - 1)).getSold_by_promoter_name());
                     break;
                 case 3:

                     c.setCellValue(data.get(keys.get(i - 1)).getModel_number());
                     break;
                 case 4:

                     c.setCellValue(data.get(keys.get(i - 1)).getService_tag());
                     break;
                 case 5:

                     c.setCellValue(data.get(keys.get(i - 1)).getBundle_code());
                     break;
                 case 6:

                     c.setCellValue(data.get(keys.get(i - 1)).getMsa_name());
                     break;
                 case 7:

                     c.setCellValue(data.get(keys.get(i - 1)).getMsa_date());
                     break;
                 case 8:

                     c.setCellValue(data.get(keys.get(i - 1)).getStore_sell_in_date());
                     break;
                 case 9:

                     c.setCellValue(data.get(keys.get(i - 1)).getStore_sell_out_date());
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
}


