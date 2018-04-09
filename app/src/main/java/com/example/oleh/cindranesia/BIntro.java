package com.example.oleh.cindranesia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class BIntro extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("Langkah 1",
                "Apa itu",
                R.drawable.logo_tulisan,
                Color.parseColor("#51e2b7")));

        addSlide(AppIntroFragment.newInstance("Langkah 2",
                "Apa itu",
                R.drawable.logo_tulisan,
                Color.parseColor("#8c50e3")));

        addSlide(AppIntroFragment.newInstance("Langkah 3",
                "Apa Itu",
                R.drawable.logo_tulisan,
                Color.parseColor("#4fd7ff")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#333639"));
        setSeparatorColor(Color.parseColor("#2196F3"));

    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this,CLogin.class));
        finish();
    }

    @Override
    public void onSkipPressed() {
        Toast.makeText(this, "Berhasil Di Skip",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {
        Toast.makeText(this, "Slide Change",Toast.LENGTH_SHORT).show();
    }
}
