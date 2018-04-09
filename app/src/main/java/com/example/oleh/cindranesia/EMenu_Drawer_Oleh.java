package com.example.oleh.cindranesia;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EMenu_Drawer_Oleh extends Fragment {


    GridView listData;
    List<ItemProduk> arrayItembaru;
    AdapterProduk objAdapter;
    private ItemProduk semuaItemobj;
    ArrayList<String> allid, allIdpas, allkk, allnama, allalamat, allnohp, alljenkel;
    String[] arrayid, arrayidpas, arraykk, arraynama, arrayalamat, arraynohp, arrayjenkel;
    Toolbar toolbar;
    FloatingActionButton tambah;
    ProgressBar progress;
    EditText by;

    String data;

    int textlength = 0;

    SearchView searchKota;

    public EMenu_Drawer_Oleh() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_emenu__drawer__oleh, container, false);

        return v;
    }
}
