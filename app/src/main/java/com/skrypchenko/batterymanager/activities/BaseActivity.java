package com.skrypchenko.batterymanager.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.skrypchenko.batterymanager.fragments.MainFragment;

/**
 * Created by yevgen on 14.02.17.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected void startMainFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(MainFragment.class.getName());
        if(fragment!=null){
            ft.remove(fragment);
        }
        fragment = MainFragment.getInstance(null);
        ft.replace(android.R.id.content ,fragment,MainFragment.class.getName());
        ft.commit();
    }



}

