package com.skrypchenko.batterymanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.activities.MainActivity;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by yevgen on 14.02.17.
 */

public class BatteryFragment extends BaseFragment {
    private static BatteryFragment instance;
    public static Fragment getInstance(Bundle bundle) {
        if(bundle==null){
            instance = new BatteryFragment();
        }
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.battery_fragment , container, false);
        ButterKnife.bind(this, view);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.battery));
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        return view;
    }



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }



}
