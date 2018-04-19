package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EMenu_Owner extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout dl;
    NavigationView view;
    ActionBarDrawerToggle ab;
    Toolbar tb;
    FloatingActionButton tambahProduk;

    String id_user,Result,id,id_toko;
    TextView nama,email,text1;

    GridView listData;
    List<ItemProduk> arrayItembaru;
    AdapterProduk objAdapter;
    private ItemProduk semuaItemobj;
    ArrayList<String> allidtoko, allid, alljudul_produk, allnama_toko, allalamat_toko, allkota_toko, alljenis_produk, allgambar;
    String[] arrayidtoko, arrayid, arrayjudul_produk, arraynama_toko, arrayalamat_toko, arraykota_toko, arrayjenis_produk, arraygambar;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emenu__owner);

        progress = (ProgressBar)findViewById(R.id.menu_owner_progbar);

        id_user = getIntent().getExtras().getString("id");

        text1 = (TextView)findViewById(R.id.text1);

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
            new Tampil().execute("http://192.168.56.10/android/cindranesia/tampildrawer.php?id_user="+id_user);
        }else{
            Toast.makeText(EMenu_Owner.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }

        if(JsonUtils.isNetworkAvailable(EMenu_Owner.this)){
            new Tampil2().execute("http://192.168.56.10/android/cindranesia/tampilidtoko.php?id_user="+id_user);
        }else{
            Toast.makeText(EMenu_Owner.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }

        if(JsonUtils.isNetworkAvailable(EMenu_Owner.this)){
            new TampilProduk().execute("http://192.168.56.10/android/cindranesia/tampiloleh_owner.php?id_toko="+id_toko);
        }else{
            new AlertDialog.Builder(EMenu_Owner.this)
                    .setTitle("Failed")
                    .setMessage("Harap Periksa Koneksi!")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();
        }

        tambahProduk = (FloatingActionButton) findViewById(R.id.menu_owner_tambah);
        tambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(EMenu_Owner.this, HTambahProduk_Owner.class);
                a.putExtra("id_toko",id_toko);
                startActivity(a);
            }
        });

        listData = (GridView)findViewById(R.id.menu_owner_grid);
        arrayItembaru = new ArrayList<ItemProduk>();

        allid = new ArrayList<String>();
        allidtoko = new ArrayList<String>();
        alljudul_produk = new ArrayList<String>();
        allnama_toko = new ArrayList<String>();
        allalamat_toko = new ArrayList<String>();
        allkota_toko = new ArrayList<String>();
        alljenis_produk = new ArrayList<String>();
        allgambar = new ArrayList<String>();

        arrayid = new String[allid.size()];
        arrayidtoko = new String[allidtoko.size()];
        arrayjudul_produk = new String[alljudul_produk.size()];
        arraynama_toko = new String[allnama_toko.size()];
        arrayalamat_toko = new String[allalamat_toko.size()];
        arraykota_toko = new String[allkota_toko.size()];
        arrayjenis_produk = new String[alljenis_produk.size()];
        arraygambar = new String[allgambar.size()];

        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                semuaItemobj = arrayItembaru.get(position);

                String ide = semuaItemobj.getJudul_produk();
                String id2 = semuaItemobj.getId();
                Diklik(ide,id2);
            }
        });
    }

    public void Diklik(final String judul_produk, final String id){
        new AlertDialog.Builder(EMenu_Owner.this)
                .setTitle("Kelola")
                .setMessage(judul_produk)
                .setCancelable(true)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(EMenu_Owner.this)
                                .setTitle("Pesan")
                                .setMessage("Apakah Anda yakin ingin menghapus "+judul_produk)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        hapus(id);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent a = new Intent(EMenu_Owner.this, HEditProduk_Owner.class);
                        a.putExtra("id",id);
                        startActivity(a);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void hapus(final String id){
        class delete extends AsyncTask<Void, Void, Void> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(EMenu_Owner.this,"","Harap Tunggu...",true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                Result = getDelete(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                resultDelete(Result);
            }
        }
        new delete().execute();
    }

    public void resultDelete(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(EMenu_Owner.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(EMenu_Owner.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getDelete(String id){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://192.168.56.10/android/cindranesia/hapusproduk.php");
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id",id));
            request.setEntity(new UrlEncodedFormEntity(nvp, HTTP.UTF_8));
            HttpResponse response = client.execute(request);
            result = request(response);

        }catch (Exception ex){
            result = "Unable To connect";
        }

        return result;
    }

    public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();

        }catch (Exception ex){
            result = "Error";
        }

        return result;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Intent b = new Intent(EMenu_Owner.this, FPemesanan_Owner.class);
                b.putExtra("id", id_toko);
                startActivity(b);
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

    public class Tampil2 extends AsyncTask<String, Void, String> {
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

                        id_toko = JsonObj.getString("id_toko");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public class TampilProduk extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String hasil) {
            super.onPostExecute(hasil);



            if (null != progress) {
                progress.setVisibility(View.GONE);
            }

            if(null == hasil || hasil.length() == 0){
                new AlertDialog.Builder(EMenu_Owner.this)
                        .setTitle("Failed")
                        .setMessage("Tidak Ada Data!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
                progress.setVisibility(View.GONE);
            }else{
                try {
                    JSONObject JsonUtama =  new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for(int i = 0;i < jsonArray.length();i++){

                        JsonObj = jsonArray.getJSONObject(i);

                        ItemProduk buku = new ItemProduk();

                        buku.setId(JsonObj.getString("id_produk"));
                        buku.setIdtoko(JsonObj.getString("id_toko"));
                        buku.setJudul_produk(JsonObj.getString("judul_produk"));
                        buku.setNama_toko(JsonObj.getString("nama_toko"));
                        buku.setAlamat_toko(JsonObj.getString("alamat_toko"));
                        buku.setKota_toko(JsonObj.getString("kota_toko"));
                        buku.setJenis_produk(JsonObj.getString("jenis_produk"));
                        buku.setGambar(JsonObj.getString("path"));
                        arrayItembaru.add(buku);

                        //  intent(JsonObj.getString("idpasien"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int j=0;j<arrayItembaru.size();j++){

                    semuaItemobj = arrayItembaru.get(j);

                    allid.add(semuaItemobj.getId());
                    arrayid = allid.toArray(arrayid);

                    allidtoko.add(semuaItemobj.getIdtoko());
                    arrayidtoko = allidtoko.toArray(arrayidtoko);

                    alljudul_produk.add(semuaItemobj.getJudul_produk());
                    arrayjudul_produk = alljudul_produk.toArray(arrayjudul_produk);

                    allnama_toko.add(semuaItemobj.getNama_toko());
                    arraynama_toko = allnama_toko.toArray(arraynama_toko);

                    allalamat_toko.add(semuaItemobj.getAlamat_toko());
                    arrayalamat_toko = allalamat_toko.toArray(arrayalamat_toko);

                    allkota_toko.add(semuaItemobj.getKota_toko());
                    arraykota_toko = allkota_toko.toArray(arraykota_toko);

                    alljenis_produk.add(semuaItemobj.getJenis_produk());
                    arrayjenis_produk = alljenis_produk.toArray(arrayjenis_produk);

                    allgambar.add(semuaItemobj.getGambar());
                    arraygambar = allgambar.toArray(arraygambar);


                }

                setAllAdapter();

            }
        }
    }

    public void setAllAdapter(){
        objAdapter = new AdapterProduk(EMenu_Owner.this,R.layout.item_produk,arrayItembaru);
        listData.setAdapter(objAdapter);
    }
}
