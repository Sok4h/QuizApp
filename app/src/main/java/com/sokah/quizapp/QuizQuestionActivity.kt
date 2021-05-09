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

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding

    private var mCurrentPosition: Int = 1
    private var correctAnswer :Int = 0
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOption: Int = 0
    private var checkOption = false
    private  var username:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mQuestionList = Constants.getQuestions()
        setQuestion()
        username= intent.getStringExtra(Constants.USER_NAME).toString()
        binding.btnSubmit.setOnClickListener(this)
        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)


    }

    private fun setQuestion() {
        checkOption=false;
        defaultOptionTextView()
        var currentQuestion: Question? = mQuestionList?.get(mCurrentPosition - 1)
        binding.tvQuestion.text = currentQuestion?.question
        binding.optionOne.text = currentQuestion?.option1
        binding.optionTwo.text = currentQuestion?.option2
        binding.optionThree.text = currentQuestion?.option3
        binding.optionFour.text = currentQuestion?.option4
        binding.questionImage.setImageResource(currentQuestion!!.image)
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

        for (option in options) {

            option.setTextColor(Color.parseColor("#7A8089"))
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border)
            option.typeface = Typeface.DEFAULT
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.optionOne -> selectedOptionView(binding.optionOne, 1)
            R.id.optionTwo -> selectedOptionView(binding.optionTwo, 2)
            R.id.optionThree -> selectedOptionView(binding.optionThree, 3)
            R.id.optionFour -> selectedOptionView(binding.optionFour, 4)
            R.id.btnSubmit -> submit()
        }

    }

    //cambia el estilo a la opcion que es seleccionada
    private fun selectedOptionView(textView: TextView, selectedOption: Int) {

        if(!checkOption) {
            defaultOptionTextView()

            if (mCurrentPosition == mQuestionList!!.size) {

                binding.btnSubmit.text = "Finish"
            } else {
                binding.btnSubmit.text = "Submit"

            }
            mSelectedOption = selectedOption
            textView.setTextColor(Color.parseColor("#363A43"))
            textView.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border)
            textView.setTypeface(textView.typeface, Typeface.BOLD)
        }
    }


    private fun answer(answer: Int, background: Int) {

        when (answer) {

            1 -> binding.optionOne.background = ContextCompat.getDrawable(this, background)
            2 -> binding.optionTwo.background = ContextCompat.getDrawable(this, background)
            3 -> binding.optionThree.background = ContextCompat.getDrawable(this, background)
            4 -> binding.optionFour.background = ContextCompat.getDrawable(this, background)
        }

    }

    private fun submit (){

        checkOption=true;
        // primera pregunta
        if (mSelectedOption == 0) {
            //cambia la pregunta
            mCurrentPosition++

            when {
                //hay preguntas disponibles
                mCurrentPosition <= mQuestionList!!.size -> {
                    setQuestion()
                }
                else -> {

                     intent  = Intent(this,ResultActivity::class.java)
                    intent.putExtra(Constants.USER_NAME,username)
                    intent.putExtra(Constants.CORRECT_ANSWERS,correctAnswer.toString())
                    intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionList?.size.toString())
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

            mSelectedOption=0;

        }
    }
}