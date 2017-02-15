package com.skrypchenko.batterymanager.adapters;

import android.app.ActivityManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skrypchenko.batterymanager.R;
import com.skrypchenko.batterymanager.utils.AppObj;

import java.util.List;

/**
 * Created by yevgen on 14.02.17.
 */

public class RAMAdapter extends RecyclerView.Adapter<RAMAdapter.ViewHolder>  {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private AppObj mItem;


        private ImageView id_app_image;
        private TextView id_app_name;
        private TextView id_app_size;
        private Button id_close_process;



        public ViewHolder(View view) {
            super(view);
            id_app_image = (ImageView) view.findViewById(R.id.id_app_image);
            id_app_name = (TextView) view.findViewById(R.id.id_app_name);
            id_app_size = (TextView) view.findViewById(R.id.id_app_size);
            id_close_process= (Button) view.findViewById(R.id.id_close_process);
            id_close_process.setOnClickListener(this);

        }

        public void setItem(AppObj item) {
            mItem = item;
            id_app_name.setText(item.getName());
            id_app_size.setText(item.getSize());
            if(item.getImage()!=null){
            id_app_image.setImageDrawable(item.getImage());}else {
                id_app_image.setImageResource(R.drawable.ic_launcher);
            }
        }

        @Override
        public void onClick(View view) {
            // Log.d(TAG, "onClick " + getPosition() + " " + mItem);
            if(onItemClickListener!=null){
                onItemClickListener.onClick(mItem,getPosition());
            }

        }
    }


    public static OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }


    public  interface OnItemClickListener{
        void onClick(AppObj item ,int position);
    }



    List<AppObj> procInfos;

    public RAMAdapter(List<AppObj> procInfos) {
        this.procInfos = procInfos;
    }

    public void setProcInfos(List<AppObj> procInfos) {
        this.procInfos = procInfos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ram_app_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(procInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return procInfos.size();
    }
}
