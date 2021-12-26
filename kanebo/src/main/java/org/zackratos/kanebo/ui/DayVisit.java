package org.zackratos.kanebo.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zackratos.basemode.mvp.BaseActivity;
import org.zackratos.basemode.mvp.BaseNetworkDetection;
import org.zackratos.basemode.mvp.BaseSp;
import org.zackratos.basemode.mvp.IPresenter;
import org.zackratos.kanebo.R;
import org.zackratos.kanebo.adapter.adaDayVisit;
import org.zackratos.kanebo.bean.B_Act_DayVisit;
import org.zackratos.kanebo.broadcastHandle.BroadCastHandle;
import org.zackratos.kanebo.networkRequestInterface.InterRetrofit;
import org.zackratos.kanebo.request.LeafRequest;
import org.zackratos.kanebo.tools.IdCode;
import org.zackratos.kanebo.tools.Tools;
import org.zackratos.kanebo.xml.XmlGetOutPlanHardwareStoreList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.net.ConnectivityManager.TYPE_WIFI;
import static android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
import static org.zackratos.basemode.mvp.BaseUrl.OPPLELTURL;

// 当日拜访
public class DayVisit extends BaseActivity {

    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rec_Store)
    RecyclerView recStore;

    // 如何在有无网络转为有网络的时候刷新页面
    public NetworkChangReceiver networkChangReceiver = new NetworkChangReceiver();
    private adaDayVisit actMain;
    private Double lat, lng = null;
    private String address = null;
    private Double dlat, dlon = null;
    public AMapLocationClient mlocationClient;// 声明mlocationClient对象
    public AMapLocationClientOption mLocationOption = null;// 声明mLocationOption对象
    private List<B_Act_DayVisit> list;
    private JSONArray jsonArray;
    private int netTpye;// 当前网络标识 0无网络连接，1无线网，2移动网络
    private netReceiver netReceiver = new netReceiver();

    @Override
    protected int initView() {
        return R.layout.atc_day_visit;
    }

    @Override
    protected void initData() {
        initTitle();
//        initRadioBroadcast();// 注册广播
        initLocation();
        netTpye = getNetWork();
        initBroadcast();
        initRefesh();
    }

    // 注册广播自己写方法
    private void initBroadcast() {
        registerReceiver(netReceiver, new BroadCastHandle().initNetworChanges());// 注册广播
    }

    // 自己写注册
    public class netReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                int network = new BaseSp(context).getInt("NetWork");// 先获取
                new BaseSp(context).saveNetWork(networkInfo.getType());// 在保存本次获取到的
                if (network != networkInfo.getType()) {
                    switch (networkInfo.getType()) {
                        case TYPE_MOBILE:
                            Toast.makeText(context, "正在使用移动网络", Toast.LENGTH_SHORT).show();
//                            netType = networkInfo.getType();
                            // 在网络由无网络到有网络的时候
                            // 发送广播
                            if (netTpye == 0) {
                                netTpye = TYPE_MOBILE;
                                try {
                                    initInterface(Tools.getUserLoginMsg(DayVisit.this).getString("userid"), Tools.getDate(), String.valueOf(IdCode.PAGE_CODE));// 测试成功
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case TYPE_WIFI:
                            Toast.makeText(context, "正在使用wifi网络", Toast.LENGTH_SHORT).show();
                            // 在网络由网络到有网络的时候
                            // 发送广播
                            if (netTpye == 0) {
                                netTpye = TYPE_WIFI;
                                try {
                                    initInterface(Tools.getUserLoginMsg(DayVisit.this).getString("userid"), Tools.getDate(), String.valueOf(IdCode.PAGE_CODE));// 测试成功
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {
                new BaseSp(context).saveNetWork(0);// 在保存本次获取到的
                Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 初始化定位
    private void initLocation() {

//        LocationManager locationManager = getLocation();
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            showToast("定位失败，没有权限");
//            return;
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    lat = location.getLatitude();
//                    lng = location.getLongitude();
//                }
//
//                @Override
//                public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                }
//
//                @Override
//                public void onProviderEnabled(String s) {
//
//                }
//
//                @Override
//                public void onProviderDisabled(String s) {
//
//                }
//            });
//        }

        mlocationClient = new AMapLocationClient(this);// 初始化定位
        mLocationOption = new AMapLocationClientOption();// 初始化定位参数配置
        mLocationOption.setOnceLocation(true);// 设置单次定位结果
        mlocationClient.setLocationOption(mLocationOption);// 设置定位参数
        mlocationClient.startLocation();
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                lat = aMapLocation.getLatitude();
                lng = aMapLocation.getLongitude();
                address = aMapLocation.getAddress();
                try {
                    initInterface(Tools.getUserLoginMsg(DayVisit.this).getString("userid"), Tools.getDate(), String.valueOf(IdCode.PAGE_CODE));// 测试成功
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initOnClick() {
        // 点击item
        actMain.setOnItemClickListener(new adaDayVisit.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                try {
//                    Log.i("778899", "============11111122222");
                    String jsonObject = jsonArray.get(position).toString();
                    Intent intent = new Intent(DayVisit.this, DayVisitDetails.class);
                    intent.putExtra("StoreMsg", jsonObject);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // 点击item中某个区域
        actMain.setOnRegionClikListener(new adaDayVisit.OnRegionClikListener() {
            @Override
            public void onClick(int position) {

                try {
                    String jsonObject = jsonArray.get(position).toString();
                    dlat = new JSONObject(jsonObject).getDouble("Lat");
                    dlon = new JSONObject(jsonObject).getDouble("Lng");
                    Log.i("GDMAP", jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 拉起高德地图 OK
//                Intent intent = null;
//                try {
//                    intent = Intent.getIntent("androidamap://route?sourceApplication=softname&slat="
//                            + lat
//                            + "&slon="
//                            + lng
//                            + "&sname="
//                            + address
//                            + "&dlat="
//                            + dlat
//                            + "&dlon="
//                            + dlon
//                            + "&dname="
//                            + "" + "&dev=0&m=0&t=1");
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                if (isInstallByread("com.autonavi.minimap")) {
//                    startActivity(intent);
//                    Toast toast = Toast.makeText(DayVisit.this, "高德地图正在启动", 3000);
//                    toast.show();
//                } else {
//                    Toast toast = Toast.makeText(DayVisit.this, "高德地图没有安装", 3000);
//                    toast.show();
//                    Intent i = new Intent();
//                    i.setData(Uri.parse("http://daohang.amap.com/index.php?id=201&CustomID=C021100013023"));
//                    i.setAction(Intent.ACTION_VIEW);
//                    DayVisit.this.startActivity(i);
//                }

                // 百度地图 OK

            }
        });
    }

    /**
     * 推断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     * @author zuolongsnail
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private void initRadioBroadcast() {
        registerReceiver(networkChangReceiver, new BroadCastHandle().initNetworChanges());// 注册广播
    }

    // 接口请求
    private void initInterface(String userid, String date, String pagecode) {

        // 查看手机的网络环境
        Boolean yrn = BaseNetworkDetection.isNetworkAvailable(DayVisit.this);
        if (yrn) {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userid);
            params.put("isAll", "0");
            params.put("lastTime", date);
            params.put("startRow", "0");
            params.put("channeltype", "1");
            params.put("keyword", "");
            params.put("lng", "");
            params.put("lat", "");
            params.put("pageIndex", pagecode);
            InterRetrofit interRetrofit = new LeafRequest().getXml(OPPLELTURL);
            Call<XmlGetOutPlanHardwareStoreList> datajsa = interRetrofit.storeList(params);
            datajsa.enqueue(new Callback<XmlGetOutPlanHardwareStoreList>() {
                @Override
                public void onResponse(Call<XmlGetOutPlanHardwareStoreList> call, Response<XmlGetOutPlanHardwareStoreList> response) {
                    list = new ArrayList<>();// 这步怎么简化？？？
                    // xml解析  测试OK
                    String storeData = response.body().storeData;// 获取[{}]格式数据测试OK
                    try {
                        jsonArray = new JSONArray(storeData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject msg = jsonArray.getJSONObject(i);
                            // 展示页面
                            B_Act_DayVisit b_act_dayVisit = new B_Act_DayVisit();// 这步怎么简化？？？
                            b_act_dayVisit.setStorename(msg.getString("StoreName"));
                            b_act_dayVisit.setStorecode(msg.getString("StoreCode"));
                            b_act_dayVisit.setIsnewstore(msg.getString("IsNewStore"));
                            b_act_dayVisit.setContact(msg.getString("Contact"));
                            b_act_dayVisit.setContactTel(msg.getString("ContactTel"));
                            b_act_dayVisit.setStorelevelname(msg.getString("StoreLevelName"));
                            b_act_dayVisit.setStoretypename(msg.getString("StoreTypeName"));
                            b_act_dayVisit.setAddress(msg.getString("Address"));
                            if (TextUtils.isEmpty(msg.getString("Lat"))) {
                                b_act_dayVisit.setLat(0.00);
                            } else {
                                b_act_dayVisit.setLat(Double.valueOf(msg.getString("Lat")));
                            }
                            if (TextUtils.isEmpty(msg.getString("Lng"))) {
                                b_act_dayVisit.setLng(0.00);
                            } else {
                                b_act_dayVisit.setLng(Double.valueOf(msg.getString("Lng")));
                            }
                            list.add(b_act_dayVisit);
                        }
                        actMain = new adaDayVisit(DayVisit.this, list, R.layout.item_dayvisit, lat, lng);
                        // new LinearLayoutManager(this) 正常的item
                        // new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)) 左右可以拖动
                        // new GridLayoutManager(this, 3) 瀑布流
                        recStore.setLayoutManager(new LinearLayoutManager(DayVisit.this));// RecyclerView需要配置，ListView不需要
                        recStore.setAdapter(actMain);
                        initOnClick();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (response.body().success == 1) {
//                        showMidToast("抓取成功");
                    } else if (response.body().success == -3) {
                        showToast("抓取失败：" + response.body().errormsg);
                    } else {
                        showToast("抓取数据异常");
                    }
                }

                @Override
                public void onFailure(Call<XmlGetOutPlanHardwareStoreList> call, Throwable t) {
                    showToast("抓取数据失败,请检查网络是否正常" + t);
                }
            });
        } else {
            showToast("当前手机未连接网络，请开启网络后重试");
        }
    }

    private void initRefesh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void initTitle() {
        titleContent.setText("当日拜访");
        titleRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected IPresenter getPresenter() {
        return null;
    }

    @Override
    protected Intent mainIntent(Context context) {
        return null;
    }

//    @Override
//    protected void upLocationData(LocationManager location) {
//        lat = location.getLatitude();
//        lng = location.getLongitude();
//        Log.i("LocationMsg", lat + "" + lng);
//    }


    @OnClick({R.id.title_back, R.id.title_right})
    public void focusClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                showToast("点击了提交");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(networkChangReceiver);// 销毁广播
        unregisterReceiver(netReceiver);
    }


}
