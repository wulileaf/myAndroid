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

// θηθΏζ₯
public class BluetoothConnect extends BaseActivity {

    private final int REQUEST_ENBLE_BT = 1;
    private final String SPP_UUID = "fa87c0d0-afac-11de-8a39-0800200c9a66";
    private Context context;
    private BroadcastReceiver btScanReceiver;
    private List<BBluetoothConnect> list = new ArrayList<>();
    private ADBluetoothConnect adBluetoothConnect;
    private int already = 0;// ε€ζ­ζ­€θ?Ύε€ζ―ε¦ε·²η»εΉιθΏδΊ

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
            // θ―΄ζζ­€θ?Ύε€δΈζ―ζθηζδ½
            showToast("ζ­€θ?Ύε€δΈζ―ζθη");
        } else {
            // ζ²‘ζεΌε―θη
            if (!getBAdapter(BluetoothConnect.this).isEnabled()) {
                showToast("ζ²‘ζεΌε―θη");
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

    // ζ₯ζΆθηηΆζε¨ζεΉΏζ­
    public class onReceBluetoothStatue extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();// θ·εεΉΏζ­εΊε?ζ ΌεΌ
                // θηεΌε³ηΆζεε
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    //θ·εθηεΉΏζ­δΈ­ηθηζ°ηΆζ
                    int blueNewState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    //θ·εθηεΉΏζ­δΈ­ηθηζ§ηΆζ
                    int blueOldState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, 0);
                    switch (blueNewState) {
                        //ζ­£ε¨ζεΌθη
                        case BluetoothAdapter.STATE_TURNING_ON: {
                            Toast.makeText(context, "STATE_TURNING_ON", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //θηε·²ζεΌ
                        case BluetoothAdapter.STATE_ON: {
                            Toast.makeText(context, "STATE_ON", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //ζ­£ε¨ε³ι­θη
                        case BluetoothAdapter.STATE_TURNING_OFF: {
                            Toast.makeText(context, "STATE_TURNING_OFF", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //θηε·²ε³ι­
                        case BluetoothAdapter.STATE_OFF: {
                            Toast.makeText(context, "STATE_OFF", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                /*
                 * ζ¬ζΊηθηθΏζ₯ηΆζεηεε
                 *
                 * ηΉζβζ δ»»δ½θΏζ₯βββθΏζ₯δ»»ζθΏη¨θ?Ύε€βοΌδ»₯εβθΏζ₯δ»»δΈζε€δΈͺθΏη¨θ?Ύε€βββζ δ»»δ½θΏζ₯βηηΆζεεοΌ
                 * ε³βθΏζ₯η¬¬δΈδΈͺθΏη¨θ?Ύε€βδΈβζ­εΌζεδΈδΈͺθΏη¨θ?Ύε€βζΆζδΌθ§¦εθ―₯Action
                 */
                else if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
                    //θ·εθηεΉΏζ­δΈ­ηθηθΏζ₯ζ°ηΆζ
                    int newConnState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0);
                    //θ·εθηεΉΏζ­δΈ­ηθηθΏζ₯ζ§ηΆζ
                    int oldConnState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, 0);
                    // ε½εθΏη¨θηθ?Ύε€
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    HashMap<String, Object> map = parseBtDevice2Map(device);
                    switch (newConnState) {
                        //θηθΏζ₯δΈ­
                        case BluetoothAdapter.STATE_CONNECTING: {
//                            Log.d(TAG, "STATE_CONNECTING, " + map.get("name"));
                            Toast.makeText(context, "STATE_CONNECTING", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //θηε·²θΏζ₯
                        case BluetoothAdapter.STATE_CONNECTED: {
//                            Log.d(TAG, "STATE_CONNECTED, " + map.get("name"));
                            Toast.makeText(context, "STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //θηζ­εΌθΏζ₯δΈ­
                        case BluetoothAdapter.STATE_DISCONNECTING: {
//                            Log.d(TAG, "STATE_DISCONNECTING, " + map.get("name"));
                            Toast.makeText(context, "STATE_DISCONNECTING", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        //θηε·²ζ­εΌθΏζ₯
                        case BluetoothAdapter.STATE_DISCONNECTED: {
//                            Log.d(TAG, "STATE_DISCONNECTED, " + map.get("name"));
                            Toast.makeText(context, "STATE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                // ζθΏη¨θ?Ύε€ζεθΏζ₯θ³ζ¬ζΊ
                else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                    // ε½εθΏη¨θηθ?Ύε€
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    HashMap<String, Object> map = parseBtDevice2Map(device);
//                    Log.d(TAG, "ACTION_ACL_CONNECTED, " + map.get("name"));
                    Toast.makeText(context, "ACTION_ACL_CONNECTED", Toast.LENGTH_SHORT).show();
                }
                // ζθΏη¨θ?Ύε€ζ­εΌθΏζ₯
                else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                    // ε½εθΏη¨θηθ?Ύε€
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
        titleContent.setText("θηθΏζ₯");
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
                showToast("ηΉε»δΊζδΊ€");
                break;
            case R.id.startBluetooth:// εΌε―θη OK
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENBLE_BT);
                break;
            case R.id.closeBluetooth:// ε³ι­θη OK
                getBAdapter(BluetoothConnect.this).disable();
                break;
            case R.id.scanBluetooth:// ζ«ζζ°θ?Ύε€  OK
                if (getBAdapter(BluetoothConnect.this).startDiscovery()) {
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);
                }
                break;
            case R.id.scanMatchedBluetooth:// ζ«ζε·²εΉιθ?Ύε€
                Set<BluetoothDevice> list = getBAdapter(BluetoothConnect.this).getBondedDevices();
                Log.i("scanBluetooth", new Gson().toJson(list));
                break;
            case R.id.scanVisibilityBluetooth:// εΌε―ε―θ§ζ§
                // PSοΌε¦ζε¨ζ­€θ?Ύε€δΈζ²‘ζζεΌθηοΌεε―η¨θ?Ύε€ε―θ§ζ§ε°δΌθͺε¨ε―η¨θη
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);// 300δΈΊε―θ§ζ§ηζΆι΄
                startActivity(discoverableIntent);
                break;
            case R.id.scanPairBluetooth:// ιε―Ή
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    //Android 4.4 API 19 δ»₯δΈζεΌζΎBondζ₯ε£
//                    device.createBond();
//                } else {
//                    //API 19 δ»₯δΈη¨εε°θ°η¨Bondζ₯ε£
//                    try {
//                        device.getClass().getMethod("connect").invoke(device);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                break;
            case R.id.scanStartBluetooth:// εΌε―ζε‘η«―,εε§εζε‘η«―
                // εΌε―δΈδΈͺε­ηΊΏη¨
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BluetoothServerSocket mmServerSocket = null;
                        try {
                            mmServerSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("Name", UUID.fromString(SPP_UUID));
                            if (mmServerSocket == null) {
                                showToast("εΌε―ζε‘εΌεΈΈ");
                            } else {
                                BluetoothSocket socket = mmServerSocket.accept();
                                showToast("εΌε―ζε‘ζε");

                                // θ―»εε?’ζ·η«―δΌ θΏζ₯ηζ°ζ?οΌεΉΆδΈη¨Handleε»ε·ζ°UIοΌ
                                InputStream mmInStream = socket.getInputStream();// θ―»εη¨εΊθΎε₯ηζ°ζ?
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
            case R.id.scanCustomerBluetooth:// εε»Ίε?’ζ·η«―
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

    // ιθΏhandler ε°ζ₯ζΆηζ°ζ?εδΌ ε°δΈ»ηΊΏη¨
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.i("scanBluetooth", msg.obj + "--------------");
                    showToast("ζ₯ζΆζεοΌη»ζδΈΊοΌ" + msg.obj);
                    break;
            }
        }
    };

    // ζ₯ζΆζ«ζεηη»ζ
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int bs = 0;
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                device.createBond();// δ½εθθηιε―Ή

                // εΌε§ιε―Ή
//                try {
//                    // ε¦ζζ³θ¦εζΆε·²η»ιε―Ήηθ?Ύε€οΌεͺιθ¦ε°creatBondζΉδΈΊremoveBond
//                    Method method = BluetoothDevice.class.getMethod("createBond");
//                    Log.e(getPackageName(), "εΌε§ιε―Ή");
//                    method.invoke(device);
//                    //η»ε?ηΆζεηεε
//                    IntentFilter intentFilter = new IntentFilter();
//                    intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


                Log.i("scanBluetooth", device.getName() + "--------------");
                if (device != null) {
                    // εδΈε―Ήζ―οΌε¦ζε·²η»ζδΊθΏδΈͺθ?Ύε€δΏ‘ζ―ε°±δΈε¨ιε€ζ·»ε 
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
                        bluetoothList.setLayoutManager(new LinearLayoutManager(BluetoothConnect.this));// RecyclerViewιθ¦ιη½?οΌListViewδΈιθ¦
                        bluetoothList.setAdapter(adBluetoothConnect);
                        // ηΉε»ζ΄δΈͺitem
                        // θΏιηnewδ½Ώη¨ηζ―ιιε¨ιι’ηclick
                        adBluetoothConnect.setOnItemClickListener(new ADBluetoothConnect.itemClick() {
                            @Override
                            public void onClick(int position) {
                                already = 0;
                                BBluetoothConnect bBluetoothConnect = list.get(position);
                                CustomDialog dialog = new CustomDialog(BluetoothConnect.this);
                                dialog.setMessage("θΏζ―δΈδΈͺθͺε?δΉDialog")
                                        .setImageResId(R.mipmap.ic_launcher)
                                        .setTitle("ζη€Ί")
                                        .setSingle(false)
                                        .setOnClickBottomListener(new CustomDialog.OnClickBottomListener() {
                                            @Override
                                            public void onPositiveClick() {
                                                dialog.dismiss();
//                                                Log.i("DIALOG", "onPositiveClick--------ηΉε»δΊOK");
                                                // εζ­’ζ«ζ
                                                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                                                // ζ₯θ―’ζ―ε¦ε¨ε·²ιε―Ήεθ‘¨ιι’
                                                // θ·εΎε·²εΉιηθ?Ύε€*********************
                                                Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
                                                for (BluetoothDevice bondedDevice : bondedDevices) {
                                                    if (bBluetoothConnect.getEqAddress().equals(bondedDevice.getAddress())) {
                                                        already = 1;
                                                    }
                                                }
                                                if (already == 1) {
                                                    // ε·²η»εΉιθΏ
                                                    // εΌε―ε?’ζ·η«―θ―·ζ±,ε³εε»Ίε?’ζ·η«―
                                                    try {
                                                        BluetoothSocket mBlueSocket = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bBluetoothConnect.getEqAddress()).createInsecureRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
                                                        mBlueSocket.connect();
                                                        OutputStream outputStream = new DataOutputStream(mBlueSocket.getOutputStream());
                                                        // δΌ θΎζ°ζ?οΌειζ°ζ?οΌ
                                                        byte[] out = new byte[1024];
                                                        String name = "δ½ ε₯½";
                                                        outputStream.write((name + "\n").getBytes("utf-8"));
                                                        // ηΉε«ζ³¨ζοΌζ°ζ?ηη»ε°Ύε δΈζ’θ‘η¬¦ζε―θ?©ζε‘ε¨η«―ηreadline()εζ­’ι»ε‘
                                                        // ζ­₯ιͺ€3οΌειζ°ζ?ε°ζε‘η«―
                                                        outputStream.flush();
                                                        outputStream.close();
                                                        showToast("εε₯ζε");
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                } else {
                                                    // θΏζ²‘εΉιθΏ
                                                    // θΏθ‘ιε―Ή
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                        //Android 4.4 API 19 δ»₯δΈζεΌζΎBondζ₯ε£
                                                        BluetoothDevice deviceConnect = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(bBluetoothConnect.getEqAddress());
                                                        // ιε―Ή  OK
                                                        // psοΌιε―Ήε
                                                        deviceConnect.createBond();
                                                        showToast("εΉιζε");
                                                        // θΏζ₯
//                                                    deviceConnect.connectGatt(BluetoothConnect.this, true, new BluetoothGattCallback() {
//                                                        @Override
//                                                        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
//                                                            Log.i("scanBluetooth", "--------------" + status);
//                                                            switch (status) {
//                                                                case BluetoothGatt.GATT_SUCCESS:
//                                                                    // θΏζ₯ζε
//                                                                    showToast("θΏζ₯ζε");
//                                                                    break;
//                                                                case BluetoothProfile.STATE_CONNECTED:
//                                                                    // εη°θηζε‘
//                                                                    break;
//                                                            }
//                                                            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
//                                                        }
//                                                    });

                                                    } else {
                                                        //API 19 δ»₯δΈη¨εε°θ°η¨Bondζ₯ε£
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
                                                Log.i("DIALOG", "onNegtiveClick-------ηΉε»δΊNO");
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
                // ε·²ιε―Ήηθ?Ύε€οΌεζΆθ·εεΆuuids
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
