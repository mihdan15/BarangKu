package com.mihdan.databarang

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object NetworkConfig {
    fun getInterceptor() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return okHttpClient
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2/server_api/index.php/ServerApi/")
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(StaffService::class.java)
}

interface StaffService {
    //fungsi create data
    @FormUrlEncoded
    @POST("addBarang")
    fun addStaff(
        @Field("tgl") tgl: String,
        @Field("nama") nama: String,
        @Field("jumlah") jumlah: String,
        @Field("ket") ket: String,
        @Field("kategori") kategori: String
    ): Call<ResultStatus>


    //fungsi get data
    @GET("getDataBarang")
    fun getData(): Call<ResultBarang>

    // fungsi update data
    @FormUrlEncoded
    @POST("updateBarang")
    fun updateStaff(
        @Field("id") id: String,
        @Field("tgl") tgl: String,
        @Field("nama") nama: String,
        @Field("jumlah") jumlah: String,
        @Field("ket") ket: String,
        @Field("kategori") kategori: String
    ): Call<ResultStatus>


    // fungsi delete
    @FormUrlEncoded
    @POST("deleteBarang")
    fun deleteStaff(@Field("id") id: String?): Call<ResultStatus>

}