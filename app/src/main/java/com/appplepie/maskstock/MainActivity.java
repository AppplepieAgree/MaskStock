package com.appplepie.maskstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    private MaskMapFragment maskMapFragment = new MaskMapFragment();

    FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment, maskMapFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction1 = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.bottom_map: {
                    transaction1.replace(R.id.fragment,maskMapFragment).commitAllowingStateLoss();
                    break;
                }
            }

            return true;
        });



    }


}
