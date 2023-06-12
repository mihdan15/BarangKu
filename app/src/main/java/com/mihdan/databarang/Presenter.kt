package com.mihdan.databarang

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class Presenter(val crudView: MainActivity) {
    //fungsi get data
    fun getData() {
        NetworkConfig.getService().getData()
            .enqueue(object :retrofit2.Callback<ResultBarang> {
                override fun onFailure(call: Call<ResultBarang>, t: Throwable) {
                    crudView.onFailedGet(t.localizedMessage)
                    Log.e("Error", "Error Data", t)
                }

                override fun onResponse(call: Call<ResultBarang>, response: Response<ResultBarang>) {
                    if (response.isSuccessful) {
                        val status = response.body()?.status
                        if (status == 200){
                            val data = response.body()?.barang
                            crudView.onSuccessGet(data)
                        } else {
                            crudView.onFailedGet("Error $status")
                        }
                    }
                }
            })
    }

    fun hapusData(id: String?) {
        NetworkConfig.getService()
            .deleteStaff(id)
            .enqueue(object:retrofit2.Callback<ResultStatus>{
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    crudView.onErrorDelete(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200){
                        crudView.onSuccessDelete(response.body()?.pesan?: "")
                    } else {
                        crudView.onErrorDelete(response.body()?.pesan?: "")
                    }
                }
            })
    }
}