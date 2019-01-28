package com.saeed.projects.mycontacts.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.saeed.projects.mycontacts.navigators.AlertDialogTwoButtonsListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by muzammilsaeed on 21/06/2017.
 */

public final class GlobalUtil
{
    private GlobalUtil() {

    }

    public static boolean isInternetConnected(Context activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static String roundOfDecimalNumber(float num) {
        return String.format("%.2f", num);
    }

    public static void showOkAlertDialog(final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
    public static void showMessageAlertWithTwoButtons(final Activity activity, String title, String message, final AlertDialogTwoButtonsListener alertDialogTwoButtonsListener, final int reqCode) {
        showAlertWithTwoCustomButtons(activity, title, message, "Yes", "No", alertDialogTwoButtonsListener, reqCode);
    }

    public static void showAlertWithTwoCustomButtons(final Activity activity, String title, String message, String positiveTitle, String negativeTitle, final AlertDialogTwoButtonsListener alertDialogTwoButtonsListener, final int reqCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton(negativeTitle, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
                alertDialogTwoButtonsListener.onNegativeClick(reqCode);
            }
        });
        builder.setPositiveButton(positiveTitle, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                alertDialogTwoButtonsListener.onPositiveClick(reqCode);
            }
        });

        AlertDialog dialog = builder.show();
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
    public static void printLog(String tag,String logMessage){
        Log.d(tag,logMessage);
    }

    public static String extractDateFromDateTimeString(String dateTime) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        String matchDate = "";
        try {
            Date dateValue = input.parse(dateTime);
            matchDate = output.format(dateValue);
        } catch ( Exception ex) {}
        return matchDate;
    }

    public static Date convertDateStringToDateObj(String date){
        try {
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.parse(date);
        } catch ( Exception ex) {

        }

        return null;
    }

    public static byte[] convertBitmapToByteArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bitmapdata){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        return bitmap;
    }

//    public static String convertDateObjToDateString(Date date) {
//
//    }

}
