package com.example.vijaygarg.delagain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        data=new HashMap<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("msa");
        writeExcelButton = (Button) findViewById(R.id.btnpreparesheet);
        writeExcelButton.setOnClickListener(this);



    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnpreparesheet:

            DatabaseReference mydatabase=databaseReference;
            try {
                mydatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                ObjectModel objectModel = dataSnapshot2.getValue(ObjectModel.class);
                                data.put(objectModel.getService_tag(), objectModel);
                            }
                        }
                        saveExcelFile(GenerateReport.this,"myExcel2.xls");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }catch(Exception e ){

            }finally {


            }
                break;

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


