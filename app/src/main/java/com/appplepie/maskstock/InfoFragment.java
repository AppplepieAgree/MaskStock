package com.appplepie.maskstock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InfoFragment extends Fragment {
    private static String[] num = {"", "", "", ""};
    private static String[] before = {"", "", "", ""};
    private static Elements element1, element2;
    TextView confirmed, cured, curing, dead, confirmed_String, cured_string, curing_String, dead_string;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_info, null);
        confirmed = v.findViewById(R.id.confirmed);
        cured = v.findViewById(R.id.cured);
        curing = v.findViewById(R.id.curing);
        dead = v.findViewById(R.id.dead);
        confirmed_String = v.findViewById(R.id.confirmed_string);
        cured_string = v.findViewById(R.id.cured_string);
        curing_String = v.findViewById(R.id.curing_string);
        dead_string = v.findViewById(R.id.dead_stirng);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        return v;
    }
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                String url = "http://ncov.mohw.go.kr/";
                Connection.Response execute = Jsoup.connect(url).execute();;
                Document doc = Jsoup.parse(execute.body());


                element1 = doc.getElementsByClass("num");
                element2 = doc.getElementsByClass("before");
                for (int i =0; i < 4; i++){
                    num[i] = element1.get(i).text();
                    before[i] = element2.get(i).text();
                    Log.e(TAG, "doInBackground: "+num[i]);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (element1 != null && element2 !=null) {
                confirmed.setText(num[0]);
                cured.setText(num[1]);
                curing.setText(num[2]);
                dead.setText(num[3]);
                confirmed_String.setText(before[0]);
                cured_string.setText(before[1]);
                curing_String.setText(before[2]);
                dead_string.setText(before[3]);
            }
        }
    }
}
