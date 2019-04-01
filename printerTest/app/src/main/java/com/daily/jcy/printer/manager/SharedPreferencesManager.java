package com.daily.jcy.printer.presenter;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {


    public static String getBluetoothName(Context context) {
        SharedPreferences mht = getSharedPreferences(context);
        return mht.getString("blueName", null);
    }

    public static void saveBluetoothName(Context context, String name) {
        SharedPreferences mht = getSharedPreferences(context);
        mht.edit().putString("blueName", name).commit();
    }

    /**
     * 保持本次连接的蓝牙地址
     *
     * @param context
     */
    public static void saveBluetoothAddress(Context context, String address) {
        SharedPreferences blue = getSharedPreferences(context);
        blue.edit().putString("blueAddress", address).commit();
    }

    /**
     * 获取上次连接的蓝牙地址
     *
     * @param context
     * @return
     */
    public static String getBluetoothAddress(Context context) {
        SharedPreferences blue = getSharedPreferences(context);
        return blue.getString("blueAddress", null);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("MHT", Context.MODE_PRIVATE);
    }
}
