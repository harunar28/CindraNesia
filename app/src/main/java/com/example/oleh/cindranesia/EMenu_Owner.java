package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EMenu_Owner extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout dl;
    NavigationView view;
    ActionBarDrawerToggle ab;
    Toolbar tb;

    String id_user;
    TextView nama,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emenu__owner);

        id_user = getIntent().getExtras().getString("id");

        tb = (Toolbar)findViewById(R.id.toolowner);
        setSupportActionBar(tb);

        dl = (DrawerLayout)findViewById(R.id.sidebarowner);

        ab = new ActionBarDrawerToggle(this,dl,tb,R.string.open,R.string.close);
        dl.addDrawerListener(ab);
        ab.syncState();

        view = (NavigationView)findViewById(R.id.viewowner);
        view.setNavigationItemSelectedListener(this);

        View header = view.getHeaderView(0);
        ImageView profile = (ImageView) header.findViewById(R.id.avatar_owner);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(EMenu_Owner.this, GProfil.class);
                a.putExtra("id", id_user);
                startActivity(a);
            }
        });

        nama = (TextView)header.findViewById(R.id.nama_owner);
        email = (TextView)header.findViewById(R.id.email_owner);

        if(JsonUtils.isNetworkAvailable(EMenu_Owner.this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampildrawer.php?id_user="+id_user);
        }else{
            Toast.makeText(EMenu_Owner.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                startActivity(new Intent(this, FPemesanan_Owner.class));
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu3:
                startActivity(new Intent(this, FTentang.class));
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
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

    public class Tampil extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(EMenu_Owner.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String hasil) {
            super.onPostExecute(hasil);

            if (null != pDialog && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (null == hasil || hasil.length() == 0) {
                Toast.makeText(EMenu_Owner.this, "Tidak Ada data!!!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject JsonUtama = new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JsonObj = jsonArray.getJSONObject(i);

                        nama.setText(JsonObj.getString("nama_lengkap"));
                        email.setText(JsonObj.getString("email"));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
