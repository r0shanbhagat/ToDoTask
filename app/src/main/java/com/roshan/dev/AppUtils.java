package com.roshan.dev;

import android.content.Context;
import android.widget.Toast;

public class AppUtils {

    /**
     * @param mContext
     * @param message
     */
    public static void showToastMessage(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
