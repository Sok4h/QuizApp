package com.sokah.quizapp.triviaapi

import android.util.Log
import com.sokah.quizapp.model.Question
import com.sokah.quizapp.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getQuestions(
        categories: String,
        limit: Int,
        difficulty: String
    ): MutableList<Question> {

        return withContext(Dispatchers.IO) {
            var response =
                retrofit.create(TriviaApi::class.java).getQuestions(categories, limit, difficulty)

            Log.e("xdd", response.toString() )
             response.body()!!
        }
    }

}