package com.example.vijaygarg.delagain.excel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

/**
 * Created by vijaygarg on 21/03/18.
 */

public class InternetConnection {
    public static boolean checkConnection(@NonNull Context context) {
        return  ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
