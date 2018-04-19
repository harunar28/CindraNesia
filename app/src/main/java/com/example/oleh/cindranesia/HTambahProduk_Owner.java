package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HTambahProduk_Owner extends AppCompatActivity {

    EditText jenis_produk,judul_produk,deskripsi_produk,harga_produk;
    Button simpan;
    String jenpro,judpro,despro,harpro,id_toko,id_user;
    String Result;

    Bitmap bitmap;
    private ImageButton mSelectImage;
    private static final int GALLERY_REQUEST = 1;

    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htambah_produk__owner);

        //toolbar
        tb = (Toolbar) findViewById(R.id.tambah_produk_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        id_toko = getIntent().getExtras().getString("id_toko");
        id_user = getIntent().getExtras().getString("id_user");

        jenis_produk = (EditText)findViewById(R.id.tambah_produk_jenis);
        judul_produk = (EditText)findViewById(R.id.tambah_produk_judul);
        deskripsi_produk = (EditText)findViewById(R.id.tambah_produk_deskripsi);
        harga_produk = (EditText)findViewById(R.id.tambah_produk_harga);
        mSelectImage = (ImageButton) findViewById(R.id.tambah_produk_img);
        simpan = (Button)findViewById(R.id.tambah_produk_btnsimpan);

        jenis_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HTambahProduk_Owner.this)
                        .setTitle("Jenis Produk")
                        .setItems(R.array.jenis, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                jenis_produk.setText(getResources().getStringArray(R.array.jenis)[which]);
                            }
                        })
                        .show();
            }
        });

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenpro = jenis_produk.getText().toString();
                judpro = judul_produk.getText().toString();
                despro = deskripsi_produk.getText().toString();
                harpro = harga_produk.getText().toString();
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

    public void UploadImageServer() {

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class Simpan extends AsyncTask<Void, Void, Void> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(HTambahProduk_Owner.this,"","Harap Tunggu...",true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                Result = getSimpan(jenpro,judpro,despro,harpro,ConvertImage,id_toko);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                resultSimpan(Result);
            }
        }

        new Simpan().execute();
    }

    public void resultSimpan(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(HTambahProduk_Owner.this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(HTambahProduk_Owner.this, EMenu_Owner.class);
            a.putExtra("id",id_user);
            startActivity(a);
            finish();
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(HTambahProduk_Owner.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getSimpan(String jenis_produk, String judul_produk, String deskripsi_produk, String harga_produk, String path, String id_toko){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://10.10.100.4/cindranesia/tambahproduk.php");
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id_toko",id_toko));
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
