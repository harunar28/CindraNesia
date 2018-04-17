package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GProfil extends AppCompatActivity {

    EditText nama,tempat,tgl,jenkel,alamat,email,nohp;
    String id_user,path;
    ImageButton image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gprofil);

        id_user = getIntent().getExtras().getString("id");

        nama = (EditText)findViewById(R.id.profil_nama);
        tempat = (EditText)findViewById(R.id.profil_tempat_lahir);
        tgl = (EditText)findViewById(R.id.profil_tgl_lahir_);
        jenkel = (EditText)findViewById(R.id.profil_jenkel);
        alamat = (EditText)findViewById(R.id.profil_alamat);
        email = (EditText)findViewById(R.id.profil_email);
        nohp = (EditText)findViewById(R.id.profil_telpon);
        image = (ImageButton) findViewById(R.id.profil_foto);

        if(JsonUtils.isNetworkAvailable(GProfil.this)){
            new Tampil().execute("https://cindranesia.000webhostapp.com/tampilprofil.php?id_user="+id_user);
        }else{
            Toast.makeText(GProfil.this,"No Network Connection!!!",Toast.LENGTH_SHORT).show();
        }
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
}
