package com.appplepie.maskstock;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetElements {
    private static final String TAG = "getElements";
    private String REQUEST_URL;
    private String result;
    StoreResult getStores(double lat, double lng){ //스토어 가져오기
        REQUEST_URL = String.format("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json?lat=%f&lng=%f&m=3000", lat, lng);
        Log.e(TAG, "getStores: "+ REQUEST_URL );

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(REQUEST_URL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        StoreResult storeResult = gson.fromJson(result, StoreResult.class);
        //Log.e(TAG, "onCreate: "+storeResult.stores.length );
        return storeResult;
    } // 스토어 가져오기 끝
}
