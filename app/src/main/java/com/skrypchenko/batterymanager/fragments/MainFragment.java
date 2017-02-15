package com.skrypchenko.batterymanager.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.activities.MainActivity;
import com.skrypchenko.batterymanager.events.BatteryEvent;
import com.skrypchenko.batterymanager.utils.AppObj;
import com.skrypchenko.batterymanager.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by yevgen on 14.02.17.
 */

public class MainFragment extends BaseFragment {

   private static MainFragment instance;
    public static Fragment getInstance(Bundle bundle) {
        if(bundle==null){
            instance = new MainFragment();
        }
        instance.setArguments(bundle);
        return instance;
    }


    @BindView(R.id.id_battery_lianer)
    LinearLayout id_battery_lianer;

    @BindView(R.id.id_storage_lianer)
    LinearLayout id_storage_lianer;

    @BindView(R.id.id_ram_lianer)
    LinearLayout id_ram_lianer;



    @BindView(R.id.id_battery)
    CircleProgressView id_battery;

    @BindView(R.id.id_storage)
    CircleProgressView id_storage;

    @BindView(R.id.id_ram)
    CircleProgressView id_ram;



    private DisplayMetrics matrix;



    @BindView(R.id.id_ram_text)
    TextView id_ram_text;

    @BindView(R.id.id_storage_text)
    TextView id_storage_text;



    @BindView(R.id.id_clear_all)
    Button id_clear_all;








    @OnClick(R.id.id_battery)
    public void onBattery(View view) {
            loadFragment(null,BatteryFragment.getInstance(null));
    }

    @OnClick(R.id.id_storage)
    public void onStorage(View view){
            loadFragment(null,StorageFragment.getInstance(null));

    }


    @OnClick(R.id.id_ram)
    public void onRam(View view) {
            loadFragment(null, RamFragment.getInstance(null));
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment , container, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        matrix = Utils.getDisplayMatrix(getActivity());

        ViewGroup.LayoutParams battery_parans =  id_battery.getLayoutParams();
        battery_parans.height = (int) ((matrix.widthPixels/2.7f));
        battery_parans.width  =  (int) ((matrix.widthPixels/2.7f));
        id_battery.setLayoutParams(battery_parans);

        ViewGroup.LayoutParams storage_parans =  id_storage.getLayoutParams();
        storage_parans.height =  (int) ((matrix.widthPixels/2.7f));
        storage_parans.width  =  (int) ((matrix.widthPixels/2.7f));
        id_storage.setLayoutParams(storage_parans);

        ViewGroup.LayoutParams ram_parans =  id_ram.getLayoutParams();
        ram_parans.height =  (int) ((matrix.widthPixels/2.7f));
        ram_parans.width  =  (int) ((matrix.widthPixels/2.7f));
        id_ram.setLayoutParams(ram_parans);



        ViewGroup.LayoutParams battery_lianer_params = id_battery_lianer.getLayoutParams();
        battery_lianer_params.width  =  (int) ((matrix.widthPixels/2.7f));
        id_battery_lianer.setLayoutParams(battery_lianer_params);

        ViewGroup.LayoutParams id_storage_lianer_params = id_storage_lianer.getLayoutParams();
        id_storage_lianer_params.width  =  (int) ((matrix.widthPixels/2.7f));
        id_storage_lianer.setLayoutParams(id_storage_lianer_params);


        ViewGroup.LayoutParams id_ram_lianer_params = id_ram_lianer.getLayoutParams();
        id_ram_lianer_params.width  =  (int) ((matrix.widthPixels/2.7f));
        id_ram_lianer.setLayoutParams(id_ram_lianer_params);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // id_battery.setValue((Utils.getMyBatteryLevel(getActivity())));





        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentBackStackFragment = getFragmentManager().findFragmentByTag(BaseFragment.class.getName());
                if(currentBackStackFragment instanceof MainFragment){
                    ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
                    ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });
        getBgApps(getActivity());


        id_battery.setValueAnimated(((MainActivity)getActivity()).getCurrentBatteryLevel());


    }


    public void onEvent(BatteryEvent event){
        id_battery.setValue(event.getLevel());
    }




    private void getBgApps(Activity activity){
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        int all_size = 0;
        double total = Utils.getTotalRAMD();
        for(ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                int pids[] = new int[1];
                pids[0] = processInfo.pid;
                android.os.Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(pids);
                for(android.os.Debug.MemoryInfo pidMemoryInfo: memoryInfoArray){
                    all_size+=(pidMemoryInfo.getTotalPss());
                }

        }

        float used = (float) (all_size/(total/100));
        id_ram.setValueAnimated(used);
        id_ram_text.setText(getString(R.string.used)+"\n"+ Utils.convertToKBMBGBTB(total-all_size)+" "+getString(R.string.free));



         used = (float) ((Utils.phone_storage_used()+Utils.sd_card_used())/((Utils.phone_storage_total()+Utils.sd_card_total())/100));
        id_storage.setValueAnimated(used);
        id_storage_text.setText(getString(R.string.used)+"\n"+ Utils.convertToKBMBGBTB((Utils.phone_storage_free()+Utils.sd_card_free())/1024)+" "+getString(R.string.free));

    }




}
