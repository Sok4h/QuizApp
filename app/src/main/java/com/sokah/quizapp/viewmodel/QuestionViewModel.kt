package com.sokah.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sokah.quizapp.model.Question
import com.sokah.quizapp.triviaapi.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {

    var service = ApiService()
    var _questions = MutableLiveData<MutableList<Question>>()

    val questions: LiveData<MutableList<Question>>
        get() = _questions

    fun getQuestions(
        categories: String,
        limit: Int,
        difficulty: String
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            var response = service.getQuestions(categories, limit, difficulty)
            _questions.postValue(response)
            questions.value

        }

    }
}