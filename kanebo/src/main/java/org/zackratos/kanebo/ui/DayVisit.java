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

// ????????????
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

    // ?????????????????????????????????????????????????????????
    public NetworkChangReceiver networkChangReceiver = new NetworkChangReceiver();
    private adaDayVisit actMain;
    private Double lat, lng = null;
    private String address = null;
    private Double dlat, dlon = null;
    public AMapLocationClient mlocationClient;// ??????mlocationClient??????
    public AMapLocationClientOption mLocationOption = null;// ??????mLocationOption??????
    private List<B_Act_DayVisit> list;
    private JSONArray jsonArray;
    private int netTpye;// ?????????????????? 0??????????????????1????????????2????????????
    private netReceiver netReceiver = new netReceiver();

    @Override
    protected int initView() {
        return R.layout.atc_day_visit;
    }

    @Override
    protected void initData() {
        initTitle();
//        initRadioBroadcast();// ????????????
        initLocation();
        netTpye = getNetWork();
        initBroadcast();
        initRefesh();
    }

    // ???????????????????????????
    private void initBroadcast() {
        registerReceiver(netReceiver, new BroadCastHandle().initNetworChanges());// ????????????
    }

    // ???????????????
    public class netReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                int network = new BaseSp(context).getInt("NetWork");// ?????????
                new BaseSp(context).saveNetWork(networkInfo.getType());// ???????????????????????????
                if (network != networkInfo.getType()) {
                    switch (networkInfo.getType()) {
                        case TYPE_MOBILE:
                            Toast.makeText(context, "????????????????????????", Toast.LENGTH_SHORT).show();
//                            netType = networkInfo.getType();
                            // ??????????????????????????????????????????
                            // ????????????
                            if (netTpye == 0) {
                                netTpye = TYPE_MOBILE;
                                try {
                                    initInterface(Tools.getUserLoginMsg(DayVisit.this).getString("userid"), Tools.getDate(), String.valueOf(IdCode.PAGE_CODE));// ????????????
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case TYPE_WIFI:
                            Toast.makeText(context, "????????????wifi??????", Toast.LENGTH_SHORT).show();
                            // ???????????????????????????????????????
                            // ????????????
                            if (netTpye == 0) {
                                netTpye = TYPE_WIFI;
                                try {
                                    initInterface(Tools.getUserLoginMsg(DayVisit.this).getString("userid"), Tools.getDate(), String.valueOf(IdCode.PAGE_CODE));// ????????????
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
                new BaseSp(context).saveNetWork(0);// ???????????????????????????
                Toast.makeText(context, "?????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ???????????????
    private void initLocation() {

//        LocationManager locationManager = getLocation();
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            showToast("???????????????????????????");
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

        mlocationClient = new AMapLocationClient(this);// ???????????????
        mLocationOption = new AMapLocationClientOption();// ???????????????????????????
        mLocationOption.setOnceLocation(true);// ????????????????????????
        mlocationClient.setLocationOption(mLocationOption);// ??????????????????
        mlocationClient.startLocation();
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                lat = aMapLocation.getLatitude();
                lng = aMapLocation.getLongitude();
                address = aMapLocation.getAddress();
                try {
                    initInterface(Tools.getUserLoginMsg(DayVisit.this).getString("userid"), Tools.getDate(), String.valueOf(IdCode.PAGE_CODE));// ????????????
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initOnClick() {
        // ??????item
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

        // ??????item???????????????
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

                // ?????????????????? OK
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
//                    Toast toast = Toast.makeText(DayVisit.this, "????????????????????????", 3000);
//                    toast.show();
//                } else {
//                    Toast toast = Toast.makeText(DayVisit.this, "????????????????????????", 3000);
//                    toast.show();
//                    Intent i = new Intent();
//                    i.setData(Uri.parse("http://daohang.amap.com/index.php?id=201&CustomID=C021100013023"));
//                    i.setAction(Intent.ACTION_VIEW);
//                    DayVisit.this.startActivity(i);
//                }

                // ???????????? OK

            }
        });
    }

    /**
     * ??????????????????????????????
     *
     * @param packageName ??????????????????????????????
     * @return ???????????????????????????
     * @author zuolongsnail
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private void initRadioBroadcast() {
        registerReceiver(networkChangReceiver, new BroadCastHandle().initNetworChanges());// ????????????
    }

    // ????????????
    private void initInterface(String userid, String date, String pagecode) {

        // ???????????????????????????
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
                    list = new ArrayList<>();// ???????????????????????????
                    // xml??????  ??????OK
                    String storeData = response.body().storeData;// ??????[{}]??????????????????OK
                    try {
                        jsonArray = new JSONArray(storeData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject msg = jsonArray.getJSONObject(i);
                            // ????????????
                            B_Act_DayVisit b_act_dayVisit = new B_Act_DayVisit();// ???????????????????????????
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
                        // new LinearLayoutManager(this) ?????????item
                        // new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)) ??????????????????
                        // new GridLayoutManager(this, 3) ?????????
                        recStore.setLayoutManager(new LinearLayoutManager(DayVisit.this));// RecyclerView???????????????ListView?????????
                        recStore.setAdapter(actMain);
                        initOnClick();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (response.body().success == 1) {
//                        showMidToast("????????????");
                    } else if (response.body().success == -3) {
                        showToast("???????????????" + response.body().errormsg);
                    } else {
                        showToast("??????????????????");
                    }
                }

                @Override
                public void onFailure(Call<XmlGetOutPlanHardwareStoreList> call, Throwable t) {
                    showToast("??????????????????,???????????????????????????" + t);
                }
            });
        } else {
            showToast("??????????????????????????????????????????????????????");
        }
    }

    private void initRefesh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableAutoLoadMore(true);//????????????????????????????????????????????????????????????
        refreshLayout.setEnableRefresh(true);//??????????????????????????????
        refreshLayout.setEnableLoadMore(true);//??????????????????????????????
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);//??????false??????????????????
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/);//??????false??????????????????
            }
        });
    }

    private void initTitle() {
        titleContent.setText("????????????");
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
                showToast("???????????????");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(networkChangReceiver);// ????????????
        unregisterReceiver(netReceiver);
    }


}
