package com.dongzhili.easylib.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtils {
    private static final String TAG = "ToastHelper";
    public static Toast mToast;

    public static void showToast(int resId, Context context) {
        String text = context.getString(resId);
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(String text, Context context) {
        if (mToast == null) {
            if (context == null) {
                return;
            }
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showToast(Context context,String text) {
        showToast(text, context);
    }

    public static void showToast(Context context,int resId) {
        String text = context.getString(resId);
        showToast(context, text);
    }
}
