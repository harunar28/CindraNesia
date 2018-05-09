package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DDaftar_Owner_2 extends AppCompatActivity {

    EditText nama_toko,alamat_toko,kota_toko,no_surat,arah;
    TextView id;
    Button daftar2;
    String nt,al,kt,nosurat,ar,id_user;
    String Result;
    String email,path;
    Toolbar tb;

    Bitmap bitmap;
    private ImageButton mSelectImage;
    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ddaftar__owner_2);

        //toolbar
        tb = (Toolbar) findViewById(R.id.daftar_owner2_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        email = getIntent().getExtras().getString("email");

        nama_toko = (EditText)findViewById(R.id.ntoko_owner);
        alamat_toko = (EditText)findViewById(R.id.alamat_toko_owner);
        kota_toko = (EditText)findViewById(R.id.kota_toko_owner);
        no_surat = (EditText)findViewById(R.id.nosurat);
        arah = (EditText)findViewById(R.id.arah_toko_owner);
        daftar2 = (Button)findViewById(R.id.btndaftar_owner2);
        mSelectImage = (ImageButton) findViewById(R.id.gambar_surat);

        if(JsonUtils.isNetworkAvailable(DDaftar_Owner_2.this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampilidowner.php?email="+email);
        }else{
            Toast.makeText(DDaftar_Owner_2.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        daftar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nt = nama_toko.getText().toString();
                al = alamat_toko.getText().toString();
                kt = kota_toko.getText().toString();
                nosurat = no_surat.getText().toString();
                ar = arah.getText().toString();
                UploadImageServer();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                mSelectImage.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public class Tampil extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DDaftar_Owner_2.this);
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
                Toast.makeText(DDaftar_Owner_2.this, "Tidak Ada data!!!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject JsonUtama = new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JsonObj = jsonArray.getJSONObject(i);

                        id_user = JsonObj.getString("id_user");

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

    public void UploadImageServer() {

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class daftarOwner2 extends AsyncTask<Void, Void, Void> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(DDaftar_Owner_2.this,"","Harap Tunggu...",true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                Result = getDaftarOwner2(id_user,nt,al,kt,nosurat,ar,ConvertImage);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                resultDaftarOwner2(Result);
            }
        }

        new daftarOwner2().execute();
    }

    public void resultDaftarOwner2(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(DDaftar_Owner_2.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DDaftar_Owner_2.this, CLogin.class));
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(DDaftar_Owner_2.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getDaftarOwner2(String id_user, String nama_toko, String alamat_toko, String kota_toko, String no_surat, String arah, String path){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("https://cindranesia.000webhostapp.com/tambahtoko.php");
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id_user",id_user));
            nvp.add(new BasicNameValuePair("nama_toko",nama_toko));
            nvp.add(new BasicNameValuePair("alamat_toko",alamat_toko));
            nvp.add(new BasicNameValuePair("kota_toko",kota_toko));
            nvp.add(new BasicNameValuePair("no_surat",no_surat));
            nvp.add(new BasicNameValuePair("arah",arah));
            nvp.add(new BasicNameValuePair("path",path));
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
