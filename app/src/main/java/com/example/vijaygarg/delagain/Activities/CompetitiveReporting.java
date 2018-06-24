package com.example.vijaygarg.delagain.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.example.vijaygarg.delagain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class CompetitiveReporting extends AppCompatActivity implements View.OnClickListener{
    Button writeExcelButton;
    static String TAG = "ExcelLog";
    DatabaseReference databaseReference;
    HashMap<String,ObjectModel> data;
    HashMap<String,Boolean>arr;
    HashMap<String ,Boolean>servicetag;
    ArrayList<String> disabledates;
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    Date startDate,endDate;
    boolean startdateset=false;
    boolean enddateset=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitive_reporting);
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        caldroidFragment = new CaldroidFragment();
        data=new HashMap<>();
        arr=new HashMap<>();
        servicetag=new HashMap<>();
        disabledates=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("competitive_report");
        writeExcelButton =  findViewById(R.id.btnpreparesheet);
        writeExcelButton.setOnClickListener(this);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();


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
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");

                    caldroidFragment.setDisableDatesFromString(disabledates,"yyyy/MM/dd");
                    ((Button)findViewById(R.id.btncustom)).setText(simpleDateFormat.format(startDate).toString()+"   TO   - - / - -/ - - - -");

                }else if(enddateset==false){
                    endDate=date;
                    datesBetween(startDate,endDate);
                    ColorDrawable red = new ColorDrawable(Color.CYAN);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");


                    colordatesbetween(startDate,endDate,red);
                    ((Button)findViewById(R.id.btncustom)).setText(simpleDateFormat.format(startDate).toString()+"   TO   "+simpleDateFormat.format(endDate).toString());

                    enddateset=true;

                }else{
                    ColorDrawable white = new ColorDrawable(Color.WHITE);
                    caldroidFragment.setBackgroundDrawableForDate(white,startDate);
                    ((Button)findViewById(R.id.btncustom)).setText("-- / --/ ----   TO   -- / --/ ----");

                    caldroidFragment.setBackgroundDrawableForDate(white,endDate);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
                    colordatesbetween(startDate,endDate,white);

                    startDate=null;
                    endDate=null;
                    startdateset=false;
                    enddateset=false;
                    Toast.makeText(CompetitiveReporting.this,"Select Dates Again",Toast.LENGTH_LONG).show();
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

                Intent i=new Intent(CompetitiveReporting.this , CompetitiveReportingData.class);
                i.putExtra("startdate",startDate);
                i.putExtra("enddate",endDate);
                startActivity(i);


                break;

        }
    }



    private  void datesBetween(Date startDate, Date endDate) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            arr.put(formatter.format(result),true);
            calendar.add(Calendar.DATE, 1);
        }

    }

}
