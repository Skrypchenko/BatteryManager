package com.skrypchenko.batterymanager.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by yevgen on 14.02.17.
 */

public class AppObj {
    String name;
    Drawable image;
    String size;

    String processName;

    public AppObj( String processName,String name,String size, Drawable image){
        this.name=name;
        this.size=size;
        this.image=image;
        this.processName=processName;
    }



    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Drawable getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getProcessName() {
        return processName;
    }
}
