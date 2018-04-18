package com.example.oleh.cindranesia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anggariansah on 18/04/2018.
 */

public class AdapterUlasan extends ArrayAdapter<ItemUlasan> {
    private Activity activity;
    private List<ItemUlasan> itembaru;
    private ItemUlasan semuaobj;
    private int row;
    Context ctx;

    public AdapterUlasan(Activity act, int resource, List<ItemUlasan> arraylist) {
        super(act, resource, arraylist);
        this.activity = act;
        this.row = resource;
        this.itembaru = arraylist;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((itembaru == null) || ((position + 1) > itembaru.size()))
            return view;

        semuaobj = itembaru.get(position);

        holder.nama = (TextView) view.findViewById(R.id.itemulasan_nama);
        holder.ulasan = (TextView) view.findViewById(R.id.itemulasan_ulasan);


        holder.nama.setText(semuaobj.getNama().toString());
        holder.ulasan.setText(semuaobj.getUlasan().toString());


        return view;
    }

    public class ViewHolder {
        public TextView nama;
        public TextView ulasan;
    }
}
