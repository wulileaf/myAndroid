package org.zackratos.kanebo.ui;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.adapter.ADBluetoothConnect;
import org.zackratos.kanebo.bean.BBluetoothConnect;
import org.zackratos.kanebo.tools.CustomDialog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

// 蓝牙连接
public class BluetoothConnect extends BaseActivity {

    private final int REQUEST_ENBLE_BT = 1;
    private final String SPP_UUID = "fa87c0d0-afac-11de-8a39-0800200c9a66";
    private Context context;
    private BroadcastReceiver btScanReceiver;
    private List<BBluetoothConnect> list = new ArrayList<>();
    private ADBluetoothConnect adBluetoothConnect;
    private int already = 0;// 判断此设备是否已经匹配过了

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.startBluetooth)
    Button startBluetooth;
    @BindView(R.id.closeBluetooth)
    Button closeBluetooth;
    @BindView(R.id.scanMatchedBluetooth)
    Button scanMatchedBluetooth;
    @BindView(R.id.scanVisibilityBluetooth)
    Button scanVisibilityBluetooth;
    @BindView(R.id.scanPairBluetooth)
    Button scanPairBluetooth;
    @BindView(R.id.scanStartBluetooth)
    Button scanStartBluetooth;
    @BindView(R.id.scanCustomerBluetooth)
    Button scanCustomerBluetooth;
    @BindView(R.id.bluetoothList)
    RecyclerView bluetoothList;

    @Override
    protected int initView() {
        return R.layout.atc_bluetooth_connect;
    }

    @Override
    protected void initData() {
        initTitle();
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (getBAdapter(BluetoothConnect.this) == null) {
            // 说明此设备不支持蓝牙操作
            showToast("此设备不支持蓝牙");
        } else {
            // 没有开启蓝牙
            if (!getBAdapter(BluetoothConnect.this).isEnabled()) {
                showToast("没有开启蓝牙");
            }
        }
    }

    public static BluetoothAdapter getBAdapter(Context context) {
        BluetoothAdapter mBluetoothAdapter = null;
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
                mBluetoothAdapter = manager.getAdapter();
            } else {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return mBluetoothAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 接收蓝牙状态动态广播
    public class onReceBluetoothStatue extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();// 获取广播固定格式
                // 蓝牙开关状态变化
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    //获取蓝牙广播中的蓝牙新状态
                    int blueNewState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    //获取蓝牙广播中的蓝牙旧状态
                    int blueOldState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, 0);
                    switch (blueNewState) {
                        //正在打开蓝牙
                        case BluetoothAdapter.STATE_TURNING_ON: {
                            Toast.makeText(context, "STATE_TURNING_ON", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //蓝牙已打开
                        case BluetoothAdapter.STATE_ON: {
                            Toast.makeText(context, "STATE_ON", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //正在关闭蓝牙
                        case BluetoothAdapter.STATE_TURNING_OFF: {
                            Toast.makeText(context, "STATE_TURNING_OFF", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //蓝牙已关闭
                        case BluetoothAdapter.STATE_OFF: {
                            Toast.makeText(context, "STATE_OFF", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                /*
                 * 本机的蓝牙连接状态发生变化
                 *
                 * 特指“无任何连接”→“连接任意远程设备”，以及“连接任一或多个远程设备”→“无任何连接”的状态变化，
                 * 即“连接第一个远程设备”与“断开最后一个远程设备”时才会触发该Action
                 */
                else if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
                    //获取蓝牙广播中的蓝牙连接新状态
                    int newConnState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0);
                    //获取蓝牙广播中的蓝牙连接旧状态
                    int oldConnState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, 0);
                    // 当前远程蓝牙设备
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    HashMap<String, Object> map = parseBtDevice2Map(device);
                    switch (newConnState) {
                        //蓝牙连接中
                        case BluetoothAdapter.STATE_CONNECTING: {
//                            Log.d(TAG, "STATE_CONNECTING, " + map.get("name"));
                            Toast.makeText(context, "STATE_CONNECTING", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //蓝牙已连接
                        case BluetoothAdapter.STATE_CONNECTED: {
//                            Log.d(TAG, "STATE_CONNECTED, " + map.get("name"));
                            Toast.makeText(context, "STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //蓝牙断开连接中
                        case BluetoothAdapter.STATE_DISCONNECTING: {
//                            Log.d(TAG, "STATE_DISCONNECTING, " + map.get("name"));
                            Toast.makeText(context, "STATE_DISCONNECTING", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //蓝牙已断开连接
                        case BluetoothAdapter.STATE_DISCONNECTED: {
//                            Log.d(TAG, "STATE_DISCONNECTED, " + map.get("name"));
                            Toast.makeText(context, "STATE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                // 有远程设备成功连接至本机
                else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                    // 当前远程蓝牙设备
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    HashMap<String, Object> map = parseBtDevice2Map(device);
//                    Log.d(TAG, "ACTION_ACL_CONNECTED, " + map.get("name"));
                    Toast.makeText(context, "ACTION_ACL_CONNECTED", Toast.LENGTH_SHORT).show();
                }
                // 有远程设备断开连接
                else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                    // 当前远程蓝牙设备
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    HashMap<String, Object> map = parseBtDevice2Map(device);
//                    Log.d(TAG, "ACTION_ACL_DISCONNECTED, " + map.get("name"));
                    Toast.makeText(context, "ACTION_ACL_DISCONNECTED", Toast.LENGTH_SHORT).show();
                }
            } catch (Throwable t) {
//                t.printStacktrace();
            }
        }
    }

    private void initTitle() {
        titleContent.setText("蓝牙连接");
        titleRight.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_back, R.id.title_right, R.id.startBluetooth, R.id.closeBluetooth, R.id.scanBluetooth, R.id.scanMatchedBluetooth,
            R.id.scanVisibilityBluetooth, R.id.scanPairBluetooth, R.id.scanStartBluetooth})
    public void focusClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showToast("点击了提交");
                break;
            case R.id.startBluetooth:// 开启蓝牙 OK
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENBLE_BT);
                break;
            case R.id.closeBluetooth:// 关闭蓝牙 OK
                getBAdapter(BluetoothConnect.this).disable();
                break;
            case R.id.scanBluetooth:// 扫描新设备  OK
                if (getBAdapter(BluetoothConnect.this).startDiscovery()) {
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);
                }
                break;
            case R.id.scanMatchedBluetooth:// 扫描已匹配设备
                Set<BluetoothDevice> list = getBAdapter(BluetoothConnect.this).getBondedDevices();
                Log.i("scanBluetooth", new Gson().toJson(list));
                break;
            case R.id.scanVisibilityBluetooth:// 开启可见性
                // PS：如果在此设备上没有打开蓝牙，则启用设备可见性将会自动启用蓝牙
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);// 300为可见性的时间
                startActivity(discoverableIntent);
                break;
            case R.id.scanPairBluetooth:// 配对
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    //Android 4.4 API 19 以上才开放Bond接口
//                    device.createBond();
//                } else {
//                    //API 19 以下用反射调用Bond接口
//                    try {
//                        device.getClass().getMethod("connect").invoke(device);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                break;
            case R.id.scanStartBluetooth:// 开启服务端,初始化服务端
                // 开启一个子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BluetoothServerSocket mmServerSocket = null;
                        try {
                            mmServerSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("Name", UUID.fromString(SPP_UUID));
                            if (mmServerSocket == null) {
                                showToast("开启服务异常");
                            } else {
                                BluetoothSocket socket = mmServerSocket.accept();
                                showToast("开启服务成功");

                                // 读取客户端传过来的数据？并且用Handle去刷新UI？
                                InputStream mmInStream = socket.getInputStream();// 读取程序输入的数据
                                byte[] buffer = new byte[1024];
                                int bytes = mmInStream.read(buffer);
                                String data = new String(buffer, 0, bytes, "UTF-8");
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = data;
                                mHandler.sendMessage(msg);
                                mmInStream.close();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.scanCustomerBluetooth:// 创建客户端
//                BluetoothSocket mBlueSocket = BluetoothAdapter.getDefaultAdapter().getRemoteDevice().createInsecureRfcommSocketToServiceRecord(SPP_UUID);
//                try {
//                    mBlueSocket.connect();
//                    OutputStream dataOut = new DataOutputStream(mBlueSocket.getOutputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            default:
                break;
        }
    }

    // 通过handler 将接收的数据回传到主线程
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.i("scanBluetooth", msg.obj + "--------------");
                    showToast("接收成功，结果为：" + msg.obj);
                    break;
            }
        }
    };

    // 接收扫描后的结果
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int bs = 0;
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                device.createBond();// 低功耗蓝牙配对

                // 开始配对
//                try {
//                    // 如果想要取消已经配对的设备，只需要将creatBond改为removeBond
//                    Method method = BluetoothDevice.class.getMethod("createBond");
//                    Log.e(getPackageName(), "开始配对");
//                    method.invoke(device);
//                    //绑定状态发生变化
//                    IntentFilter intentFilter = new IntentFilter();
//                    intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                Log.i("scanBluetooth", device.getName() + "--------------");
                if (device != null) {
                    // 坐下对比，如果已经有了这个设备信息就不在重复添加
                    for (int i = 0; i < list.size(); i++) {
                        if (device.getAddress().equals(list.get(i).getEqAddress())) {
                            bs = 1;
                        }
                    }
                    if (bs == 0) {
                        BBluetoothConnect bBluetoothConnect = new BBluetoothConnect();
                        bBluetoothConnect.setEqName(device.getName());
                        bBluetoothConnect.setEqAddress(device.getAddress());
                        list.add(bBluetoothConnect);
                        adBluetoothConnect = new ADBluetoothConnect(BluetoothConnect.this, list, R.layout.itme_bluetooth_connect);
                        bluetoothList.setLayoutManager(new LinearLayoutManager(BluetoothConnect.this));// RecyclerView需要配置，ListView不需要
                        bluetoothList.setAdapter(adBluetoothConnect);
                        // 点击整个item
                        // 这里的new使用的是适配器里面的click
                        adBluetoothConnect.setOnItemClickListener(new ADBluetoothConnect.itemClick() {
                            @Override
                            public void onClick(int position) {
                                already = 0;
                                BBluetoothConnect bBluetoothConnect = list.get(position);
                                CustomDialog dialog = new CustomDialog(BluetoothConnect.this);
                                dialog.setMessage("这是一个自定义Dialog")
                                        .setImageResId(R.mipmap.ic_launcher)
                                        .setTitle("提示")
                                        .setSingle(false)
                                        .setOnClickBottomListener(new CustomDialog.OnClickBottomListener() {
                                            @Override
                                            public void onPositiveClick() {
                                                dialog.dismiss();
//                                                Log.i("DIALOG", "onPositiveClick--------点击了OK");
                                                // 停止扫描
                                                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                                                // 查询是否在已配对列表里面
                                                // 获得已匹配的设备*********************
                                                Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                                                for (BluetoothDevice bondedDevice : bondedDevices) {
                                                    if (bBluetoothConnect.getEqAddress().equals(bondedDevice.getAddress())) {
                                                        already = 1;
                                                    }
                                                }
                                                if (already == 1) {
                                                    // 已经匹配过
                                                    // 开启客户端请求,即创建客户端
                                                    try {
                                                        BluetoothSocket mBlueSocket = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bBluetoothConnect.getEqAddress()).createInsecureRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
                                                        mBlueSocket.connect();
                                                        OutputStream outputStream = new DataOutputStream(mBlueSocket.getOutputStream());
                                                        // 传输数据？发送数据？
                                                        byte[] out = new byte[1024];
                                                        String name = "你好";
                                                        outputStream.write((name + "\n").getBytes("utf-8"));
                                                        // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞
                                                        // 步骤3：发送数据到服务端
                                                        outputStream.flush();
                                                        outputStream.close();
                                                        showToast("写入成功");
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    // 还没匹配过
                                                    // 进行配对
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                        //Android 4.4 API 19 以上才开放Bond接口
                                                        BluetoothDevice deviceConnect = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bBluetoothConnect.getEqAddress());
                                                        // 配对  OK
                                                        // ps：配对后
                                                        deviceConnect.createBond();
                                                        showToast("匹配成功");
                                                        // 连接
//                                                    deviceConnect.connectGatt(BluetoothConnect.this, true, new BluetoothGattCallback() {
//                                                        @Override
//                                                        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//                                                            Log.i("scanBluetooth", "--------------" + status);
//                                                            switch (status) {
//                                                                case BluetoothGatt.GATT_SUCCESS:
//                                                                    // 连接成功
//                                                                    showToast("连接成功");
//                                                                    break;
//                                                                case BluetoothProfile.STATE_CONNECTED:
//                                                                    // 发现蓝牙服务
//                                                                    break;
//                                                            }
//                                                            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
//                                                        }
//                                                    });

                                                    } else {
                                                        //API 19 以下用反射调用Bond接口
                                                        try {
//                                                        device.getClass().getMethod("connect").invoke(device);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onNegtiveClick() {
                                                dialog.dismiss();
                                                Log.i("DIALOG", "onNegtiveClick-------点击了NO");
                                            }
                                        }).show();
                            }
                        });
                    }
                }
            }
        }
    };


    @SuppressLint("MissingPermission")
    private static HashMap<String, Object> parseBtDevice2Map(BluetoothDevice device) {
        HashMap<String, Object> map = new HashMap<>();
        if (device != null) {
            try {
                map.put("name", device.getName());
                map.put("address", device.getAddress());
                map.put("bondState", device.getBondState());

                BluetoothClass btClass = device.getBluetoothClass();
                int majorClass = btClass.getMajorDeviceClass();
                int deviceClass = btClass.getDeviceClass();
                map.put("majorClass", majorClass);
                map.put("deviceClass", deviceClass);

                if (Build.VERSION.SDK_INT >= 18) {
                    map.put("type", device.getType());
                }
                // 已配对的设备，同时获取其uuids
                if (Build.VERSION.SDK_INT >= 15 && device.getBondState() == 12) {
                    ArrayList<String> uuids = new ArrayList<>();
                    ParcelUuid[] parcelUuids = device.getUuids();
                    if (parcelUuids != null && parcelUuids.length > 0) {
                        for (ParcelUuid parcelUuid : parcelUuids) {
                            if (parcelUuid != null && parcelUuid.getUuid() != null) {
                                uuids.add(parcelUuid.getUuid().toString());
                            }
                        }
                    }
                    map.put("uuids", uuids);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return map;
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }
}
