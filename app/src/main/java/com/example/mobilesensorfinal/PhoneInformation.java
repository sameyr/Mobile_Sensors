package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.widget.TextView;

import java.io.File;

public class PhoneInformation extends AppCompatActivity {
    private boolean isNetworkConnected;
    private TextView textView;

    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            File internalStorageFile=getFilesDir();
            long availableSizeInBytes=new StatFs(internalStorageFile.getPath()).getAvailableBytes();
            long availableSizeInMb = availableSizeInBytes / 1048576;
            long availableSizeInGb = availableSizeInMb / 1024;

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo!= null){
                if(networkInfo.isConnected()){
                    isNetworkConnected = true;
                }
                else{
                    isNetworkConnected = false;
                }
            }
            else {
                isNetworkConnected = false;
            }

            textView.setText(   "Battery Level: " + String.valueOf(level)+"%" + "\n"+
                                "Product Name: "+ Build.PRODUCT + "\n" +
                                "Product Brand: "+ Build.BRAND + "\n"   +
                                "Device name: " + Build.DEVICE+"\n"   +
                                "FingerPrint: " + Build.FINGERPRINT+"\n" +
                                "Bootloader " + Build.BOOTLOADER +"\n"+
                                "Hardware Type:  " + Build.HARDWARE+"\n"+
                                "Free Space in this device is:" + availableSizeInGb + "gb"
                                );
            if (isNetworkConnected){
                textView.setText(textView.getText() + "\n" +
                                 "Internet Connected");
            }
            else{
                textView.setText(textView.getText() + "\n" +
                        "Internet Not Connected");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_information);
        textView = (TextView) this.findViewById(R.id.txt_phoneinfo);
        this.registerReceiver(this.batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

}