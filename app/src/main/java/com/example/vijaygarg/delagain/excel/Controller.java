package com.example.vijaygarg.delagain.excel;

/**
 * Created by vijaygarg on 21/03/18.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vijaygarg.delagain.Model.ObjectModel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class Controller {



    public static final String TAG = "TAG";

    public static final String WAURL="https://script.google.com/macros/s/AKfycbw8APsS_gNvTEmLbIBZn_rGsOjZ7aRt9gmDz4CezJ6ihdfjnw5m/exec?";

    private static Response response;


    public static JSONObject insertData(String sno, ObjectModel objectModel) {
        try {

            String servicetag,msaname,storename,msadate,sellindate,selloutdate,promotername,modelnumber,bundlecode;
            servicetag=objectModel.getService_tag();
            msaname=objectModel.getMsa_name();
            storename=objectModel.getStore_name();
            msadate=objectModel.getMsa_date();
            sellindate=objectModel.getStore_sell_in_date();
            selloutdate=objectModel.getStore_sell_out_date();
            promotername=objectModel.getSold_by_promoter_name();
            modelnumber=objectModel.getModel_number();
            bundlecode=objectModel.getBundle_code();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL+"action=insert&sno="+sno+"&storename="+storename+"&promotorname="+promotername+"&modelnumber="
                            +modelnumber+"&servicetag="+servicetag+"&bundlecode="+bundlecode+"&msaname="+msaname+
                            "&msadate="+msadate+"&sellindate="+sellindate+"&selloutdate="+selloutdate)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject deleteAllData() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL+"action=deleteall")
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

}
