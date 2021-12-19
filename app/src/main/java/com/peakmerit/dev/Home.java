package com.peakmerit.dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {

    //BottomNavigationView bottomNavigationView;
    ChipNavigationBar bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        openFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);


    }

    public void openFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }

    ChipNavigationBar.OnItemSelectedListener onItemSelectedListener = new ChipNavigationBar.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int item) {
            switch (item) {
                case R.id.home:
                    openFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    openFragment(new ProfileFragment());
                    break;
                case R.id.notif:
                    openFragment(new NotifFragment());
                    break;
            }

        }
    };

}