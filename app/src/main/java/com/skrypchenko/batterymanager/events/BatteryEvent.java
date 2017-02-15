package com.skrypchenko.batterymanager.events;

/**
 * Created by yevgen on 14.02.17.
 */

public class BatteryEvent {
    public Integer level;
    public BatteryEvent(Integer level){
        this.level=level;
    }

    public Integer getLevel() {
        return level;
    }
}
