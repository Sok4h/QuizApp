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
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANSWERS : String = "correctAnswer"
    private var retrofit :Retrofit = Retrofit.Builder().baseUrl("https://trivia.willfry.co.uk/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getQuestions():ArrayList<Question>{

        var service = retrofit.create<ApiService>(ApiService::class.java)
        var questionsList = ArrayList<Question>()

        service.getTrivia().enqueue(object : Callback<ArrayList<Question>>{
            override fun onResponse(
                call: Call<ArrayList<Question>>?,
                response: Response<ArrayList<Question>>?
            ) {
                questionsList= response?.body() !!

                for (question in questionsList){

                    Log.e("TAG", question.question, )
                }
            }

            override fun onFailure(call: Call<ArrayList<Question>>?, t: Throwable?) {

                t?.printStackTrace()
            }


        })

        return questionsList


    }



}