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
        addSlide(AppIntroFragment.newInstance("CindraNesia",
                "Aplikasi yang memiliki konten - konten tentang " +
                        "cendramata Indonesia. Aplikasi ini menjadi " +
                        "media promosi bagi mikro usaha yang sedang " +
                        "menjalankan usaha berupa oleh - oleh khas Indonesia.",
                R.drawable.logo3,
                Color.parseColor("#cc0004")));

        addSlide(AppIntroFragment.newInstance("Cari CenderaMata",
                "Cari cenderamata Indonesia hanya dengan satu " +
                        "ketukan dan dapat melihat semua jenis cenderamata indonesia.",
                R.drawable.icon2,
                Color.parseColor("#cc0004")));

        addSlide(AppIntroFragment.newInstance("Pesan Cenderamata",
                "Hanya dengan satu ketukan Anda dapat memesan cenderamata Indonesia.",
                R.drawable.pesan,
                Color.parseColor("#cc0004")));

        showStatusBar(false);
//        setBarColor(Color.parseColor("#333639"));
//        setSeparatorColor(Color.parseColor("#2196F3"));

    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this,CLogin.class));
        finish();
    }

    @Override
    public void onSkipPressed() {
//        Toast.makeText(this, "Berhasil Di Skip",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,CLogin.class));
        finish();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {
//        Toast.makeText(this, "Slide Change",Toast.LENGTH_SHORT).show();
    }
}
