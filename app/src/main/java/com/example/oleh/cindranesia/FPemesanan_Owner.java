package com.example.oleh.cindranesia;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FPemesanan_Owner extends AppCompatActivity {

    Toolbar tb;

    GridView listData;
    List<ItemPemesananOwner> arrayItembaru;
    AdapterPemesanan objAdapter;
    private ItemPemesananOwner semuaItemobj;
    ArrayList<String> allnama, alljudul, alljumlah, alltanggal;
    String[] arraynama, arrrayjudul, arrayjumlah, arraytanggal;
    ProgressBar progress;

    String id_toko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpemesanan__owner);

        id_toko = getIntent().getExtras().getString("id");

        //toolbar
        tb = (Toolbar) findViewById(R.id.pemesanan_owner_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        progress = (ProgressBar)findViewById(R.id.pemesanan_owner_progbar);
        listData = (GridView) findViewById(R.id.pemesanan_owner_grid);

        arrayItembaru = new ArrayList<ItemPemesananOwner>();


        allnama = new ArrayList<String>();
        alljudul = new ArrayList<String>();
        alljumlah = new ArrayList<String>();
        alltanggal = new ArrayList<String>();

        arraynama = new String[allnama.size()];
        arrrayjudul = new String[alljudul.size()];
        arrayjumlah = new String[alljumlah.size()];
        arraytanggal = new String[alltanggal.size()];

        if(JsonUtils.isNetworkAvailable(this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampilpemesanan.php?id_toko="+id_toko);
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
                new AlertDialog.Builder(FPemesanan_Owner.this)
                        .setTitle("Pesan")
                        .setMessage("Maaf belum ada pemesanan produk!")
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

                        ItemPemesananOwner buku = new ItemPemesananOwner();
                        buku.setNama(JsonObj.getString("nama"));
                        buku.setTanggal(JsonObj.getString("tanggal"));
                        buku.setJumlah(JsonObj.getString("jumlah"));
                        buku.setJudul(JsonObj.getString("judul"));
                        arrayItembaru.add(buku);

                        //  intent(JsonObj.getString("idpasien"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int j=0;j<arrayItembaru.size();j++){

                    semuaItemobj = arrayItembaru.get(j);

                    allnama.add(semuaItemobj.getNama());
                    arraynama = allnama.toArray(arraynama);

                    alljudul.add(semuaItemobj.getJudul());
                    arrrayjudul = alljudul.toArray(arrrayjudul);

                    alltanggal.add(semuaItemobj.getTanggal());
                    arraytanggal = alltanggal.toArray(arraytanggal);

                    alljumlah.add(semuaItemobj.getJumlah());
                    arrayjumlah = alljumlah.toArray(arrayjumlah);


                }

                setAllAdapter();

            }
        }
    }

    public void setAllAdapter(){
        objAdapter = new AdapterPemesanan(this,R.layout.item_pemesanan,arrayItembaru);
        listData.setAdapter(objAdapter);
    }
}
