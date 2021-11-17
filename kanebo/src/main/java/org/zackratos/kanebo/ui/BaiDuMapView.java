package org.zackratos.kanebo.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;

import butterknife.BindView;
import butterknife.OnClick;

// 定位测试OK
public class BaiDuMapView extends BaseActivity {

    @BindView(R.id.title_content)
    TextView title_content;
    @BindView(R.id.title_back)
    ImageView title_back;
    @BindView(R.id.bmapView)
    MapView mapView;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Boolean isFirstLoc = true;// 是否首次定位
    private BaiduMap mBaiduMap;

    @Override
    protected int initView() {
        return R.layout.atc_baidumap;
    }

    @Override
    protected void initData() {
        title_content.setText("百度地图");
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);// 开启地图的定位图层
//        View child = mapView.getChildAt(1);
//        if (child != null) {
//            child.setVisibility(View.INVISIBLE);
//        }
        // 定位初始化
        mLocationClient = new LocationClient(this);
        // 通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setNeedNewVersionRgc(true);
        option.setScanSpan(1000);
        // 设置locationClientOption
        mLocationClient.setLocOption(option);
        // 注册LocationListener监听器
        mLocationClient.registerLocationListener(myListener);
        // 开启地图定位图层
        mLocationClient.start();
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

//            String addr = bdLocation.getAddrStr();    //获取详细地址信息
//            String country = bdLocation.getCountry();    //获取国家
//            String province = bdLocation.getProvince();    //获取省份
//            String city = bdLocation.getCity();    //获取城市
//            String district = bdLocation.getDistrict();    //获取区县
//            String street = bdLocation.getStreet();    //获取街道信息
//            String adcode = bdLocation.getAdCode();    //获取adcode
//            String town = bdLocation.getTown();    //获取乡镇信息

//            Log.i("BaiDu", bdLocation + "");
            Log.i("BaiDu", bdLocation.getAddrStr() + "--" + bdLocation.getCountry() + "--" + bdLocation.getProvince()
                    + "--" + bdLocation.getCity() + "--" + bdLocation.getDistrict() + "--" + bdLocation.getStreet() + "--" + bdLocation.getAdCode() + "--" + bdLocation.getTown());

            //mapView 销毁后不在处理新接收的位置
            if (bdLocation == null || mapView == null) {
                Log.i("BaiDu", "locData.toString()");
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(bdLocation.getDirection())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
//            Log.i("BaiDu", "-----------------locData.toString()");
            mBaiduMap.setMyLocationData(locData);

            // 显示定位点
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(17.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }


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

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }


}
