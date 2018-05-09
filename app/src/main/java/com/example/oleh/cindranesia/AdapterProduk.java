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
 * Created by Harun Ar on 08/04/2018.
 */

public class AdapterProduk extends ArrayAdapter<ItemProduk> {
    private Activity activity;
    private List<ItemProduk> itembaru;
    private ItemProduk semuaobj;
    private int row;
    Context ctx;

    public AdapterProduk(Activity act, int resource, List<ItemProduk> arraylist) {
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

        holder.judul_produk = (TextView) view.findViewById(R.id.judul_produk);
        holder.nama_toko = (TextView) view.findViewById(R.id.nama_toko);
        holder.alamat_toko = (TextView) view.findViewById(R.id.alamat_toko);
        holder.kota_toko = (TextView) view.findViewById(R.id.kota_toko);
        holder.jenis_produk = (TextView) view.findViewById(R.id.jenis_produk);
        holder.gmb = (ImageView) view.findViewById(R.id.gambar);

        if(semuaobj.getGambar().toString().equals("")){
            holder.gmb.setImageResource(R.drawable.default_produk_putih);
        }else{
            Picasso
                    .with(activity)
                    .load(semuaobj.getGambar().toString())
                    .fit()
                    .into(holder.gmb);
        }

        holder.judul_produk.setText(semuaobj.getJudul_produk().toString());
        holder.nama_toko.setText(semuaobj.getNama_toko().toString());
        holder.alamat_toko.setText(semuaobj.getAlamat_toko().toString());
        holder.kota_toko.setText(semuaobj.getKota_toko().toString());
        holder.jenis_produk.setText(semuaobj.getJenis_produk().toString());

        return view;
    }

    public class ViewHolder{
        public TextView judul_produk;
        public TextView nama_toko;
        public TextView alamat_toko;
        public TextView kota_toko;
        public TextView jenis_produk;
        public ImageView gmb;
    }
}
