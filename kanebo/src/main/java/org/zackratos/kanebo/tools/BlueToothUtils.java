package org.zackratos.kanebo.tools;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

/**
 * 蓝牙工具类
 */
public class BlueToothUtils {

    private Activity activity;
    private BluetoothAdapter bluetoothAdapter;
    //蓝牙是否可用
    private boolean bleEnable = false;

    public BlueToothUtils(Activity activity) {
        this.activity = activity;
        bleEnable = checkBlueToothEnable();
    }

    /**
     * 检测设备是否支持蓝牙
     */
    public boolean checkBlueToothEnable() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
//            ToastUtils.showShort("该设备不支持蓝牙");
            Toast.makeText(activity, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show();
            return false;
        } else {
//            ToastUtils.showShort("该设备能支持蓝牙");
            Toast.makeText(activity, "该设备能支持蓝牙", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    /**
     * 让用户去设置蓝牙
     */
    public void setBlueTooth() {
        if (bleEnable) {
            Intent blueTooth = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            activity.startActivity(blueTooth);
        }
    }

    /**
     * 打开蓝牙
     */
    public void onBlueTooth() {
        if (bleEnable) {
            if (bluetoothAdapter.isEnabled()) {
//                ToastUtils.showShort("蓝牙已打开，不用在点了~");
                Toast.makeText(activity, "蓝牙已打开，不用在点了~", Toast.LENGTH_SHORT).show();
            } else {
                bluetoothAdapter.enable();
            }
        }
    }

    /**
     * 关闭蓝牙
     */
    public void offBlueTooth() {
        if (bleEnable) {
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
            } else {
//                ToastUtils.showShort("蓝牙已关闭，不用在点了~");
                Toast.makeText(activity, "蓝牙已关闭，不用在点了~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取已经配对的设备
     */
    public Set<BluetoothDevice> getConnectedDevices() {
        if (bleEnable) {
            if (bluetoothAdapter.isEnabled()) {
                return bluetoothAdapter.getBondedDevices();
            }
        }
        return null;
    }

    /**
     * 可发现模式
     * 默认情况下，设备的可发现模式会持续120秒。
     * 通过给Intent对象添加EXTRA_DISCOVERABLE_DURATION附加字段，可以定义不同持续时间。
     * 应用程序能够设置的最大持续时间是3600秒
     */
    public void discoverableDuration() {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //定义持续时间
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        activity.startActivity(discoverableIntent);
    }

    /**
     * 扫描蓝牙，会走广播
     */
    public void startDiscovery() {
        if (bleEnable) {
            if (!bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.startDiscovery();
            }
//            ToastUtils.showShort("扫描蓝牙设备");
            Toast.makeText(activity, "扫描蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 停止扫描
     */
    public void stopDiscovery() {
        if (bleEnable) {
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }
        }
    }

    /**
     * 扫描蓝牙
     */
    public void startScan() {
        if (bleEnable) {
            bluetoothAdapter.getBluetoothLeScanner().startScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    //信号强度，是负的，数值越大代表信号强度越大
                    result.getRssi();
                    super.onScanResult(callbackType, result);
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                }
            });
//            ToastUtils.showShort("扫描蓝牙设备");
        }
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        if (bleEnable) {
            bluetoothAdapter.getBluetoothLeScanner().stopScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                }
            });
        }
    }

    /**
     * 连接设备
     */
    public void connectGatt(final BluetoothDevice device) {
        stopDiscovery();
        if (bleEnable) {
            device.connectGatt(activity, true, new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    switch (status) {
                        case BluetoothGatt.GATT_SUCCESS:
                            //连接成功
                            break;
                        case BluetoothProfile.STATE_CONNECTED:
                            //发现蓝牙服务
                            break;
                    }
                    super.onConnectionStateChange(gatt, status, newState);
                }
            });
        }
    }
}