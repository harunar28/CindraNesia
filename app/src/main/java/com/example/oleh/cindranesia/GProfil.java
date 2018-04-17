package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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
import android.widget.ImageView;
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

public class GProfil extends AppCompatActivity {

    EditText nama,tempat,tgl,jenkel,alamat,email,nohp;
    String nm,tmpt,tg,jk,al,em,nh;
    Button update;
    String Result;
    String id_user,path;
    ImageButton image;
    Toolbar tb;

    Bitmap bitmap;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gprofil);

        //toolbar
        tb = (Toolbar) findViewById(R.id.profil_tool);
        setSupportActionBar(tb);

        //back-toolbar
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        id_user = getIntent().getExtras().getString("id");

        nama = (EditText)findViewById(R.id.profil_nama);
        tempat = (EditText)findViewById(R.id.profil_tempat_lahir);
        tgl = (EditText)findViewById(R.id.profil_tgl_lahir_);
        jenkel = (EditText)findViewById(R.id.profil_jenkel);
        alamat = (EditText)findViewById(R.id.profil_alamat);
        email = (EditText)findViewById(R.id.profil_email);
        nohp = (EditText)findViewById(R.id.profil_telpon);
        image = (ImageButton) findViewById(R.id.profil_foto);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });



        update = (Button) findViewById(R.id.profil_btn_simpan);

        if(JsonUtils.isNetworkAvailable(GProfil.this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampilprofil.php?id_user="+id_user);
        }else{
            Toast.makeText(GProfil.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm = nama.getText().toString();
                tmpt = tempat.getText().toString();
                tg = tgl.getText().toString();
                jk = jenkel.getText().toString();
                al = alamat.getText().toString();
                em = email.getText().toString();
                nh = nohp.getText().toString();

                UploadImageServer();

            }
        });
    }


    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                image.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
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

            pDialog = new ProgressDialog(GProfil.this);
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
                Toast.makeText(GProfil.this, "Tidak Ada data!!!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    JSONObject JsonUtama = new JSONObject(hasil);
                    JSONArray jsonArray = JsonUtama.getJSONArray("data");
                    JSONObject JsonObj = null;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JsonObj = jsonArray.getJSONObject(i);

                        nama.setText(JsonObj.getString("nama_lengkap"));
                        tempat.setText(JsonObj.getString("tempat_lahir"));
                        tgl.setText(JsonObj.getString("tgl_lahir"));
                        jenkel.setText(JsonObj.getString("jenis_kelamin"));
                        alamat.setText(JsonObj.getString("alamat"));
                        email.setText(JsonObj.getString("email"));
                        nohp.setText(JsonObj.getString("no_telp"));
                        path = JsonObj.getString("path_profil");

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

        class update extends AsyncTask<Void, Void, Void> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(GProfil.this, "", "Harap Tunggu...", true);

            }

            @Override
            protected Void doInBackground(Void... params) {

                Result = getUpdate(id_user, nm, tmpt, tg, jk, al, em, nh, ConvertImage);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                resultUpdate(Result);
            }
        }

        new update().execute();
    }

    public void resultUpdate(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(GProfil.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(GProfil.this, CLogin.class));
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(GProfil.this, "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getUpdate(String id,String nama, String tempat, String tgl, String jenkel, String alamat, String email, String nohp, String path){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("https://cindranesia.000webhostapp.com/updateprofil.php");
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("id_user",id));
            nvp.add(new BasicNameValuePair("nama",nama));
            nvp.add(new BasicNameValuePair("tempat",tempat));
            nvp.add(new BasicNameValuePair("tgl",tgl));
            nvp.add(new BasicNameValuePair("jenkel",jenkel));
            nvp.add(new BasicNameValuePair("alamat",alamat));
            nvp.add(new BasicNameValuePair("email",email));
            nvp.add(new BasicNameValuePair("nohp",nohp));
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
