package com.appplepie.maskstock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InfoFragment extends Fragment {
    private static String[] num = {"", "", "", ""};
    private static String[] before = {"", "", "", ""};
    private static Elements element1, element2;
    TextView confirmed, cured, curing, dead, confirmed_String, cured_string, curing_String, dead_string;
    PieChart chart;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
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
        chart = v.findViewById(R.id.piechart);
        chart.getDescription().setEnabled(false);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");

        chart.setCenterTextTypeface(tf);
        chart.setCenterText(generateCenterText());
        chart.setCenterTextSize(10f);
        chart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        chart.setHoleRadius(45f);
        chart.setTransparentCircleRadius(50f);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        chart.setData(generatePieData());

        return v;
    }
    private PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf"));

        return d;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
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
