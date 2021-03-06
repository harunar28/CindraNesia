package com.example.oleh.cindranesia;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CLogin extends AppCompatActivity {

    TextView daftar;
    Button login;
    String Result,us,ps;
    EditText user,pass;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clogin);

        daftar = (TextView) findViewById(R.id.login_daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CLogin.this,DDaftar.class));
            }
        });

        user = (EditText)findViewById(R.id.login_user);
        pass = (EditText)findViewById(R.id.login_pass);

        login = (Button)findViewById(R.id.login_btnMasuk);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                us = user.getText().toString();
                ps = pass.getText().toString();
                if(JsonUtils.isNetworkAvailable(CLogin.this)){
                    new Tampil().execute("https://cindranesia.000webhostapp.com/login.php?user="+us+"&pass="+ps);
                }else{
                    new AlertDialog.Builder(CLogin.this)
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
            }
        });
    }

    public class Tampil extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(CLogin.this);
            pDialog.setMessage("Harap Tunggu...");
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

            if(null == hasil || hasil.length() == 0){
                new AlertDialog.Builder(CLogin.this)
                        .setTitle("Failed")
                        .setMessage("Please Check Your Connection!")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
            }else{
                try {
                    JSONObject JsonUtama =  new JSONObject(hasil);

                    JSONArray res = JsonUtama.getJSONArray("hasil");
                    JSONObject re = null;
                    re = res.getJSONObject(0);

                    final String result = re.getString("result");

                    if (result.equals("true")) {

                        JSONArray jsonArray = JsonUtama.getJSONArray("data");
                        JSONObject JsonObj = null;

                        JsonObj = jsonArray.getJSONObject(0);

                        final String roll = JsonObj.getString("role");
                        final String id_user = JsonObj.getString("id_user");

                        if (roll.equals("user")){
                            new AlertDialog.Builder(CLogin.this)
                                    .setTitle("Succes")
                                    .setMessage("Login Berhasil!")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            Intent a = new Intent(CLogin.this, EMenu_Drawer.class);
                                            a.putExtra("id",id_user);
                                            startActivity(a);
                                        }
                                    }).show();
                        }else{
                            new AlertDialog.Builder(CLogin.this)
                                    .setTitle("Succes")
                                    .setMessage("Login Berhasil!")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            Intent a = new Intent(CLogin.this, EMenu_Owner.class);
                                            a.putExtra("id",id_user);
                                            startActivity(a);
                                        }
                                    }).show();
                        }

                    } else {
                        new AlertDialog.Builder(CLogin.this)
                                .setTitle("Failed")
                                .setMessage("Username Atau Password Salah!")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
