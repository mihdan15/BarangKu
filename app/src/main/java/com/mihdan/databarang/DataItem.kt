package com.mihdan.databarang

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataItem : Serializable {
    @field : SerializedName("id_barang")
    val idBarang: String? = null

    @field : SerializedName("tanggal_barang")
    val tglBarang: String? = null

    @field : SerializedName("nama_barang")
    val namaBarang: String? = null

    @field : SerializedName("jumlah_barang")
    val jmlBarang: String? = null

    @field : SerializedName("ket_barang")
    val ketBarang: String? = null

    @field : SerializedName("kategori_barang")
    val kategori: String? = null
}
