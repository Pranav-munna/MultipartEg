package com.twixttechnologies.multiparteg.Controller

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Pranav Ashok on 11/06/18.
 */
abstract class AbstractRequest<T> (protected var context: Context, private val responcehandler: ProcessResponceInterphase<T>?) : Callback<T> {
    protected lateinit var interphase: MultipartEgInterphase
    private var base_url = "https://a2itservices.com/"

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .client(OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
                        .connectTimeout(300, TimeUnit.SECONDS)
                        .readTimeout(300, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
        interphase=retrofit.create(MultipartEgInterphase::class.java)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.e("response", "  ok")
        processResponse(response.body())
    }

    override fun onFailure(call: Call<T>, t: Throwable?) {
        Log.e("response ", t!!.message + "  poe")
        processResponse(null)
    }

    private fun processResponse(response: T?) {
        if (response != null) {
            responcehandler?.procssResponce(response)
        }
    }
}