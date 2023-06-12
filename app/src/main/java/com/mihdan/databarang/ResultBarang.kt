package com.mihdan.databarang

import com.google.gson.annotations.SerializedName

class ResultBarang {
    @field : SerializedName("pesan")
    val pesan : String? = null

    @field : SerializedName("barang")
    val barang : List<DataItem>? = null

    @field : SerializedName("status")
    val status : Int? = null
}