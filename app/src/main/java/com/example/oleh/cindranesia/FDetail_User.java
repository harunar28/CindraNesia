package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class FDetail_User extends AppCompatActivity {

    TextView judul,desk,nama,alamat,kota,arah,harga;
    ImageView image;
    EditText ulasan,jmh;
    Button kirim,simpan;

    String iduser,idproduk,idtoko;
    String ulas;

    String Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdetail__user);

        judul = (TextView)findViewById(R.id.detail_user_judul);
        desk = (TextView)findViewById(R.id.detail_user_deskripsi);
        nama = (TextView)findViewById(R.id.detail_user_nama_toko);
        alamat = (TextView)findViewById(R.id.detail_user_alamat_toko);
        kota = (TextView)findViewById(R.id.detail_user_kota);
        arah = (TextView)findViewById(R.id.detail_user_arah);
        harga = (TextView)findViewById(R.id.detail_user_harga);

        ulasan = (EditText) findViewById(R.id.detail_user_ulasan);

        kirim = (Button) findViewById(R.id.detail_user_btnKirim);
        simpan = (Button) findViewById(R.id.detail_user_btnSimpan);

        image = (ImageView)findViewById(R.id.detail_user_img);

        iduser = getIntent().getExtras().getString("iduser");
        idproduk = getIntent().getExtras().getString("idproduk");
        idtoko = getIntent().getExtras().getString("idtoko");

        if(JsonUtils.isNetworkAvailable(FDetail_User.this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampiloleh.php?id="+idproduk);
        }else{
            new AlertDialog.Builder(FDetail_User.this)
                    .setTitle("Failed")
                    .setMessage("Please Check Connection!")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();
        }


        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ulas = ulasan.getText().toString();

                new kirim().execute();
            }
        });


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                jumlah();
            }
        });

    }


    private void jumlah(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Masukan Jumlah Pesanan");

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.item_jumlah_pesan,null);

        jmh = (EditText) v.findViewById(R.id.jumlah);

        dialog.setView(v);

        dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String jumlah = jmh.getText().toString();

                save(jumlah);

            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    public class Tampil extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(FDetail_User.this);
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
                Toast.makeText(FDetail_User.this, "Tidak Ada data!!!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject JsonUtama = new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JsonObj = jsonArray.getJSONObject(i);

                        judul.setText(JsonObj.getString("judul"));
                        desk.setText(JsonObj.getString("deskripsi"));
                        nama.setText(JsonObj.getString("nama"));
                        alamat.setText(JsonObj.getString("alamat"));
                        kota.setText(JsonObj.getString("kota"));
                        arah.setText(JsonObj.getString("arah"));
                        harga.setText(JsonObj.getString("harga"));

                        String path = JsonObj.getString("path");

//                        Picasso
//                                .with(GProfil.this)
//                                .load(path)
//                                .fit()
//                                .into(image);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }



    public void save(final String jmh) {

        class simpan extends AsyncTask<Void, Void, Void> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(FDetail_User.this, "", "Harap Tunggu...", true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                Result = getSimpan(iduser, idproduk, idtoko, jmh);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                resultSimpan(Result);
            }
        }

        new simpan().execute();
    }

    public void resultSimpan(String HasilProses) {
        if (HasilProses.trim().equalsIgnoreCase("OK")) {
            Toast.makeText(FDetail_User.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(FDetail_User.this, CLogin.class));
        } else if (HasilProses.trim().equalsIgnoreCase("Failed")) {
            Toast.makeText(FDetail_User.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getSimpan(String iduser, String idproduk, String idtoko, String jumlah) {
        String result = "";

        HttpClient client = new DefaultHttpClient();
<<<<<<< HEAD
        HttpPost request = new HttpPost("https://cindranesia.000webhostapp.com/tambahkeranjang.php");
=======
        HttpPost request = new HttpPost("http://192.168.56.10/android/cindranesia/tambahkeranjang.php");
>>>>>>> 9791e7a806210fe03c983f4c17a09012565d99f7
        try {
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id_user", iduser));
            nvp.add(new BasicNameValuePair("id_produk", idproduk));
            nvp.add(new BasicNameValuePair("id_toko", idtoko));
            nvp.add(new BasicNameValuePair("jumlah", jumlah));
            request.setEntity(new UrlEncodedFormEntity(nvp, HTTP.UTF_8));
            HttpResponse response = client.execute(request);
            result = request(response);

        } catch (Exception ex) {
            result = "Unable To connect";
        }

        return result;
    }




    //KIRIM ULASAN

    public class kirim extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(FDetail_User.this,"","Harap Tunggu...",true);

        }

        @Override
        protected Void doInBackground(Void... params) {

            Result = getKirim(iduser,idproduk,ulas);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            resultKirim(Result);
        }
    }

    public void resultKirim(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(FDetail_User.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(FDetail_User.this, CLogin.class));
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(FDetail_User.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getKirim(String iduser, String idproduk, String ulasan){
        String result = "";

        HttpClient client = new DefaultHttpClient();
<<<<<<< HEAD
        HttpPost request = new HttpPost("https://cindranesia.000webhostapp.com/tambahulasan.php");
=======
        HttpPost request = new HttpPost("http://192.168.56.10/android/cindranesia/tambahulasan.php");
>>>>>>> 9791e7a806210fe03c983f4c17a09012565d99f7
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id_user",iduser));
            nvp.add(new BasicNameValuePair("id_produk",idproduk));
            nvp.add(new BasicNameValuePair("ulasan",ulasan));
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
}
