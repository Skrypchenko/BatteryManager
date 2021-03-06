package com.skrypchenko.batterymanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.activities.MainActivity;
import com.skrypchenko.batterymanager.utils.Utils;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by yevgen on 14.02.17.
 */

public class StorageFragment extends BaseFragment {

    private static StorageFragment instance;
    public static Fragment getInstance(Bundle bundle) {
        if(bundle==null){
            instance = new StorageFragment();
        }
        instance.setArguments(bundle);
        return instance;
    }



    @BindView(R.id.id_storage)
    CircleProgressView id_storage;

    private DisplayMetrics matrix;

    @BindView(R.id.id_storage_lianer)
    LinearLayout id_storage_lianer;



    @BindView(R.id.id_unnesasery_system_and_apps)
    TextView id_unnesasery_system_and_apps;


    @BindView(R.id.id_system_and_apps)
    TextView id_system_and_apps;


    @BindView(R.id.id_free_mem)
    TextView id_free_mem;

    @BindView(R.id.id_total_mem)
    TextView id_total_mem;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storage_fragment , container, false);
        ButterKnife.bind(this, view);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        matrix = Utils.getDisplayMatrix(getActivity());

        ViewGroup.LayoutParams storage_parans =  id_storage.getLayoutParams();
        storage_parans.height =  (int) ((matrix.widthPixels/2.7f));
        storage_parans.width  =  (int) ((matrix.widthPixels/2.7f));
        id_storage.setLayoutParams(storage_parans);

        ViewGroup.LayoutParams id_storage_lianer_params = id_storage_lianer.getLayoutParams();
        id_storage_lianer_params.width  =  (int) ((matrix.widthPixels/2.7f));
        id_storage_lianer.setLayoutParams(id_storage_lianer_params);


        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.storage));
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        id_unnesasery_system_and_apps.setText(getString(R.string.unnecessary_data)+" "+ Utils.convertToKBMBGBTB((Utils.sd_card_used())/1024));
        id_system_and_apps.setText(getString(R.string.system_user_data)+" "+ Utils.convertToKBMBGBTB((Utils.phone_storage_used())/1024));
        id_free_mem.setText(Utils.convertToKBMBGBTB(((Utils.phone_storage_free()+Utils.sd_card_free())/1024))+" "+getString(R.string.free));

        id_total_mem.setText(getString(R.string.total)+" "+ Utils.convertToKBMBGBTB((Utils.phone_storage_total()+Utils.sd_card_total())/1024));

        float used = (float) ((Utils.phone_storage_used()+Utils.sd_card_used())/((Utils.phone_storage_total()+Utils.sd_card_total())/100));
        id_storage.setValueAnimated(used);
    }





}
