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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class HEditProduk_Owner extends AppCompatActivity {

    EditText jenis_produk,judul_produk,deskripsi_produk,harga_produk;
    Button edit;
    String jenpro,judpro,despro,harpro,id;
    String path;
    String Result;

    Bitmap bitmap;
    private ImageButton mSelectImage;
    private static final int GALLERY_REQUEST = 1;

    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hedit_produk);

        //toolbar
        tb = (Toolbar) findViewById(R.id.edit_produk_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        id = getIntent().getExtras().getString("id");

        jenis_produk = (EditText)findViewById(R.id.edit_produk_jenis);
        judul_produk = (EditText)findViewById(R.id.edit_produk_judul);
        deskripsi_produk = (EditText)findViewById(R.id.edit_produk_deskripsi);
        harga_produk = (EditText)findViewById(R.id.edit_produk_harga);
        mSelectImage = (ImageButton) findViewById(R.id.edit_produk_img);
        edit = (Button)findViewById(R.id.edit_produk_btnedit);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenpro = jenis_produk.getText().toString();
                judpro = judul_produk.getText().toString();
                despro = deskripsi_produk.getText().toString();
                harpro = harga_produk.getText().toString();
                UploadImageServer();
            }
        });

        if(JsonUtils.isNetworkAvailable(HEditProduk_Owner.this)){
            new Tampil().execute("http://10.10.100.4/cindranesia/tampileditproduk.php?id_produk="+id);
        }else{
            Toast.makeText(HEditProduk_Owner.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }

    public class Tampil extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(HEditProduk_Owner.this);
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
                Toast.makeText(HEditProduk_Owner.this, "Tidak Ada data!!!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject JsonUtama = new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JsonObj = jsonArray.getJSONObject(i);

                        jenis_produk.setText(JsonObj.getString("jenis_produk"));
                        judul_produk.setText(JsonObj.getString("judul_produk"));
                        deskripsi_produk.setText(JsonObj.getString("deskripsi_produk"));
                        harga_produk.setText(JsonObj.getString("harga_produk"));
                        path = JsonObj.getString("path_produk");

//                        Picasso
//                                .with(HEditProduk_Owner.this)
//                                .load(path)
//                                .fit()
//                                .into(mSelectImage);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

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

    public void UploadImageServer() {

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class Edit extends AsyncTask<Void, Void, Void> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(HEditProduk_Owner.this,"","Harap Tunggu...",true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                Result = getEdit(id,jenpro,judpro,despro,harpro,ConvertImage);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                resultEdit(Result);
            }
        }

        new Edit().execute();
    }

    public void resultEdit(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(HEditProduk_Owner.this, "Produk berhasil diubah", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HEditProduk_Owner.this, EMenu_Owner.class));
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(HEditProduk_Owner.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getEdit(String id_produk,String jenis_produk, String judul_produk, String deskripsi_produk, String harga_produk, String path){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://10.10.100.4/cindranesia/editproduk.php");
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id_produk",id_produk));
            nvp.add(new BasicNameValuePair("jenis_produk",jenis_produk));
            nvp.add(new BasicNameValuePair("judul_produk",judul_produk));
            nvp.add(new BasicNameValuePair("deskripsi_produk",deskripsi_produk));
            nvp.add(new BasicNameValuePair("harga_produk",harga_produk));
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
