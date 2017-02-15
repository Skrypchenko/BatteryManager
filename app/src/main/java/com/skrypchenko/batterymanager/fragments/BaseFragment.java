package com.skrypchenko.batterymanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by yevgen on 14.02.17.
 */

public class BaseFragment extends Fragment {


    public void loadFragment(Bundle bundle, Fragment basefragment)  {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(basefragment.getClass().getName());
        if(fragment!=null){
            ft.remove(fragment);
        }

        ft.addToBackStack(null);
        ft.replace(android.R.id.content ,basefragment ,basefragment.getClass().getName());
        ft.commit();





    }


}
