package com.sokah.quizapp.triviaapi

import com.sokah.quizapp.Question
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("questions?categories=movies,music,general_knowledge&limit=20")
    fun getTrivia() : Call<ArrayList<Question>>
}