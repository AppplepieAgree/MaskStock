package com.appplepie.maskstock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    String url = "https://blog.naver.com/kfdazzang/221839489769";
    SpannableString site = new SpannableString(url);
    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내사항");
        builder.setCancelable(false);
        builder.setMessage("전국의 약사님, 현장에서 열심히 일해주시는 모든 종사자 분들께 감사의 인사를 표합니다.\n\n " +
                "다음 안내사항을 꼭 숙지하신다음 앱을 이용해주시기 바랍니다." + "\n\n" +
                "1.본 앱에서 제공하는 마스크 재고에 관한 정보는 최소 5분이상 지연된 정보입니다\n"+
                "2.공적 마스크 관련 안내는 식약처 블로그 및 홈페이지를 참고해주세요\n"+
                "3.앱에서 표시되는 재고량은 실제 재고량과 많은 차이가 있을 수 있습니다.\n"+
                "재고수량은 참고용으로만 이용 부탁드립니다.");
        builder.setPositiveButton("내용을 확인했습니다", (dialog, which) -> {showUrl();});
        AlertDialog dialog = builder.create();
        dialog.show();
        ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

    }
    void showUrl(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("식약처 공적마스크 참고 링크");
        builder.setCancelable(false);
        builder.setMessage(site);
        builder.setPositiveButton("내용을 확인했습니다", (dialog, which) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
        ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

    }

    private MaskMapFragment maskMapFragment = new MaskMapFragment();
    private InfoFragment infoFragment = new InfoFragment();
    private MoreInfoFragment moreInfoFragment = new MoreInfoFragment();

    FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Linkify.addLinks(site, Linkify.ALL);
        show();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        FragmentTransaction transaction1 = fm.beginTransaction();
        transaction1.replace(R.id.fragment,maskMapFragment).commitAllowingStateLoss();


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.bottom_map: {
                    transaction.replace(R.id.fragment,maskMapFragment).commitAllowingStateLoss();
                    break;
                }
                case R.id.bottom_info: {
                    transaction.replace(R.id.fragment,infoFragment).commitAllowingStateLoss();
                    break;
                }
                case R.id.more_info:{
                    transaction.replace(R.id.fragment,moreInfoFragment).commitAllowingStateLoss();
                    break;
                }

            }


            return true;
        });



    }


}