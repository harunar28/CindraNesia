package com.example.oleh.cindranesia;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EMenu_Drawer_Oleh extends Fragment {


    GridView listData;
    List<ItemProduk> arrayItembaru;
    AdapterProduk objAdapter;
    private ItemProduk semuaItemobj;
    ArrayList<String> allidtoko, allid, alljudul_produk, allnama_toko, allalamat_toko, allkota_toko, alljenis_produk;
    String[] arrayidtoko, arrayid, arrayjudul_produk, arraynama_toko, arrayalamat_toko, arraykota_toko, arrayjenis_produk;
    ProgressBar progress;
    EditText by;

    String data;

    int textlength = 0;

    SearchView search;

    View v;

    public EMenu_Drawer_Oleh() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_emenu__drawer__oleh, container, false);

        progress = (ProgressBar)v.findViewById(R.id.menu_drawer_oleh_progbar);

        data = getArguments().getString("id");

        listData = (GridView)v.findViewById(R.id.menu_drawer_oleh_grid);
        arrayItembaru = new ArrayList<ItemProduk>();

        allid = new ArrayList<String>();
        allidtoko = new ArrayList<String>();
        alljudul_produk = new ArrayList<String>();
        allnama_toko = new ArrayList<String>();
        allalamat_toko = new ArrayList<String>();
        allkota_toko = new ArrayList<String>();
        alljenis_produk = new ArrayList<String>();

        arrayid = new String[allid.size()];
        arrayidtoko = new String[allidtoko.size()];
        arrayjudul_produk = new String[alljudul_produk.size()];
        arraynama_toko = new String[allnama_toko.size()];
        arrayalamat_toko = new String[allalamat_toko.size()];
        arraykota_toko = new String[allkota_toko.size()];
        arrayjenis_produk = new String[alljenis_produk.size()];

        if(JsonUtils.isNetworkAvailable(getActivity())){
            new Tampil().execute("http://192.168.56.10/android/cindranesia/tampiloleh.php");
        }else{
            new AlertDialog.Builder(getActivity())
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

                String ide = semuaItemobj.getId();
                String idtoko = semuaItemobj.getIdtoko();

                Intent a = new Intent(getActivity() ,FDetail_User.class);
                a.putExtra("idtoko",idtoko);
                a.putExtra("idproduk",ide);
                a.putExtra("iduser",data);
                startActivity(a);
            }
        });

        by = (EditText)v.findViewById(R.id.cariber);

        by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Cari Berdasarkan")
                        .setItems(R.array.filter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                by.setText(getResources().getStringArray(R.array.filter)[which]);
                            }
                        })
                        .show();
            }
        });


        searchdata();

        return v;
    }

    public void searchdata() {
        search = (SearchView) v.findViewById(R.id.cariprod);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String text) {
                // TODO Auto-generated method stub
                textlength = text.length();
                arrayItembaru.clear();

                String sc = by.getText().toString();

                if (sc.equals("Jenis Produk")) {

                    for (int i = 0; i < arrayjenis_produk.length; i++) {
                        if (textlength <= arrayjenis_produk[i].length()) {
                            if (text.toString().equalsIgnoreCase((String) arrayjenis_produk[i].subSequence(0, textlength))) {
                                ItemProduk data = new ItemProduk();

                                data.setId(arrayid[i]);
                                data.setIdtoko(arrayidtoko[i]);
                                data.setJudul_produk(arrayjudul_produk[i]);
                                data.setNama_toko(arraynama_toko[i]);
                                data.setAlamat_toko(arrayalamat_toko[i]);
                                data.setKota_toko(arraykota_toko[i]);
                                data.setJenis_produk(arrayjenis_produk[i]);

                                arrayItembaru.add(data);
                            }
                        }
                    }

                    setAllAdapter();

                } else if (sc.equals("Kota")) {
                    for (int i = 0; i < arraykota_toko.length; i++) {
                        if (textlength <= arraykota_toko[i].length()) {
                            if (text.toString().equalsIgnoreCase((String) arraykota_toko[i].subSequence(0, textlength))) {
                                ItemProduk data = new ItemProduk();

                                data.setId(arrayid[i]);
                                data.setIdtoko(arrayidtoko[i]);
                                data.setJudul_produk(arrayjudul_produk[i]);
                                data.setNama_toko(arraynama_toko[i]);
                                data.setAlamat_toko(arrayalamat_toko[i]);
                                data.setKota_toko(arraykota_toko[i]);
                                data.setJenis_produk(arrayjenis_produk[i]);

                                arrayItembaru.add(data);
                            }
                        }
                    }

                    setAllAdapter();

                } else if (sc.equals("Nama Produk")) {
                    for (int i = 0; i < arrayjudul_produk.length; i++) {
                        if (textlength <= arrayjudul_produk[i].length()) {
                            if (text.toString().equalsIgnoreCase((String) arrayjudul_produk[i].subSequence(0, textlength))) {
                                ItemProduk data = new ItemProduk();

                                data.setId(arrayid[i]);
                                data.setIdtoko(arrayidtoko[i]);
                                data.setJudul_produk(arrayjudul_produk[i]);
                                data.setNama_toko(arraynama_toko[i]);
                                data.setAlamat_toko(arrayalamat_toko[i]);
                                data.setKota_toko(arraykota_toko[i]);
                                data.setJenis_produk(arrayjenis_produk[i]);

                                arrayItembaru.add(data);
                            }
                        }
                    }

                    setAllAdapter();

                } else if (sc.equals("Nama Toko")) {
                    for (int i = 0; i < arraynama_toko.length; i++) {
                        if (textlength <= arraynama_toko[i].length()) {
                            if (text.toString().equalsIgnoreCase((String) arraynama_toko[i].subSequence(0, textlength))) {
                                ItemProduk data = new ItemProduk();

                                data.setId(arrayid[i]);
                                data.setIdtoko(arrayidtoko[i]);
                                data.setJudul_produk(arrayjudul_produk[i]);
                                data.setNama_toko(arraynama_toko[i]);
                                data.setAlamat_toko(arrayalamat_toko[i]);
                                data.setKota_toko(arraykota_toko[i]);
                                data.setJenis_produk(arrayjenis_produk[i]);

                                arrayItembaru.add(data);
                            }
                        }
                    }

                    setAllAdapter();

                }

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }
        });

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
                new AlertDialog.Builder(getActivity())
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

                        ItemProduk buku = new ItemProduk();

                        buku.setId(JsonObj.getString("id_produk"));
                        buku.setIdtoko(JsonObj.getString("id_toko"));
                        buku.setJudul_produk(JsonObj.getString("judul_produk"));
                        buku.setNama_toko(JsonObj.getString("nama_toko"));
                        buku.setAlamat_toko(JsonObj.getString("alamat_toko"));
                        buku.setKota_toko(JsonObj.getString("kota_toko"));
                        buku.setJenis_produk(JsonObj.getString("jenis_produk"));
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

                }

                setAllAdapter();

            }
        }
    }

    public void setAllAdapter(){
        objAdapter = new AdapterProduk(getActivity(),R.layout.item_produk,arrayItembaru);
        listData.setAdapter(objAdapter);
    }
}
