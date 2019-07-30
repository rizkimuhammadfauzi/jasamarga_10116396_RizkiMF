package com.example.jasamarga.data;

public class modelData {
    private int id;
    private String kategori, jenis, kilometer, meter, meterDetail, deskripsi, foto, tgl;

    public modelData(int id , String kategori, String jenis , String kilometer , String meter , String meterDetail , String deskripsi , String foto , String tgl) {
        this.id = id;
        this.jenis = jenis;
        this.kilometer = kilometer;
        this.meter = meter;
        this.meterDetail = meterDetail;
        this.deskripsi = deskripsi;
        this.foto = foto;
        this.tgl = tgl;
        this.kategori = kategori;
    }

    public int getId(){
        return id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String Jenis) {
        this.jenis = Jenis;
    }

    public String getKilometer() {
        return kilometer;
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getMeterDetail() {
        return meterDetail;
    }

    public void setMeterDetail(String meterDetail) {
        this.meterDetail = meterDetail;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

}
