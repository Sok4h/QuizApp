package com.sokah.quizapp.triviaapi

import com.sokah.quizapp.Question
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("questions?limit=10")
    fun getTrivia() : Call<ArrayList<Question>>
}