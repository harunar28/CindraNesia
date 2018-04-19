package com.example.oleh.cindranesia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by anggariansah on 19/04/2018.
 */

public class AdapterPemesanan extends ArrayAdapter<ItemPemesananOwner> {
    private Activity activity;
    private List<ItemPemesananOwner> itembaru;
    private ItemPemesananOwner semuaobj;
    private int row;
    Context ctx;

    public AdapterPemesanan(Activity act, int resource, List<ItemPemesananOwner> arraylist) {
        super(act, resource, arraylist);
        this.activity = act;
        this.row = resource;
        this.itembaru = arraylist;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if((itembaru == null) || ((position + 1) > itembaru.size()))
            return view;

        semuaobj = itembaru.get(position);

        holder.nama = (TextView) view.findViewById(R.id.itempesan_nama);
        holder.judul = (TextView) view.findViewById(R.id.itempesan_judul);
        holder.jumlah = (TextView) view.findViewById(R.id.itempesan_jumlah);
        holder.tanggal = (TextView) view.findViewById(R.id.itemriwayat_tgl);

        holder.nama.setText(semuaobj.getNama().toString());
        holder.judul.setText(semuaobj.getJudul().toString());
        holder.jumlah.setText(semuaobj.getJumlah().toString());
        holder.tanggal.setText(semuaobj.getTanggal().toString());

        return view;
    }

    public class ViewHolder{
        public TextView nama;
        public TextView judul;
        public TextView jumlah;
        public TextView tanggal;
    }
}
