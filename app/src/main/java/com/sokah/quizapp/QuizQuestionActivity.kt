package com.sokah.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.sokah.quizapp.databinding.ActivityQuizQuestionBinding
import com.sokah.quizapp.triviaapi.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding
    private var mCurrentPosition: Int = 1
    private var correctAnswer: Int = 0
    private lateinit var mQuestionList: ArrayList<Question>
    private var mQuestionListAnswers: ArrayList<String> = ArrayList()
    private  var mSelectedOption: String =""
    private var checkOption = false
    private var answered = false;
    private var username: String? = null
    private var retrofit: Retrofit = Retrofit.Builder().baseUrl("https://trivia.willfry.co.uk/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getQuestions()
        username = intent.getStringExtra(Constants.USER_NAME).toString()
        binding.btnSubmit.setOnClickListener(this)
        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)


    }

    private fun setQuestion() {
        checkOption = false;
        answered=false
        defaultOptionTextView()
        var currentQuestion: Question? = mQuestionList?.get(mCurrentPosition - 1)
        binding.tvQuestion.text = currentQuestion?.question
        binding.optionOne.text = currentQuestion?.correctAnswer
        binding.optionTwo.text = currentQuestion?.incorrectAnswers!![0]
        binding.optionThree.text = currentQuestion?.incorrectAnswers!![1]
        binding.optionFour.text = currentQuestion?.incorrectAnswers!![2]
        //binding.questionImage.setImageResource(currentQuestion!!.image)
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgressBar.text = "$mCurrentPosition / ${mQuestionList?.size}"

    }

    //resetea los estilos a todos los botones
    private fun defaultOptionTextView() {

        var options: ArrayList<TextView> = ArrayList()

        options.add(0, binding.optionOne)
        options.add(1, binding.optionTwo)
        options.add(2, binding.optionThree)
        options.add(3, binding.optionFour)

        // verifica que no haya seleccionado una opciona antes
        if(!answered) {
            for (option in options) {

                option.setTextColor(Color.parseColor("#7A8089"))
                option.background =
                    ContextCompat.getDrawable(this, R.drawable.default_option_border)
                option.typeface = Typeface.DEFAULT
            }
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.optionOne -> selectedOptionView(binding.optionOne, binding.optionOne.text.toString())
            R.id.optionTwo -> selectedOptionView(binding.optionTwo, binding.optionTwo.text.toString())
            R.id.optionThree -> selectedOptionView(binding.optionThree, binding.optionThree.text.toString())
            R.id.optionFour -> selectedOptionView(binding.optionFour, binding.optionFour.text.toString())
            R.id.btnSubmit -> submit()
        }

    }

    //cambia el estilo a la opcion que es seleccionada
    private fun selectedOptionView(textView: TextView, selectedOption: String) {

            if(!answered) {

                defaultOptionTextView()

                if (mCurrentPosition == mQuestionList!!.size) {

                    binding.btnSubmit.text = "Finish"
                } else {
                    binding.btnSubmit.text = "Submit"

                }
                mSelectedOption = selectedOption

                textView.setTextColor(Color.parseColor("#363A43"))
                textView.background =
                    ContextCompat.getDrawable(this, R.drawable.selected_option_border)
                textView.setTypeface(textView.typeface, Typeface.BOLD)
                checkOption = true
            }


    }


    private fun answer(answer: String, background: Int) {
        answered =true
        when (answer) {

            binding.optionOne.text -> binding.optionOne.background = ContextCompat.getDrawable(this, background)
            binding.optionTwo.text -> binding.optionTwo.background = ContextCompat.getDrawable(this, background)
            binding.optionThree.text -> binding.optionThree.background = ContextCompat.getDrawable(this, background)
            binding.optionFour.text-> binding.optionFour.background = ContextCompat.getDrawable(this, background)
        }

    }

    private fun submit() {

        // primera pregunta

        if (mSelectedOption == "") {

            //cambia la pregunta
            if (checkOption) {
                mCurrentPosition++
                answered =true
            }
            else{

                Toast.makeText(this,"Please choose an answer",Toast.LENGTH_SHORT).show()
            }

            when {
                //hay preguntas disponibles
                mCurrentPosition <= mQuestionList!!.size -> {

                    if (checkOption){
                        setQuestion()
                    }




                }
                else -> {

                    intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, username)
                    intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswer.toString())
                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList?.size.toString())
                    startActivity(intent)
                    finish()
                }

            }
        } else {

            val question = mQuestionList?.get(mCurrentPosition - 1)

            if (question!!.correctAnswer != mSelectedOption) {

                answer(mSelectedOption, R.drawable.wrong_option_border)
            }else{

                correctAnswer++
            }
            answer(question.correctAnswer, R.drawable.correct_option_border)


            //decide que valor darle al boton dependiendo si quedan respuestas
            if(mCurrentPosition==mQuestionList?.size){

                binding.btnSubmit.text ="Finish"

            }

            else{

                binding.btnSubmit.text ="Next Question"
            }

            mSelectedOption="";

        }
    }

    //se comunica con el api para traer preguntas
    fun getQuestions() {

        var service = retrofit.create(ApiService::class.java)

        service.getTrivia().enqueue(object : Callback<ArrayList<Question>> {
            override fun onResponse(
                call: Call<ArrayList<Question>>?,
                response: Response<ArrayList<Question>>?
            ) {

                if (response != null) {

                    if (response.isSuccessful) {
                        mQuestionList = response?.body()!!
                        setQuestion()
                        for (wrongAnswer in mQuestionList[0].incorrectAnswers) {

                            mQuestionListAnswers.add(wrongAnswer);
                        }

                        mQuestionListAnswers.add(mQuestionList[0].correctAnswer)


                    }


                }


            }

            override fun onFailure(call: Call<ArrayList<Question>>?, t: Throwable?) {

                t?.printStackTrace()
            }


        })

    }
}