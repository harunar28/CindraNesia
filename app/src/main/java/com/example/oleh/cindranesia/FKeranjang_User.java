package com.example.oleh.cindranesia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FKeranjang_User extends AppCompatActivity {

    GridView listData;
    List<ItemKeranjang> arrayItembaru;
    AdapterKeranjang objAdapter;
    private ItemKeranjang semuaItemobj;
    ArrayList<String> alljudul_produk, allnama_toko, allalamat_toko, allkota_toko, alljenis_produk, alljumlah, allgambar;
    String[] arrayjudul_produk, arraynama_toko, arrayalamat_toko, arraykota_toko, arrayjenis_produk, arrayjumlah, arraygambar;
    ProgressBar progress;
    Toolbar tb;
    String id;
    Button pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fkeranjang__user);

        id = getIntent().getExtras().getString("id");

        //toolbar
        tb = (Toolbar) findViewById(R.id.keranjang_user_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        progress = (ProgressBar)findViewById(R.id.keranjang_user_progbar);

        listData = (GridView)findViewById(R.id.keranjang_user_grid);
        arrayItembaru = new ArrayList<ItemKeranjang>();

        alljudul_produk = new ArrayList<String>();
        allnama_toko = new ArrayList<String>();
        allalamat_toko = new ArrayList<String>();
        allkota_toko = new ArrayList<String>();
        alljenis_produk = new ArrayList<String>();
        alljumlah = new ArrayList<String>();
        allgambar = new ArrayList<String>();

        arrayjudul_produk = new String[alljudul_produk.size()];
        arraynama_toko = new String[allnama_toko.size()];
        arrayalamat_toko = new String[allalamat_toko.size()];
        arraykota_toko = new String[allkota_toko.size()];
        arrayjenis_produk = new String[alljenis_produk.size()];
        arrayjumlah = new String[alljumlah.size()];
        arraygambar = new String[allgambar.size()];

        if(JsonUtils.isNetworkAvailable(this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampilkeranjang.php?id_user="+id);
        }else{
            new AlertDialog.Builder(this)
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

        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                semuaItemobj = arrayItembaru.get(position);

//                String ide = semuaItemobj.getIdpasien();
//                Intent a = new Intent(DataPasien.this ,DetailPasien.class);
//                a.putExtra("idpasien",ide);
//                startActivity(a);
            }
        });

        pesan = (Button)findViewById(R.id.keranjang_user_btnPesan);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(FKeranjang_User.this)
                        .setTitle("Succes")
                        .setMessage("Pemesanan sedang di proses, harap tunggu!")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                startActivity(new Intent(FKeranjang_User.this, EMenu_Drawer.class));
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    public class Tampil extends AsyncTask<String, Void, String> {


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
                new AlertDialog.Builder(FKeranjang_User.this)
                        .setTitle("Pesan")
                        .setMessage("Maaf Anda belum memilih produk!")
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

                        ItemKeranjang buku = new ItemKeranjang();
                        buku.setJudul_produk(JsonObj.getString("judul_produk"));
                        buku.setNama_toko(JsonObj.getString("nama_toko"));
                        buku.setAlamat_toko(JsonObj.getString("alamat_toko"));
                        buku.setKota_toko(JsonObj.getString("kota_toko"));
                        buku.setJenis_produk(JsonObj.getString("jenis_produk"));
                        buku.setJumlah(JsonObj.getString("jumlah"));
                        buku.setGambar(JsonObj.getString("path"));
                        arrayItembaru.add(buku);

                        //  intent(JsonObj.getString("idpasien"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int j=0;j<arrayItembaru.size();j++){

                    semuaItemobj = arrayItembaru.get(j);

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

                    alljumlah.add(semuaItemobj.getJumlah());
                    arrayjumlah = alljumlah.toArray(arrayjumlah);

                    allgambar.add(semuaItemobj.getGambar());
                    arraygambar = allgambar.toArray(arraygambar);

                }

                setAllAdapter();

            }
        }
    }

    public void setAllAdapter(){
        objAdapter = new AdapterKeranjang(this,R.layout.item_keranjang,arrayItembaru);
        listData.setAdapter(objAdapter);
    }
}
