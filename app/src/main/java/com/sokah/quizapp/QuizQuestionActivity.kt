package com.sokah.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sokah.quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityQuizQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val questionList = Constants.getQuestions()
        var currentPosition = 2
        var currentQuestion:Question? = questionList[currentPosition - 1]
        Log.e("TAG", "Question " + questionList.size.toString())

        binding.tvQuestion.text=currentQuestion?.question
        binding.optionTwo.text=currentQuestion?.option2
        binding.optionThree.text=currentQuestion?.option3
        binding.optionFour.text=currentQuestion?.option4
        binding.questionImage.setImageResource(currentQuestion!!.image)
        binding.progressBar.progress=currentPosition
        binding.tvProgressBar.text="$currentPosition / ${questionList.size}"
    }
}