package com.example.oleh.cindranesia;

import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class EMenu_Owner extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout dl;
    NavigationView view;
    ActionBarDrawerToggle ab;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emenu__owner);

        tb = (Toolbar)findViewById(R.id.toolowner);
        setSupportActionBar(tb);

        dl = (DrawerLayout)findViewById(R.id.sidebarowner);

        ab = new ActionBarDrawerToggle(this,dl,tb,R.string.open,R.string.close);
        dl.addDrawerListener(ab);
        ab.syncState();

        view = (NavigationView)findViewById(R.id.viewowner);
        view.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu3:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu4:
                new AlertDialog.Builder(this).setMessage("Apakah Anda yakin?")
                        .setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EMenu_Owner.this.finish();
                    }
                })
                        .setNegativeButton("Tidak", null)
                        .show();
                break;
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}
