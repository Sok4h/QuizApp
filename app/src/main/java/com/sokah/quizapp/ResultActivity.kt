package com.sokah.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sokah.quizapp.Constants.USER_NAME
import com.sokah.quizapp.databinding.ActivityQuizQuestionBinding
import com.sokah.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding : ActivityResultBinding
    private lateinit var name :String
    private lateinit var correctAnswer :String
    private lateinit var total_questions :String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        name =  intent.getStringExtra(USER_NAME).toString()
        correctAnswer = intent.getStringExtra(Constants.CORRECT_ANSWERS).toString()
        total_questions = intent.getStringExtra(Constants.TOTAL_QUESTIONS).toString()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvUser.text = name
        binding.tvResult.text = "Your score is $correctAnswer out of $total_questions"

        binding.btnFinish.setOnClickListener {

            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}