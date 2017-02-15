package com.skrypchenko.batterymanager.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.activities.MainActivity;
import com.skrypchenko.batterymanager.adapters.RAMAdapter;
import com.skrypchenko.batterymanager.utils.AppObj;
import com.skrypchenko.batterymanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.support.v7.widget.RecyclerView;
import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by yevgen on 14.02.17.
 */

public class RamFragment extends BaseFragment {

    private static RamFragment instance;
    public static Fragment getInstance(Bundle bundle) {
        if(bundle==null){
            instance = new RamFragment();
        }
        instance.setArguments(bundle);
        return instance;
    }


    @BindView(R.id.id_ram_lianer)
    LinearLayout id_ram_lianer;

    @BindView(R.id.id_ram)
    CircleProgressView id_ram;

    private DisplayMetrics matrix;



    @BindView(R.id.id_total_mem)
    TextView id_total_mem;

    @BindView(R.id.id_free_mem)
    TextView id_free_mem;

    @BindView(R.id.id_system_and_apps)
    TextView id_system_and_apps;


    @BindView(R.id.id_background_applications)
    TextView id_background_applications;


    @BindView(R.id.id_recycler_view)
    RecyclerView recyclerView;


    private List<AppObj> list = new ArrayList();
    private RAMAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ram_fragment , container, false);
        ButterKnife.bind(this, view);
        matrix = Utils.getDisplayMatrix(getActivity());
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }

        ViewGroup.LayoutParams ram_parans =  id_ram.getLayoutParams();
        ram_parans.height =  (int) ((matrix.widthPixels/2.7f));
        ram_parans.width  =  (int) ((matrix.widthPixels/2.7f));
        id_ram.setLayoutParams(ram_parans);

        ViewGroup.LayoutParams id_ram_lianer_params = id_ram_lianer.getLayoutParams();
        id_ram_lianer_params.width  =  (int) ((matrix.widthPixels/2.7f));
        id_ram_lianer.setLayoutParams(id_ram_lianer_params);


        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.hram).toUpperCase());
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return view;
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RAMAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RAMAdapter.OnItemClickListener() {
            @Override
            public void onClick(AppObj item, int position) {
                Utils.killAppBypackage(getActivity(),item.getProcessName());
                updadateAll();
            }
        });
        updadateAll();
    }

    private void updadateAll(){
        id_total_mem.setText(getString(R.string.total)+" "+Utils.getTotalRAM()+" "+getString(R.string.excluded_reserved_memory));
        getBgApps(getActivity(),"2222222");

    }












    private void getBgApps(Activity activity,String TAG ){
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        id_background_applications.setText(getString(R.string.background_applications)+" "+procInfos.size());
        list.clear();
        PackageManager packageManager =activity.getPackageManager();
        final List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
        int all_size = 0;
        double total = Utils.getTotalRAMD();
        for(ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            String appName = processInfo.processName;
            Drawable icon = null;
            String size = null;
            try {try{
                appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(processInfo.processName, PackageManager.GET_META_DATA));
                ApplicationInfo app = packageManager.getApplicationInfo(processInfo.processName, 0);
                icon = packageManager.getApplicationIcon(app);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG,"Application info not found for process : " + processInfo.processName,e);}}catch (Exception e){e.printStackTrace();}
                int pids[] = new int[1];
                pids[0] = processInfo.pid;
                android.os.Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(pids);
                for(android.os.Debug.MemoryInfo pidMemoryInfo: memoryInfoArray){
                    all_size+=(pidMemoryInfo.getTotalPss());
                    size = Utils.convertToKBMBGBTB(pidMemoryInfo.getTotalPss());
                }
            list.add(new AppObj(processInfo.processName,appName,size,icon));
        }

        float used = (float) (all_size/(total/100));
        id_ram.setValueAnimated(used);
        id_system_and_apps.setText(getString(R.string.system_and_apps)+" "+Utils.convertToKBMBGBTB(all_size));
        id_free_mem.setText(Utils.convertToKBMBGBTB(total-all_size)+" "+getString(R.string.free));
        adapter.setProcInfos(list);

    }





}
