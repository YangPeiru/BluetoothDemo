package com.example.yang.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.yang.myapplication.R;
import com.example.yang.myapplication.utils.YUtils;

import org.xutils.x;

/**
 * Created by ypr on 2016-06-17 10:08
 * 描述:显示地图的页面
 * TODO:
 */
public class GDMapFragment extends BaseFragment implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener, AMap.OnMapClickListener {
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器

    /**
     * 添加的覆盖物标志
     */
    private Marker currentMarker;

    /**
     * 展示popWindow布局
     */
    private RelativeLayout mpop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapLayout = inflater.inflate(R.layout.fragment_bdmap, null);
        x.view().inject(mapLayout);
        mapView = (MapView) mapLayout.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        initData();
        return mapLayout;
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_bdmap;
    }

    private void initData() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
//		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
//		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);

        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转3种
        //跟随：每次定位都会回归当定位的位置上,LOCATION_TYPE_MAP_FOLLOW
        //旋转：变成3D显示图,会回归,LOCATION_TYPE_MAP_ROTATE
        //定位:只是定位,不会强行回归,LOCATION_TYPE_LOCATE
//		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        // 设置地图可视缩放大小
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        aMap.getUiSettings().setCompassEnabled(true);// 设置指南针
        aMap.getUiSettings().setScaleControlsEnabled(true);// 设置比例尺
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式,Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置是否返回地址信息（默认返回地址信息）
//            mLocationOption.setNeedAddress(true);
            //设置是否只定位一次,默认为false
//            mLocationOption.setOnceLocation(false);
            //设置是否强制刷新WIFI，默认为强制刷新
//            mLocationOption.setWifiActiveScan(true);
            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(true);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(10000);

            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
        Log.d("Log", "停止定位");
    }

    /**
     * 定位成功后回调函数
     */
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());//纬度和经度
                //定义一个图标
                MarkerOptions otMarkerOptions = new MarkerOptions();
                otMarkerOptions.position(latLng);
                otMarkerOptions.visible(true);//设置可见
//				otMarkerOptions.title(amapLocation.getCity()).snippet(amapLocation.getAddress());//里面的内容自定义
                //标题
                otMarkerOptions.title(amapLocation.getAoiName());
                //子标题
                otMarkerOptions.snippet("这里好火");
                otMarkerOptions.draggable(true);
                //下面这个是标记上面这个经纬度在地图的位置
                otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mark));
                //设置多少帧刷新一次图片资源
                otMarkerOptions.period(60);

                //下面这个是自定义的标记图标使用方法
//                otMarkerOptions.icon(ImageNormal(0));

                aMap.addMarker(otMarkerOptions);
                aMap.setOnMarkerClickListener(this);
                aMap.setOnMapClickListener(this);
//                Log.d("Log", "经度:" + amapLocation.getLongitude());
//                Log.d("Log", "纬度:" + amapLocation.getLatitude());
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                amapLocation.getCountry();//国家信息
//                amapLocation.getProvince();//省信息
//                amapLocation.getCity();//城市信息
//                amapLocation.getDistrict();//城区信息
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
//                amapLocation.getCityCode();//城市编码
//                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息
                YUtils.showToast(getActivity().getApplicationContext(), amapLocation.getAddress());
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                YUtils.showToast(getActivity().getApplicationContext(), errText);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        currentMarker = marker;
        Toast.makeText(getActivity(), "你点击了的是" + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 点击地图其他地方时，隐藏InfoWindow,和popWindow弹出框
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.hideInfoWindow();//隐藏InfoWindow框
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        activate(mListener);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
        aMap = null;
        mapView.onDestroy();
        Log.d("Log", "地图销毁");
    }
}
