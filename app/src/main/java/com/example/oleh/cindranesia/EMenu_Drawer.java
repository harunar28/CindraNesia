package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EMenu_Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    NavigationView view;
    ActionBarDrawerToggle ab;
    Toolbar tb;

    ViewPager vp;
    TabLayout tl;

    ImageView profile;

    String id_user;
    TextView nama,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emenu__drawer);

        id_user = getIntent().getExtras().getString("id");

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


        View header = view.getHeaderView(0);
<<<<<<< HEAD
        profile = (ImageView) header.findViewById(R.id.avatar);
=======
        ImageView profile = (ImageView) header.findViewById(R.id.avatar_user);
>>>>>>> 9791e7a806210fe03c983f4c17a09012565d99f7
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(EMenu_Drawer.this, GProfil.class);
                a.putExtra("id", id_user);
                startActivity(a);
            }
        });

        nama = (TextView)header.findViewById(R.id.nama_user);
        email = (TextView)header.findViewById(R.id.email_user);

        if(JsonUtils.isNetworkAvailable(EMenu_Drawer.this)){
            new Tampil().execute("http://192.168.56.10/android/cindranesia/tampildrawer.php?id_user="+id_user);
        }else{
            Toast.makeText(EMenu_Drawer.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }
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


    public class Tampil extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(EMenu_Drawer.this);
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
                Toast.makeText(EMenu_Drawer.this, "Tidak Ada data!!!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject JsonUtama = new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JsonObj = jsonArray.getJSONObject(i);

                        nama.setText(JsonObj.getString("nama_lengkap"));
                        email.setText(JsonObj.getString("email"));


                        Picasso
                                .with(EMenu_Drawer.this)
                                .load(JsonObj.getString("path_profil"))
                                .fit()
                                .into(profile);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

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

                Bundle data = new Bundle();
                data.putString("id",id_user);
                f.setArguments(data);
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
