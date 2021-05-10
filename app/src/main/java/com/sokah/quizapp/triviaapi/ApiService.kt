package com.sokah.quizapp.triviaapi

import com.sokah.quizapp.Question
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("https://trivia.willfry.co.uk/api/questions?categories=history,science&limit=10")
    fun getTrivia() : Call<ArrayList<Question>>

}