package com.hnam.week6demo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by nampham on 5/26/20.
 */
interface MovieApi {
    @GET("movie/top_rated")
    fun getTopRateMovie() : Call<VideoResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(): Call<VideoResponse>
}