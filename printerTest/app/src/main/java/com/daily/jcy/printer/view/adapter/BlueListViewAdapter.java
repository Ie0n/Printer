package com.daily.jcy.printer.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daily.jcy.printer.R;

import java.util.List;

public class BlueListViewAdapter extends BaseAdapter {


    private List<BluetoothDevice> deviceList = null;
    private Context context;
    public BlueListViewAdapter(Context context, List<BluetoothDevice> deviceList){
        this.context = context;
        this.deviceList = deviceList;
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlueListViewAdapterHolder holder = null;
        if(convertView == null){
            holder = new BlueListViewAdapterHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.blue_list_item,null,false);
            holder.tv_blue_list_address = (TextView) convertView.findViewById(R.id.tv_blue_list_address);
            holder.tv_blue_list_name = (TextView) convertView.findViewById(R.id.tv_blue_list_name);
            convertView.setTag(holder);
        }else{
            holder = (BlueListViewAdapterHolder) convertView.getTag();
        }

        BluetoothDevice device = (BluetoothDevice) getItem(position);
        holder.tv_blue_list_address.setText(device.getAddress());
        holder.tv_blue_list_name.setText(device.getName());
        return convertView;
    }

    static class BlueListViewAdapterHolder{
        TextView tv_blue_list_name,tv_blue_list_address;
    }

}
