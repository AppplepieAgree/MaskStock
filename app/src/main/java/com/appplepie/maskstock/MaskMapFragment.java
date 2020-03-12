package com.appplepie.maskstock;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;


public class MaskMapFragment extends Fragment implements OnMapReadyCallback {

    private final static  int LOCATION_REQUEST_CODE = 1001;
    private static final String TAG = "MaskMapFragment";
    private static View view;
    private Activity a;
    private FusedLocationSource locationSource;
    private double lat;
    private double lng;
    private NaverMap naverMap;
    private String type = "";
    private String id = "3gapo17ttk";
    private Context context;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            a = (Activity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null){
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_mask_map, container, false);
        } catch (InflateException e){

        }

        FloatingActionButton loactionRefresh = view.findViewById(R.id.location_refresh);
        NaverMapSdk.getInstance(a).setClient(
                new NaverMapSdk.NaverCloudPlatformClient(id));


        FragmentManager fm = getFragmentManager();

        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);


        mapFragment = MapFragment.newInstance();
        fm.beginTransaction().add(R.id.map, mapFragment).commit();


        mapFragment.getMapAsync(this);


        //위치 반환하기 좋게하는 그런거
        locationSource =
                new FusedLocationSource(this, LOCATION_REQUEST_CODE);

        loactionRefresh.setOnClickListener(v1 -> {
            Toast.makeText(getContext(), "최대 5초가량 소요될 수 있습니다", Toast.LENGTH_SHORT).show();
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lng));
            naverMap.moveCamera(cameraUpdate);
            GetElements getElements = new GetElements(lat, lng, naverMap, type);
            getElements.execute();
            naverMap.setLocationSource(locationSource);
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        });
        return view;
    }
    //이거는 그냥 있으면 좋은거 (네이버에서만 지원한다고 함)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.e(TAG, "onMapReady: onMapReady");
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //location.getLatitude = 위도 location.getLongitude() = 경도
        naverMap.addOnLocationChangeListener(location -> {
            lat = location.getLatitude();
            lng = location.getLongitude();
        });
    }
}
