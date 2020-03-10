package com.appplepie.maskstock;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GetElements extends AsyncTask<Void, Void, Void> {
    public GetElements(double lat, double lng, NaverMap naverMap) {
        this.lat = lat;
        this.lng = lng;
        this.naverMap = naverMap;
    }

    double lat, lng;
    NaverMap naverMap;
    StoreResult storeResult;


//    private static final String TAG = "getElements";
//    private String REQUEST_URL;
//    private String result;
//    StoreResult getStores(double lat, double lng){ //스토어 가져오기
//        REQUEST_URL = String.format("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json?lat=%f&lng=%f&m=3000", lat, lng);
//        Log.e(TAG, "getStores: "+ REQUEST_URL );
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(REQUEST_URL);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                    int responseStatusCode = httpURLConnection.getResponseCode();
//                    InputStream inputStream;
//                    if (responseStatusCode == HttpURLConnection.HTTP_OK){
//                        inputStream = httpURLConnection.getInputStream();
//                    }else {
//                        inputStream = httpURLConnection.getErrorStream();
//                        Log.e(TAG, "run: Error" );
//                    }
//                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//                    BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);
//
//                    StringBuilder sb = new StringBuilder();
//                    String line;
//
//                    while ((line = bufferedReader.readLine()) != null){
//                        sb.append(line);
//                    }
//                    bufferedReader.close();
//                    httpURLConnection.disconnect();
//                    result = sb.toString();
//                    Log.e(TAG, "run: "+result);
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Gson gson = new Gson();
//        StoreResult storeResult = gson.fromJson(result, StoreResult.class);
    //Log.e(TAG, "onCreate: "+storeResult.stores.length );// 스토어 가져오기 끝

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        final String TAG = "getElements";
        String REQUEST_URL;
        final String[] result = new String[1];//스토어 가져오기
        REQUEST_URL = String.format("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json?lat=%f&lng=%f&m=3000", lat, lng);
        Log.e(TAG, "getStores: " + REQUEST_URL);
        try {
            URL url = new URL(REQUEST_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            int responseStatusCode = httpURLConnection.getResponseCode();
            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
                Log.e(TAG, "run: Error");
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            httpURLConnection.disconnect();
            result[0] = sb.toString();
            Log.e(TAG, "run: " + result[0]);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        storeResult = gson.fromJson(result[0], StoreResult.class);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (storeResult != null) {
            for (int i = 0; i < storeResult.count; i++) {
                Marker marker = new Marker();
                marker.setPosition(new LatLng(storeResult.stores[i].lat, storeResult.stores[i].lng));

                //iconCustom 원하는 아이콘
//                    switch (storeResult.stores[i].type){
//                        case "01":
//
//                            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_pharmacy));
//                            break;
//                        case "02":
//
//                            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_post_office));
//                            break;
//
//                        case "03":
//
//                            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_nhbank));
//                            break;
//
//                        default:
//
//                            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_pharmacy));
//                            break;
//
//                    }
                //iconCustom - 이름 출력
                marker.setCaptionText(storeResult.stores[i].name);
                marker.setCaptionTextSize(16);
                marker.setHideCollidedMarkers(true);
                marker.setWidth(110);
                marker.setHeight(155);

                marker.setIcon(MarkerIcons.BLACK);
                //markerCustom - 색깔 바꾸기
                String remain_stat = storeResult.stores[i].remain_stat;
                Log.e(TAG, "onCreateView: " + remain_stat);
                if (remain_stat == null) {
                    marker.setIconTintColor(Color.GRAY);
                } else if (remain_stat.equals("empty")) {
                    marker.setIconTintColor(Color.GRAY);

                } else if (remain_stat.equals("few")) {

                    marker.setIconTintColor(Color.RED);

                } else if (remain_stat.equals("some")) {

                    marker.setIconTintColor(Color.YELLOW);

                } else if (remain_stat.equals("plenty")) {

                    marker.setIconTintColor(Color.GREEN);

                } else {
                    marker.setIconTintColor(Color.GRAY);
                }


                marker.setMap(naverMap);
            }
        }
    }
}

