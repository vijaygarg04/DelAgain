package com.example.vijaygarg.delagain.Activities;

import android.content.Context;

import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.widget.Button;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import android.widget.Toast;

import com.example.vijaygarg.delagain.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import java.util.Calendar;
import java.util.Date;

import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GenerateReport extends AppCompatActivity implements View.OnClickListener
{
    Button writeExcelButton;
    static String TAG = "ExcelLog";
    DatabaseReference databaseReference;
    HashMap<String,ObjectModel> data;
    HashMap<String,Boolean>arr;
    HashMap<String ,Boolean>servicetag;
    ArrayList<String> disabledates;
    private CaldroidFragment caldroidFragment;
    Date startDate,endDate;
    boolean startdateset=false;
    boolean enddateset=false;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        caldroidFragment = new CaldroidFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        data=new HashMap<>();
        arr=new HashMap<>();
        servicetag=new HashMap<>();
        disabledates=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("sell_out");
        writeExcelButton =  findViewById(R.id.btnpreparesheet);
        writeExcelButton.setOnClickListener(this);





        final Button customizeButton =  findViewById(R.id.btncustom);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
                ColorDrawable green = new ColorDrawable(Color.GREEN);
                caldroidFragment.setBackgroundDrawableForDate(green, date);
                if(startdateset==false){
                    startDate=date;
                    startdateset=true;
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);

                    int i=-1;
                    while (i>-365) {
                        calendar.add(Calendar.DATE, -1);
                        Date result = calendar.getTime();
                        disabledates.add(formatter.format(result));
                        i--;
                    }
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");

                    caldroidFragment.setDisableDatesFromString(disabledates,"dd/MM/yyyy");
                    ((Button)findViewById(R.id.btncustom)).setText(simpleDateFormat.format(startDate).toString()+"   TO   - - / - -/ - - - -");

                }else if(enddateset==false){
                    endDate=date;
                    datesBetween(startDate,endDate);
                    ColorDrawable red = new ColorDrawable(Color.CYAN);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");


                    colordatesbetween(startDate,endDate,red);
                    ((Button)findViewById(R.id.btncustom)).setText(simpleDateFormat.format(startDate).toString()+"   TO   "+simpleDateFormat.format(endDate).toString());

                    enddateset=true;

                }else{
                    ColorDrawable white = new ColorDrawable(Color.WHITE);
                    caldroidFragment.setBackgroundDrawableForDate(white,startDate);
                    ((Button)findViewById(R.id.btncustom)).setText("-- / --/ ----   TO   -- / --/ ----");

                    caldroidFragment.setBackgroundDrawableForDate(white,endDate);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddMMyyyy");
                    colordatesbetween(startDate,endDate,white);

                    startDate=null;
                    endDate=null;
                    startdateset=false;
                    enddateset=false;
                    Toast.makeText(GenerateReport.this,"Select Dates Again",Toast.LENGTH_LONG).show();
                    caldroidFragment.clearDisableDates();
                    caldroidFragment.refreshView();
                }
                caldroidFragment.refreshView();
            }



        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);


    }

    private void colordatesbetween(Date startDate, Date endDate,ColorDrawable drawable) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();

            caldroidFragment.setBackgroundDrawableForDate(drawable,result);
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void onClick(View v)
    {


        switch (v.getId())
        {
            case R.id.btnpreparesheet:


                datesBetween(startDate,endDate);
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
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd_MM_yyyy");

                saveExcelFile(GenerateReport.this,simpleDateFormat.format(date)+"_report.xls");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private  void datesBetween(Date startDate, Date endDate) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        final SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            arr.put(formatter.format(result),true);
            calendar.add(Calendar.DATE, 1);
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
        sheet1.setColumnWidth(10, (15 * 500));
        sheet1.setColumnWidth(11, (15 * 500));

        Row row1 = sheet1.createRow(0);
        c = row1.createCell(0);
        c.setCellValue("S.No");
        c = row1.createCell(1);
        c.setCellValue("Store ID");
        c = row1.createCell(2);
        c.setCellValue("Store Name");
        c = row1.createCell(3);
        c.setCellValue("Promoter ID");
        c = row1.createCell(4);
        c.setCellValue("Promoter Name");
        c = row1.createCell(5);
        c.setCellValue("Model Number");
        c = row1.createCell(6);
        c.setCellValue("Service Tag");
        c = row1.createCell(7);
        c.setCellValue("Bundle Code");
        c = row1.createCell(8);
        c.setCellValue("MSA Name");
        c = row1.createCell(9);
        c.setCellValue("MSA DATE");
        c = row1.createCell(10);
        c.setCellValue("Sell In DATE");
        c = row1.createCell(11);
        c.setCellValue("Sell Out DATE");

        for(int i=1;i<=keys.size();i++) {

            Row row = sheet1.createRow(i);
            for(int j=0;j<=11;j++) {
             c = row.createCell(j);
             switch (j) {
                 case 0:
                     c.setCellValue(i + "");
                     break;
                 case 1:
                     c.setCellValue(data.get(keys.get(i - 1)).getStore_id());
                     break;
                 case 2:
                     c.setCellValue(data.get(keys.get(i - 1)).getStore_name());
                     break;
                 case 3:

                     c.setCellValue(data.get(keys.get(i - 1)).getSold_by_promoter_id());
                     break;

                 case 4:

                     c.setCellValue(data.get(keys.get(i - 1)).getSold_by_promoter_name());
                     break;
                 case 5:

                     c.setCellValue(data.get(keys.get(i - 1)).getModel_number());
                     break;
                 case 6:

                     c.setCellValue(data.get(keys.get(i - 1)).getService_tag());
                     break;
                 case 7:

                     c.setCellValue(data.get(keys.get(i - 1)).getBundle_code());
                     break;
                 case 8:

                     c.setCellValue(data.get(keys.get(i - 1)).getMsa_name());
                     break;
                 case 9:
                     String sdate=data.get(keys.get(i - 1)).getMsa_date();
                     sdate=dateformat(sdate);
                     c.setCellValue(sdate);
                     break;
                 case 10:
                     String ssdate=data.get(keys.get(i - 1)).getStore_sell_in_date();
                     ssdate=dateformat(ssdate);
                     c.setCellValue(ssdate);
                     break;
                 case 11:
                     String sssdate=data.get(keys.get(i - 1)).getStore_sell_out_date();
                     sssdate=dateformat(sssdate);

                     c.setCellValue(sssdate);
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
        Toast.makeText(GenerateReport.this,"Report Saved",Toast.LENGTH_LONG).show();
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
    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -700 );
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.caldroid_sky_blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.caldroid_white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.caldroid_white, greenDate);
        }
    }
    public String dateformat(String date){
        return date.substring(0,2)+"/"+date.substring(2,4)+"/"+date.substring(4);
    }
}


