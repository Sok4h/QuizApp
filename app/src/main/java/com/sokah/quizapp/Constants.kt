package com.sokah.quizapp

import android.util.Log
import com.sokah.quizapp.triviaapi.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {

    const val USER_NAME : String = "username"
    const val DIFFICULTY : String = "difficulty"
    const val CATEGORIES : String = "categories"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANSWERS : String = "correctAnswer"
    const val BASEURL = "https://trivia.willfry.co.uk/api/"


}