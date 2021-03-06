package com.appplepie.maskstock;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GetElements extends AsyncTask<Void, Void, Void> {
    public GetElements(double lat, double lng, NaverMap naverMap, String type,boolean check) {
        this.lat = lat;
        this.lng = lng;
        this.naverMap = naverMap;
        this.type = type;
        this.check = check;
    }
    static ArrayList<Marker> grayMarkers = new ArrayList<>();
    static ArrayList<Marker> markers = new ArrayList<>();


    String type;
    double lat, lng;
    private NaverMap naverMap;
    private StoreResult storeResult;
    private boolean check;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        final String TAG = "getElements";
        String REQUEST_URL;
        final String[] result = new String[1];//스토어 가져오기
        REQUEST_URL = String.format("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v2/storesByGeo/json?lat=%f&lng=%f&m=3000", lat, lng);
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
            setMarker(type);
            Log.e(TAG, "onPostExecute: 1" );
        }
    }
    private void setMarker(String type){
        for (int i = 0; i < storeResult.count; i++) {
            Marker marker = new Marker();
            if (type.equals("") || storeResult.stores[i].type.equals(type)){
                marker.setPosition(new LatLng(storeResult.stores[i].lat, storeResult.stores[i].lng));
                marker.setCaptionText(storeResult.stores[i].name);
                marker.setCaptionTextSize(12);
                marker.setWidth(95);
                marker.setHeight(135);
                marker.setIcon(MarkerIcons.BLACK);
//                marker.setOnClickListener(new Overlay.OnClickListener() {
//                    @Override
//                    public boolean onClick(@NonNull Overlay overlay) {
//                        InfoWindow infoWindow = new InfoWindow(InfoWindow.DEFAULT_ADAPTER);
//                        infoWindow.open(marker);
//                        return true;
//                    }
//                });
                //markerCustom - 색깔 바꾸기
                String remain_stat = storeResult.stores[i].remain_stat;
                if (remain_stat == null){
                    marker.setIconTintColor(Color.parseColor("#AFC9CF"));
                    grayMarkers.add(marker);
                    if (!check){
                        continue;
                    }
                    continue;
                }
                switch (remain_stat) {
                    case "empty":
                        marker.setIconTintColor(Color.parseColor("#AFC9CF"));
                        grayMarkers.add(marker);
                        if (!check) {
                            continue;
                        }

                        break;
                    case "few":
                        marker.setIconTintColor(Color.parseColor("#F63C41"));

                        break;
                    case "some":
                        marker.setIconTintColor(Color.parseColor("#FFDD3C"));

                        break;
                    case "plenty":
                        marker.setIconTintColor(Color.parseColor("#89E894"));

                        break;
                    default:
                        marker.setIconTintColor(Color.parseColor("#AFC9CF"));
                        grayMarkers.add(marker);
                        if (!check) {
                            continue;
                        }
                        break;
                }
                marker.setMap(naverMap);


            }

        }
    }

}

