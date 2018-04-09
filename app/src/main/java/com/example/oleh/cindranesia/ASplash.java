package com.example.oleh.cindranesia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class ASplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asplash);

        ImageView lg = (ImageView) findViewById(R.id.logo);

        AlphaAnimation animasi = new AlphaAnimation(0.0f, 1.0f);
        animasi.setDuration(3000);
        animasi.setStartOffset(1000);
        lg.startAnimation(animasi);

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences b = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean c = b.getBoolean("first",true);
                if (c){
                    startActivity(new Intent(ASplash.this,BIntro.class));
                    SharedPreferences.Editor d = b.edit();
                    d.putBoolean("first",false);
                    d.apply();
                }else{
                    Thread timer = new Thread(){
                        public void run(){
                            try{
                                sleep(2000);
                            }
                            catch (InterruptedException e){
                                Log.e("xception","Exception"+e);
                            }
                            finally {
                                startActivity(new Intent(ASplash.this, CLogin.class));
                            }
                            finish();
                        }
                    };
                    timer.start();
                }
            }
        });
        a.start();
    }
}
