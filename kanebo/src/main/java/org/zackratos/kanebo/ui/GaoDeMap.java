package org.zackratos.kanebo.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;

import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.CustomPopupWindow;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author leaf
 * @version 1.0
 * @Note 高德地图SDK实例
 */
public class GaoDeMap extends BaseActivity implements AMapLocationListener {

    private int ACCESS_COARSE_LOCATION_CODE = 1;
    private int ACCESS_FINE_LOCATION_CODE = 2;
    private int WRITE_EXTERNAL_STORAGE_CODE = 3;
    private int READ_EXTERNAL_STORAGE_CODE = 4;
    private int READ_PHONE_STATE_CODE = 5;
    private CustomPopupWindow mpop;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private Double mlatitude = null, mlongitude = null;
    private String maddress;
    private MapView map;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private AMapLocation amapLocation;
    private LocationSource.OnLocationChangedListener onLocationChangedListener;

    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.title_back)
    ImageView title_back;


    @Override
    protected int initView() {
        return R.layout.atc_gaodemap;
    }

    @Override
    protected void initData() {
        title_content.setText("高德地图");

        //SDK在Android 6.0下需要进行运行检测的权限如下：
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
        }

        map = findViewById(R.id.map);
        mpop = new CustomPopupWindow(GaoDeMap.this, null);
//        mpop.setOnItemClickListener(this);
        // 地图初始化
        map.onCreate(savedInstanceState());
        if (aMap == null) {
            aMap = map.getMap();
        }

        // 初始化定位功能
        mlocationClient = new AMapLocationClient(this);// 初始化定位
        mlocationClient.setLocationListener(this);// 设置定位监听

        mLocationOption = new AMapLocationClientOption();// 初始化定位参数配置
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        // 默认高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationOption.setLocationPurpose()
        // 单次定位有以下两种方式1,2
        // 获取一次定位结果：
        // 该方法默认为false。
        // 1
        mLocationOption.setOnceLocation(false);
        // 获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        // 2
        mLocationOption.setOnceLocationLatest(true);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 设置定位同时是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        mLocationOption.setMockEnable(true);
        // 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        // 给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        Log.i("aMap", "启动了定位");
        mlocationClient.startLocation();
//        new AMapLocationListener(){
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//
//            }
//        };
//        mlocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
//        mlocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

        // 设置定位蓝点
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//定位一次，且将视角移动到地图中心点
        // 设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        myLocationStyle.showMyLocation(true);
        // 设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数
//        myLocationStyle.myLocationIcon();
        // ----------------------------------------------------------------------------------
        //设置定位蓝点精度圆圈的边框颜色的方法
//        MyLocationStyle strokeColor(int color);
        //设置定位蓝点精度圆圈的填充颜色的方法
//        MyLocationStyle radiusFillColor(int color);
        //设置定位蓝点精度圈的边框宽度的方法
//        MyLocationStyle strokeWidth(float width);
        // ----------------------------------------------------------------------------------

        // 实现地图
        aMap.getUiSettings().setCompassEnabled(true);// 指南针
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//显示默认的定位按钮
        aMap.setTrafficEnabled(true);//显示实时交通状况(默认地图)
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        aMap.setMinZoomLevel(15);
        aMap.setMaxZoomLevel(20);
        aMap.setMyLocationStyle(myLocationStyle);
        Log.i("aMap", "启动了定位1111111111");

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mlatitude = marker.getPosition().latitude;
                mlongitude = marker.getPosition().longitude;
                maddress = marker.getTitle();
                Log.e("LJ", marker.getPosition().latitude + "经度");
                Log.e("LJ", marker.getPosition().longitude + "维度");
                Log.e("LJ", marker.getSnippet() + "名称1");
                Log.e("LJ", marker.getTitle() + "名称2");
                Log.e("LJ", mlatitude + "定位经度");
                Log.e("LJ", mlongitude + "定位维度");
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                LatLonPoint mStartPoint = new LatLonPoint(121.114992, 31.141427);// 强制定位青浦淀浦河
// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                LatLonPoint mEndPoint = new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude);
                return true;
            }
        });
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    // 底部弹出框的点击事件
//    @Override
//    public void setOnItemClick(View v) {
//
//    }

    @OnClick({R.id.title_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    // 定位回调监听
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
//        showToast("定位了");
        Log.i("aMap", "启动了定位++++++++++" + aMapLocation);
        Log.i("aMap", "启动了定位城市++++++++++" + aMapLocation.getErrorCode());
        if (amapLocation != null) {
//            try {
//                Log.i("aMap", "" + new JSONObject(aMapLocation.toString()));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息
                amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                amapLocation.getFloor();//获取当前室内定位的楼层
                amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);
//                onLocationChangedListener.onLocationChanged(amapLocation);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
