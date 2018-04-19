package com.example.oleh.cindranesia;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DDaftar_Owner extends Fragment {

    EditText nama_lengkap,username,email,password,no_telp;
    Button daftar;
    String nl,uname,mail,pass,notelp;
    String Result;

    public DDaftar_Owner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ddaftar__owner, container, false);

        nama_lengkap = (EditText)v.findViewById(R.id.fname_owner);
        username = (EditText)v.findViewById(R.id.uname_owner);
        email = (EditText)v.findViewById(R.id.email_owner);
        password = (EditText)v.findViewById(R.id.pass_owner);
        no_telp = (EditText)v.findViewById(R.id.nohp_owner);
        daftar = (Button)v.findViewById(R.id.btndaftar_owner);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nl = nama_lengkap.getText().toString();
                uname = username.getText().toString();
                mail = email.getText().toString();
                pass = password.getText().toString();
                notelp = no_telp.getText().toString();

                new daftarOwner().execute();

            }
        });
        return v;
    }

    public class daftarOwner extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(),"","Harap Tunggu...",true);

        }

        @Override
        protected Void doInBackground(Void... params) {

            Result = getDaftarOwner(nl,uname,mail,pass,notelp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            resultDaftarOwner(Result);
        }
    }

    public void resultDaftarOwner(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Pesan")
                    .setMessage("Pendaftaran berhasil, klik OK untuk tahap selanjutnya.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent a = new Intent(getActivity(), DDaftar_Owner_2.class);
                            a.putExtra("email",mail);
                            startActivity(a);
                        }
                    }).show();
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(getActivity(), "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getDaftarOwner(String nama_lengkap, String username, String email, String password, String no_telp){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://10.10.100.4/cindranesia/tambahowner.php");
        try{
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(6);
            nvp.add(new BasicNameValuePair("nama_lengkap",nama_lengkap));
            nvp.add(new BasicNameValuePair("username",username));
            nvp.add(new BasicNameValuePair("email",email));
            nvp.add(new BasicNameValuePair("password",password));
            nvp.add(new BasicNameValuePair("no_telp",no_telp));
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
