package com.nadji.myapp.data.network.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nadji.myapp.common.utils.Constant
import com.nadji.myapp.data.network.model.PromoResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface NetworkService {

    @GET("promos")
    suspend fun promo(): PromoResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): NetworkService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
//                .addInterceptor(chuckerInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}