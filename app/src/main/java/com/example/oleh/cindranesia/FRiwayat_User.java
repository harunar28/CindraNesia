package com.example.oleh.cindranesia;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FRiwayat_User extends AppCompatActivity {

    GridView listData;
    List<ItemRiwayatUser> arrayItembaru;
    AdapterRiwayat objAdapter;
    private ItemRiwayatUser semuaItemobj;
    ArrayList<String> alljudul_produk, allnama_toko, allalamat_toko, allkota_toko, alljenis_produk, alljumlah, alltanggal, allgambar;
    String[] arrayjudul_produk, arraynama_toko, arrayalamat_toko, arraykota_toko, arrayjenis_produk, arrayjumlah, arraytanggal, arraygambar;
    ProgressBar progress;
    String iduser;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friwayat__user);

        iduser = getIntent().getExtras().getString("id");

        //toolbar
        tb = (Toolbar) findViewById(R.id.riwayat_user_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        progress = (ProgressBar)findViewById(R.id.riwayat_user_progbar);

        listData = (GridView)findViewById(R.id.riwayat_user_grid);
        arrayItembaru = new ArrayList<ItemRiwayatUser>();

        alljudul_produk = new ArrayList<String>();
        allnama_toko = new ArrayList<String>();
        allalamat_toko = new ArrayList<String>();
        allkota_toko = new ArrayList<String>();
        alljenis_produk = new ArrayList<String>();
        alljumlah = new ArrayList<String>();
        alltanggal = new ArrayList<String>();
        allgambar = new ArrayList<String>();

        arrayjudul_produk = new String[alljudul_produk.size()];
        arraynama_toko = new String[allnama_toko.size()];
        arrayalamat_toko = new String[allalamat_toko.size()];
        arraykota_toko = new String[allkota_toko.size()];
        arrayjenis_produk = new String[alljenis_produk.size()];
        arrayjumlah = new String[alljumlah.size()];
        arraytanggal = new String[alltanggal.size()];
        arraygambar = new String[allgambar.size()];

        if(JsonUtils.isNetworkAvailable(this)){
            new Tampil().execute("http://10.10.100.4/cindranesia/tampilriwayat.php?id_user="+iduser);
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
                new AlertDialog.Builder(FRiwayat_User.this)
                        .setTitle("Failed")
                        .setMessage("Harap Periksa Koneksi!")
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

                        ItemRiwayatUser buku = new ItemRiwayatUser();
                        buku.setJudul_produk(JsonObj.getString("judul_produk"));
                        buku.setNama_toko(JsonObj.getString("nama_toko"));
                        buku.setAlamat_toko(JsonObj.getString("alamat_toko"));
                        buku.setKota_toko(JsonObj.getString("kota_toko"));
                        buku.setJenis_produk(JsonObj.getString("jenis_produk"));
                        buku.setJumlah(JsonObj.getString("jumlah"));
                        buku.setTanggal(JsonObj.getString("tanggal"));
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

                }

                setAllAdapter();

            }
        }
    }

    public void setAllAdapter(){
        objAdapter = new AdapterRiwayat(this,R.layout.item_riwayat_pemesanan,arrayItembaru);
        listData.setAdapter(objAdapter);
    }
}
