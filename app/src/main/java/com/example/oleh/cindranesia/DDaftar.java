package com.example.oleh.cindranesia;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DDaftar extends AppCompatActivity {

    ViewPager vp;
    TabLayout tl;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddaftar);

        //toolbar
        tb = (Toolbar) findViewById(R.id.tooldaftar);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        vp = (ViewPager) findViewById(R.id.vpdaftar);
        tl = (TabLayout) findViewById(R.id.tabDaftar);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            if (position == 0) {
                f = new DDaftar_User();
            }
            if (position == 1) {
                f = new DDaftar_Owner();
            }
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name = null;
            if (position == 0) {
                name = "USER";
            }
            if (position == 1) {
                name = "OWNER";
            }
            return name;
        }
    }
}
