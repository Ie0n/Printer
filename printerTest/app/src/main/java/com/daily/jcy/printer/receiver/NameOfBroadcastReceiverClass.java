package com.example.jcy.testleancloud;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.print.sdk.PrinterConstants;

public class NameOfBroadcastReceiverClass extends BroadcastReceiver {

    private String TAG = "NameOfBroadcastReceiverClass";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) { //蓝牙连接已经断开
            PrintfManager.getInstance(context).mHandler.sendEmptyMessage(PrinterConstants.Connect.CLOSED);
        } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_OFF) {//蓝牙关闭
                PrintfManager.getInstance(context).mHandler.sendEmptyMessage(PrinterConstants.Connect.CLOSED);
            }
        }
    }
}
