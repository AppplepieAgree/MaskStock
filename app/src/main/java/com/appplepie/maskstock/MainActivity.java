package com.appplepie.maskstock;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static  int LOCATION_REQUEST_CODE = 1001;
    private FusedLocationSource locationSource;
    String REQUEST_URL;
    String result;
    double lat;
    double lng;
    Button loaction_refresh;
    StoreResult storeResult;
    private static final String TAG = "MainActivity";

    public StoreResult getStores (double lat, double lng){ //스토어 가져오기
        REQUEST_URL = String.format("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json?lat=%f&lng=%f&m=3000", lat, lng);
        Log.e(TAG, "getStores: "+ REQUEST_URL );

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(REQUEST_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

//                    httpURLConnection.setReadTimeout(3000);
//                    httpURLConnection.setConnectTimeout(3000);
//                    httpURLConnection.setDoInput(true);
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.setUseCaches(false);
//                    httpURLConnection.connect();

                    int responseStatusCode = httpURLConnection.getResponseCode();
                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK){
                        inputStream = httpURLConnection.getInputStream();
                    }else {
                        inputStream = httpURLConnection.getErrorStream();
                        Log.e(TAG, "run: Error" );
                    }
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    result = sb.toString();
                    Log.e(TAG, "run: "+result);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        StoreResult storeResult = gson.fromJson(result, StoreResult.class);
        Log.e(TAG, "onCreate: "+storeResult.stores.length );
        return storeResult;
    } // 스토어 가져오기 끝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaction_refresh = findViewById(R.id.location_refresh);



        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("3gapo17ttk"));






        FragmentManager fm = getSupportFragmentManager();

        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


        //위치 반환하기 좋게하는 그런거
        locationSource =
                new FusedLocationSource(this, LOCATION_REQUEST_CODE);

        loaction_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeResult = getStores(lat, lng);
                Toast.makeText(getApplicationContext(), storeResult.count+"", Toast.LENGTH_SHORT).show();

            }
        });




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

        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        //location.getLatitude = 위도 location.getLongitude() = 경도
        naverMap.addOnLocationChangeListener(location ->{
                lat = location.getLatitude(); lng = location.getLongitude();});
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lng));
        naverMap.moveCamera(cameraUpdate);




    }


}
