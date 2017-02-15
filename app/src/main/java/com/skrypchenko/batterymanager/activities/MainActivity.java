package com.skrypchenko.batterymanager.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.activities.BaseActivity;
import com.skrypchenko.batterymanager.events.BatteryEvent;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startMainFragment();
    }



    private int currentBatteryLevel = -1;
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            boolean  present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            int  scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int  status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            String  technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int  temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            int  voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            if ((currentBatteryLevel == -1) || (Math.abs(level - currentBatteryLevel) >= 1)) {
                currentBatteryLevel = level;

                EventBus.getDefault().post(new BatteryEvent(level));

            }


        }
    };


    public int getCurrentBatteryLevel() {
        return currentBatteryLevel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent batteryStatus = getApplicationContext().registerReceiver(this.batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        currentBatteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        EventBus.getDefault().post(new BatteryEvent(currentBatteryLevel));

    }

    protected void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(this.batteryInfoReceiver);
    }


}
