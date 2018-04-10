package com.example.oleh.cindranesia;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class EMenu_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    NavigationView view;
    ActionBarDrawerToggle ab;
    Toolbar tb;

    ViewPager vp;
    TabLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emenu__drawer);

        tb = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(tb);

        dl = (DrawerLayout)findViewById(R.id.sidebar1);

        ab = new ActionBarDrawerToggle(this,dl,tb,R.string.open,R.string.close);
        dl.addDrawerListener(ab);
        ab.syncState();

        view = (NavigationView)findViewById(R.id.view1);
        view.setNavigationItemSelectedListener(this);

        vp = (ViewPager) findViewById(R.id.vpberuser);
        tl = (TabLayout) findViewById(R.id.tabberuser);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                startActivity(new Intent(this, FFavorit_User.class));
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu3:
                startActivity(new Intent(this, FKeranjang_User.class));
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu4:
                startActivity(new Intent(this, FRiwayat_User.class));
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu5:
                startActivity(new Intent(this, FTentang.class));
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu6:
                //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show()
                    new AlertDialog.Builder(this).setMessage("Apakah Anda yakin?")
                            .setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EMenu_Drawer.this.finish();
                        }
                    })
                            .setNegativeButton("Tidak", null)
                            .show();
                break;
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            if (position == 0) {
                f = new EMenu_Drawer_Oleh();
            }
            if (position == 1) {
                f = new EMenu_Drawer_Populer();
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
                name = "OLEH - OLEH";
            }
            if (position == 1) {
                name = "POPULER";
            }
            return name;
        }
    }
}
