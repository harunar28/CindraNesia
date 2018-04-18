package com.example.oleh.cindranesia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemProduk extends AppCompatActivity {

    private String id;
    private String idtoko;
    private String judul_produk;
    private String nama_toko;
    private String alamat_toko;
    private String kota_toko;
    private String jenis_produk;

    public String getIdtoko() {
        return idtoko;
    }

    public void setIdtoko(String idtoko) {
        this.idtoko = idtoko;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul_produk() {
        return judul_produk;
    }

    public void setJudul_produk(String judul_produk) {
        this.judul_produk = judul_produk;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public String getAlamat_toko() {
        return alamat_toko;
    }

    public void setAlamat_toko(String alamat_toko) {
        this.alamat_toko = alamat_toko;
    }

    public String getKota_toko() {
        return kota_toko;
    }

    public void setKota_toko(String kota_toko) {
        this.kota_toko = kota_toko;
    }

    public String getJenis_produk() {
        return jenis_produk;
    }

    public void setJenis_produk(String jenis_produk) {
        this.jenis_produk = jenis_produk;
    }
}