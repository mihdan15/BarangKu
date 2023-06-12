package com.mihdan.databarang

import retrofit2.Call
import retrofit2.Response

class Presenter2 (val crudView: UpdateAddActivity) {


    //Add data
    fun addData(tgl: String, nama: String, jumlah :String, ket:String, kategori: String) {
        NetworkConfig.getService()
            .addStaff(tgl, nama, jumlah, ket, kategori)
            .enqueue(object :retrofit2.Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorAdd(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessAdd(response.body()?.pesan?:"")
                    } else {
                        crudView.onErrorAdd(response.body()?.pesan?:"")
                    }
                }

            })
    }

    //Update data
    fun updateData(id: String, tgl: String, nama:String, jumlah:String, ket:String, kategori: String) {
        NetworkConfig.getService()
            .updateStaff(id, tgl, nama, jumlah, ket, kategori)
            .enqueue(object :retrofit2.Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorUpdate(t.localizedMessage)
                }

                override fun onResponse(call: Call<ResultStatus>, response: Response<ResultStatus>) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        crudView.onSuccessUpdate(response.body()?.pesan?:"")
                    } else {
                        crudView.onErrorUpdate(response.body()?.pesan?:"")
                    }
                }

            })
    }






}