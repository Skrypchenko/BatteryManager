package com.skrypchenko.batterymanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.activities.MainActivity;
import com.skrypchenko.batterymanager.events.BatteryEvent;
import com.skrypchenko.batterymanager.utils.Utils;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
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
    @BindView(R.id.id_battary_lianer)
    LinearLayout id_battary_lianer;


    @BindView(R.id.id_battary)
    CircleProgressView id_battary;

    private DisplayMetrics matrix;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.battery_fragment , container, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }




        matrix = Utils.getDisplayMatrix(getActivity());

        ViewGroup.LayoutParams storage_parans =  id_battary.getLayoutParams();
        storage_parans.height =  (int) ((matrix.widthPixels/2.7f));
        storage_parans.width  =  (int) ((matrix.widthPixels/2.7f));
        id_battary.setLayoutParams(storage_parans);

        ViewGroup.LayoutParams id_storage_lianer_params = id_battary_lianer.getLayoutParams();
        id_storage_lianer_params.width  =  (int) ((matrix.widthPixels/2.7f));
        id_battary_lianer.setLayoutParams(id_storage_lianer_params);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.battery));
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        return view;
    }



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        id_battary.setValueAnimated(((MainActivity)getActivity()).getCurrentBatteryLevel());

    }

    public void onEvent(BatteryEvent event){
        id_battary.setValueAnimated(event.getLevel());
    }

}
