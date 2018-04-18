package com.example.oleh.cindranesia;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class DDaftar_User extends Fragment {

    EditText nama_lengkap,username,email,password,no_telp;
    Button daftar;
    String nl,uname,mail,pass,notelp;
    String Result;

    View v;

    public DDaftar_User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_ddaftar__user, container, false);

        nama_lengkap = (EditText)v.findViewById(R.id.fname_user);
        username = (EditText)v.findViewById(R.id.uname_user);
        email = (EditText)v.findViewById(R.id.email_user);
        password = (EditText)v.findViewById(R.id.pass_user);
        no_telp = (EditText)v.findViewById(R.id.nohp_user);
        daftar = (Button)v.findViewById(R.id.btndaftar_user);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nl = nama_lengkap.getText().toString();
                uname = username.getText().toString();
                mail = email.getText().toString();
                pass = password.getText().toString();
                notelp = no_telp.getText().toString();

                new daftar().execute();

            }
        });

        return  v;
    }

    public class daftar extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(),"","Harap Tunggu...",true);

        }

        @Override
        protected Void doInBackground(Void... params) {

            Result = getDaftar(nl,uname,mail,pass,notelp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
            resultDaftar(Result);
        }
    }

    public void resultDaftar(String HasilProses){
        if(HasilProses.trim().equalsIgnoreCase("OK")){
            Toast.makeText(getActivity(), "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), CLogin.class));
        }else if(HasilProses.trim().equalsIgnoreCase("Failed")){
            Toast.makeText(getActivity(), "Data Gagal Or Failed", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("HasilProses", HasilProses);
        }
    }

    public String getDaftar(String nama_lengkap, String username, String email, String password, String no_telp){
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://192.168.56.10/android/cindranesia/tambahuser.php");
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
