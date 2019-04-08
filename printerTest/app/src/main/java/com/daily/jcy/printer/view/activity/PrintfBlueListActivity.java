package com.daily.jcy.printer.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.daily.jcy.printer.MyApplication;
import com.daily.jcy.printer.R;
import com.daily.jcy.printer.manager.PrintfManager;
import com.daily.jcy.printer.manager.SharedPreferencesManager;
import com.daily.jcy.printer.utils.PermissionUtil;
import com.daily.jcy.printer.utils.Util;
import com.daily.jcy.printer.view.adapter.BlueListViewAdapter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.daily.jcy.printer.utils.Util.rotate;


public class PrintfBlueListActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter = null;

    private PrintfManager printfManager;

    private View root;

    private static int REQUEST_ENABLE_BT = 2;
    private AbstractList<BluetoothDevice> bluetoothDeviceArrayList;

    private List<BluetoothDevice> alreadyBlueList;

    private Context context;

    private ListView lv_blue_list,lv_already_blue_list;
    private TextView search;
    private String TAG = "PrintfBlueListActivity";
    private BlueListViewAdapter adapter;
    private boolean isRegister;

    private TextView tv_blue_list_modify,tv_blue_list_name,tv_blue_list_address;
    private ImageView iv_blue_list_already_paired,iv_blue_list_unpaired;

    private PrintfManager.BluetoothChangLister bluetoothChangLister;

    private LinearLayout ll_blue_list_already_paired,ll_blue_list_unpaired;

    /**
     * 是否打开了配对
     */
    private boolean ALREADY_PAIRED_IS_OPEN,UNPAIRED_IS_OPEN;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printf_blue_list);
        context = this;
        setCustomActionBar();
        initView();
        initData();
        setLister();

        if(PermissionUtil.checkLocationPermission(context)){
            if(!printfManager.isConnect()){
                starSearchBlue();
            }
        }
    }

    private void stopSearchBlue() {
        search.setText(getString(R.string.printf_blue_list_search));
        if (mReceiver != null && isRegister) {
            try {
                unregisterReceiver(mReceiver);
                Util.ToastText(context,getString(R.string.stop_search));
            }catch (Exception e){

            }
        }
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionUtil.MY_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(!printfManager.isConnect()){
                        starSearchBlue();
                    }
                } else {
                    //权限被拒绝
                    new AlertDialog.Builder(context).setMessage(getString(R.string.permissions_are_rejected_bluetooth))
                            .setPositiveButton(getString(R.string.to_set_up), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = Util.getAppDetailSettingIntent(context);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setTitle(getString(R.string.prompt)).show();
                    break;
                }
        }
    }

    @Override
    protected void onDestroy() {
        stopSearchBlue();
        printfManager.removeBluetoothChangLister(bluetoothChangLister);
        super.onDestroy();
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        isRegister = true;
        return super.registerReceiver(receiver, filter);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
        isRegister = false;
    }

    private void starSearchBlue() {
        search.setText(getString(R.string.printf_blue_list_stop));
        Util.ToastText(context,getString(R.string.start_search));
        bluetoothDeviceArrayList.clear();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            mBluetoothAdapter.startDiscovery();
        }
    }

    private void setLister() {

        printfManager.setConnectSuccess(new PrintfManager.ConnectSuccess() {
            @Override
            public void success() {
                finish();
            }
        });



        ll_blue_list_already_paired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALREADY_PAIRED_IS_OPEN) {
                    ALREADY_PAIRED_IS_OPEN = false;
                    closeRotate(iv_blue_list_already_paired);
                    lv_already_blue_list.setVisibility(View.GONE);
                } else {
                    openAlreadyPaired();
                }
            }
        });

        ll_blue_list_unpaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UNPAIRED_IS_OPEN) {
                    UNPAIRED_IS_OPEN = false;
                    closeRotate(iv_blue_list_unpaired);
                    lv_blue_list.setVisibility(View.GONE);
                } else {
                    openUnpaired();
                }
            }
        });


        iv_blue_list_unpaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lv_already_blue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MyApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        printfManager.openPrinter(alreadyBlueList.get(position));
                    }
                });
            }
        });

        lv_blue_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Util.ToastText(context,getString(R.string.connect_now));
                //先停止搜索
                stopSearchBlue();
                //进行配对
                MyApplication.getInstance().getCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BluetoothDevice mDevice = mBluetoothAdapter.getRemoteDevice(bluetoothDeviceArrayList.get(position).getAddress());
                            printfManager.openPrinter(mDevice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = search.getText().toString();
                String stopText = getString(R.string.printf_blue_list_stop);
                String searchText = getString(R.string.printf_blue_list_search);
                if (text.equals(searchText)) {//点了搜索
                    starSearchBlue();
                } else if (text.equals(stopText)) {//点击了停止
                    stopSearchBlue();
                }
            }
        });

        PrintfManager.getInstance(context).addBluetoothChangLister(bluetoothChangLister);
    }

    private void openUnpaired() {
        UNPAIRED_IS_OPEN = true;
        openRotate(iv_blue_list_unpaired);
        lv_blue_list.setVisibility(View.VISIBLE);
    }

    private void openAlreadyPaired() {
        ALREADY_PAIRED_IS_OPEN = true;
        openRotate(iv_blue_list_already_paired);
        lv_already_blue_list.setVisibility(View.VISIBLE);
    }

    private void closeRotate(View v) {
        rotate(v, 90f, 0f);
    }

    private void openRotate(View v) {
        rotate(v, 0f, 90f);
    }

    private void initData() {
        printfManager = PrintfManager.getInstance(context);
        bluetoothDeviceArrayList = new ArrayList<>();
        alreadyBlueList = new ArrayList<>();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : bondedDevices){
            if (judge(device,alreadyBlueList))
                continue;
            alreadyBlueList.add(device);
        }
        adapter = new BlueListViewAdapter(context, bluetoothDeviceArrayList);
        lv_blue_list.setAdapter(adapter);
        lv_already_blue_list.setAdapter(new BlueListViewAdapter(context,alreadyBlueList));

        //蓝牙名称
        String blueName = getString(R.string.no);
        String blueAddress = getString(R.string.no);
        if(printfManager.isConnect()){
            blueName = SharedPreferencesManager.getBluetoothName(context);
            blueAddress = SharedPreferencesManager.getBluetoothAddress(context);
        }
        tv_blue_list_name.setText(getString(R.string.name_colon) + blueName);
        tv_blue_list_address.setText(getString(R.string.address_colon) + blueAddress);

        bluetoothChangLister = new PrintfManager.BluetoothChangLister() {
            @Override
            public void chang(String name,String address) {
                tv_blue_list_name.setText(getString(R.string.name_colon) + name);
                tv_blue_list_address.setText(getString(R.string.address_colon) + address);
            }
        };

        openUnpaired();
    }

    private void initView() {
        root = findViewById(R.id.root);

        ll_blue_list_already_paired = (LinearLayout)findViewById(R.id.ll_blue_list_already_paired);
        ll_blue_list_unpaired = (LinearLayout)findViewById(R.id.ll_blue_list_unpaired);

        lv_blue_list = (ListView) findViewById(R.id.lv_blue_list);
        lv_already_blue_list = (ListView)findViewById(R.id.lv_already_blue_list);

        tv_blue_list_name = (TextView)findViewById(R.id.tv_blue_list_name);
        tv_blue_list_address = (TextView)findViewById(R.id.tv_blue_list_address);

        iv_blue_list_already_paired = (ImageView)findViewById(R.id.iv_blue_list_already_paired);
        iv_blue_list_unpaired = (ImageView)findViewById(R.id.iv_blue_list_unpaired);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                mBluetoothAdapter.startDiscovery();
            }
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //找到设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (judge(device,bluetoothDeviceArrayList))
                    return;
                bluetoothDeviceArrayList.add(device);
                adapter.notifyDataSetChanged();
            }
            //搜索完成
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                search.setText(getString(R.string.printf_blue_list_search));
                stopSearchBlue();
            }
        }
    };

    private boolean judge(BluetoothDevice device, List<BluetoothDevice> devices) {
        if (device == null || devices.contains(device)) {
            return true;
        }
        int majorDeviceClass = device.getBluetoothClass().getMajorDeviceClass();
        if(majorDeviceClass != 1536){
            return true;
        }
        return false;
    }

    public static void startActivity(Activity activity){

        if(PrintfManager.getInstance(activity).isCONNECTING()){
            Util.ToastText(activity,activity.getString(R.string.bluetooth_is_being_connected));
            return;
        }
        Intent intent = new Intent(activity,PrintfBlueListActivity.class);
        activity.startActivity(intent);
    }
    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_bluelist, null);
        TextView text = mActionBarView.findViewById(R.id.title);
        ImageView back = mActionBarView.findViewById(R.id.pic);
        search = mActionBarView.findViewById(R.id.search);
        text.setText("打印机列表");
        getSupportActionBar().setCustomView(mActionBarView, lp);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintfBlueListActivity.this.finish();
            }
        });

    }


}