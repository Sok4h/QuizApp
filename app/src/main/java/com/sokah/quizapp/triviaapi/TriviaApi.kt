package com.sokah.quizapp.triviaapi

import com.sokah.quizapp.model.Question
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApi {

    @GET("questions")

    suspend fun getQuestions(
        @Query("categories") categories:String,
        @Query("limit") limit :Int,
        @Query("difficulty") difficulty:String
    ) : Response<MutableList<Question>>

}