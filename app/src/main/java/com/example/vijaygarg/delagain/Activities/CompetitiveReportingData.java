package com.example.vijaygarg.delagain.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vijaygarg.delagain.Adapters.CompetitionReportAdapter;
import com.example.vijaygarg.delagain.Model.CompetitiveModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class CompetitiveReportingData extends AppCompatActivity {

    DatabaseReference databaseReference;
    Date startdate,enddate;
    ArrayList<CompetitiveModel> arr;
    HashMap<String,Boolean> dates;
    RecyclerView rv;
    CompetitionReportAdapter competitionReportAdapter;
    TextView totalunit,delunits,otherunit,delper,hpper,acerper,lenovoper,othreper;
    Button savereport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitive_reporting_data);
        arr=new ArrayList<>();
        dates=new HashMap<>();
        otherunit=findViewById(R.id.otherunit);
        totalunit=findViewById(R.id.totalunit);
        delunits=findViewById(R.id.delunits);
        delper=findViewById(R.id.dellpercent);
        hpper=findViewById(R.id.hppercent);
        acerper=findViewById(R.id.acerpercent);
        lenovoper=findViewById(R.id.lenovopercent);
        othreper=findViewById(R.id.otherpercent);
        savereport=findViewById(R.id.savecompreport);
        savereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd_MM_yyyy");
                String filename=simpleDateFormat.format(date)+"_competition_report.xls";
                saveExcelFile(CompetitiveReportingData.this,filename);
            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference().child("comp_reporting");
        Intent i=getIntent();
        Bundle b=i.getExtras();
        startdate=(Date)b.get("startdate");
        enddate=(Date)b.get("enddate");
        rv=findViewById(R.id.rvcompetition);
        rv.setLayoutManager(new LinearLayoutManager(this));
        competitionReportAdapter=new CompetitionReportAdapter(this,arr);
        rv.setAdapter(competitionReportAdapter);

        datesBetween(startdate,enddate);
        retrivedatanow();


    }
    private  void datesBetween(Date startDate, Date endDate) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            dates.put(formatter.format(result),true);
            calendar.add(Calendar.DATE, 1);
        }

    }
    private void retrivedatanow() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dates.containsKey(dataSnapshot1.getKey())) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            arr.add(dataSnapshot2.getValue(CompetitiveModel.class));}

                    }
                }
                    competitionReportAdapter.notifyDataSetChanged();
                    showReport();
                    databaseReference.removeEventListener(this);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showReport() {
        int del=0, hp=0, acer=0, lenovo=0, other=0;
        for(int i=0;i<arr.size();i++){
            CompetitiveModel competitiveModel=arr.get(i);
            del+=Integer.parseInt(competitiveModel.getDell());
            hp+=Integer.parseInt(competitiveModel.getHp());
            acer+=Integer.parseInt(competitiveModel.getAcer());
            lenovo+=Integer.parseInt(competitiveModel.getLenovo());
            other+=Integer.parseInt(competitiveModel.getOther());
        }
        int total=0;
        total=del+hp+acer+lenovo+other;
        totalunit.setText(total+"");
        delunits.setText(del+"");
        otherunit.setText(total-del+"");
        int idelper,ihpper,iacerper, ilenovoper,iotherper;
        idelper=(int)((del*100*1.0)/(total));
        delper.setText(idelper+" %");

        ihpper=(int)((hp*100*1.0)/(total));
        hpper.setText(ihpper+" %");
        iacerper=(int)((acer*100*1.0)/(total));
        acerper.setText(iacerper+" %");
        ilenovoper=(int)((lenovo*100*1.0)/(total));
        lenovoper.setText(ilenovoper+" %");
        iotherper=(int)((other*100*1.0)/(total));
        othreper.setText(iotherper+" %");

        totalunit.setTextSize(40.0f);
        delunits.setTextSize(40.0f);
        otherunit.setTextSize(40.0f);
        if(idelper>10){
        delper.setTextSize((float) (100.0f*idelper*0.01));}
        if(ihpper>10){
        hpper.setTextSize((float) (100.0f*ihpper*0.01));}
        if(ilenovoper>10){
        lenovoper.setTextSize((float) (100.0f*ilenovoper*0.01));}
        if(iacerper>10){
        acerper.setTextSize((float) (100.0f*iacerper*0.01));}
        if(iotherper>10){
        othreper.setTextSize((float) (100.0f*iotherper*0.01));}
    }

    private boolean saveExcelFile(Context context, String fileName) {

        if (!GenerateReport.isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return false;
        }

        boolean success = false;

        final Workbook wb = new HSSFWorkbook();
        Cell c=null;
        final Sheet sheet1 =wb.createSheet("Comp_Reporting");

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 300));
        sheet1.setColumnWidth(3, (15 * 300));
        sheet1.setColumnWidth(4, (15 * 300));
        sheet1.setColumnWidth(5, (15 * 300));
        sheet1.setColumnWidth(6, (15 * 300));
        sheet1.setColumnWidth(7, (15 * 300));
        sheet1.setColumnWidth(8, (15 * 300));

        sheet1.setColumnWidth(9, (15 * 300));

        Row row1 = sheet1.createRow(0);
        c = row1.createCell(0);
        c.setCellValue("S.No");
        c = row1.createCell(1);
        c.setCellValue("Store Id");
        c = row1.createCell(2);
        c.setCellValue("Store Name");
        c = row1.createCell(3);
        c.setCellValue("Promoter Id");
        c = row1.createCell(4);
        c.setCellValue("Promoter Name");

        c = row1.createCell(5);
        c.setCellValue("DELL");
        c = row1.createCell(6);
        c.setCellValue("HP");
        c = row1.createCell(7);
        c.setCellValue("LENOVO");
        c = row1.createCell(8);
        c.setCellValue("ACER");
        c = row1.createCell(9);
        c.setCellValue("Other");

        for(int i=1;i<=arr.size();i++) {

            final Row row = sheet1.createRow(i);
            for(int j=0;j<=9;j++) {
                c = row.createCell(j);
                switch (j) {
                    case 0:
                        c.setCellValue(i + "");
                        break;
                    case 1:
                        c.setCellValue(arr.get(i-1).getStore_id());
                        break;
                    case 2:
                        //storename
                       c.setCellValue(arr.get(i-1).getStore_name());
                        break;
                    case 3:

                        c.setCellValue(arr.get(i-1).getPromoter_id());
                        break;
                    case 4:
                        //promoter name

                        c.setCellValue(arr.get(i-1).getPromoter_name());
                        break;
                    case 5:

                        c.setCellValue(arr.get(i-1).getDell());
                        break;
                    case 6:

                        c.setCellValue(arr.get(i-1).getHp());
                        break;
                    case 7:

                        c.setCellValue(arr.get(i-1).getLenovo());
                        break;
                    case 8:

                        c.setCellValue(arr.get(i-1).getAcer());
                        break;
                    case 9:

                        c.setCellValue(arr.get(i-1).getOther());
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
        Toast.makeText(CompetitiveReportingData.this,"Report Saved",Toast.LENGTH_LONG).show();
        return success;
    }



    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
