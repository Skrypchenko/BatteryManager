package com.skrypchenko.batterymanager.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;



import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yevgen on 14.02.17.
 */

public class Utils {

    public static DisplayMetrics getDisplayMatrix(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }




    public static void killAppBypackage(Activity activity,String packageTokill){

        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = activity.getPackageManager();
        //get a list of installed apps.
        packages = pm.getInstalledApplications(0);


        ActivityManager mActivityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        String myPackage = activity.getApplicationContext().getPackageName();

        for (ApplicationInfo packageInfo : packages) {

            if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1) {
                continue;
            }
            if(packageInfo.packageName.equals(myPackage)) {
                continue;
            }
            if(packageInfo.packageName.equals(packageTokill)) {
                mActivityManager.killBackgroundProcesses(packageInfo.packageName);
            }

        }

    }


    public static double getTotalRAMD() {
        RandomAccessFile reader = null;
        String load = null;

        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totRam = Double.parseDouble(value);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }
        return totRam;
    }

    public static String getTotalRAM() {
        RandomAccessFile reader = null;
        String load = null;

        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;


            lastValue = convertToKBMBGBTB(totRam);


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }

        return lastValue;
    }



    public static String convertToKBMBGBTB(double totRam){
        String lastValue = "";
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double mb = totRam / 1024.0;
        double gb = totRam / 1048576.0;
        double tb = totRam / 1073741824.0;

        if (tb > 1) {
            lastValue = twoDecimalForm.format(tb).concat(" TB");
        } else if (gb > 1) {
            lastValue = twoDecimalForm.format(gb).concat(" GB");
        } else if (mb > 1) {
            lastValue = twoDecimalForm.format(mb).concat(" MB");
        } else {
            lastValue = twoDecimalForm.format(totRam).concat(" KB");
        }
        return lastValue;
    }










    public static long phone_storage_free(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long free_memory = 0; //return value is in bytes
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            free_memory = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
        }else {
            free_memory = stat.getAvailableBlocks() * stat.getBlockSize();
        }
        return free_memory;
    }

    public static long phone_storage_used(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long free_memory =0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            free_memory = (stat.getBlockCountLong() - stat.getAvailableBlocksLong()) * stat.getBlockSizeLong(); //return value is in bytes
        }else {
            free_memory = (stat.getBlockCount() - stat.getAvailableBlocks()) * stat.getBlockSize();
        }

        return free_memory;
    }

    public static long phone_storage_total(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long free_memory =0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            free_memory = stat.getBlockCountLong() * stat.getBlockSizeLong(); //return value is in bytes
        }else {
            free_memory = stat.getBlockCount() * stat.getBlockSize(); //return value is in bytes

        }

        return free_memory;
    }



    public static long sd_card_free(){

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long free_memory=0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
             free_memory = stat.getAvailableBlocksLong() * stat.getBlockSizeLong(); //return value is in bytes
        }else {
            free_memory = stat.getAvailableBlocks() * stat.getBlockSize(); //return value is in bytes

        }
        return free_memory;
    }
    public static long sd_card_used(){

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long free_memory = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            free_memory = (stat.getBlockCountLong() - stat.getAvailableBlocksLong()) * stat.getBlockSizeLong(); //return value is in bytes

        }else {
             free_memory = (stat.getBlockCount() - stat.getAvailableBlocks()) * stat.getBlockSize(); //return value is in bytes
        }
        return free_memory;
    }
    public static long sd_card_total(){

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long free_memory = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            free_memory = stat.getBlockCountLong() * stat.getBlockSizeLong(); //return value is in bytes
        }else {
             free_memory = stat.getBlockCount() * stat.getBlockSize(); //return value is in bytes
        }
        return free_memory;
    }


}
