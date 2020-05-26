package com.hnam.week6demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object{
        private val TAG = MainActivity::class.java.simpleName
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "7519cb3f829ecd53bd9b7007076dbe23"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MovieService.getInstance().getApi().getTopRateMovie().enqueue(object : Callback<VideoResponse> {
            override fun onFailure(call: Call<VideoResponse>?, t: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<VideoResponse>?,
                response: Response<VideoResponse>?
            ) {
                response?.let {
                    val resp = it.body()
                }
            }

        })

    }
}
