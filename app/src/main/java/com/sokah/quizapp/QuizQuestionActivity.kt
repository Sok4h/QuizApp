package com.sokah.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.sokah.quizapp.Constants.CATEGORIES
import com.sokah.quizapp.Constants.DIFFICULTY
import com.sokah.quizapp.Constants.TOTAL_QUESTIONS
import com.sokah.quizapp.Constants.USER_NAME
import com.sokah.quizapp.databinding.ActivityQuizQuestionBinding
import com.sokah.quizapp.model.Question
import com.sokah.quizapp.viewmodel.QuestionViewModel

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding
    private var mCurrentPosition: Int = 1
    private var numberCorrectAnswers: Int = 0
    private var mQuestionList = mutableListOf<Question>()
    private var mQuestionListAnswers: MutableList<String> = emptyList<String>().toMutableList()
    private var mSelectedOption: String = ""
    private var checkOption = false
    private var answered = false;
    private var username: String? = null
    private var viewModel = QuestionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        username = getIntent().getStringExtra(USER_NAME).toString()
        binding.btnSubmit.setOnClickListener(this)
        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)

        viewModel = QuestionViewModel()

        viewModel.questions.observe(this) {



            if(it.isNotEmpty()){
                mQuestionList = it
                binding.progressBar.max = mQuestionList.size + 1
                setQuestion()
            }
        }

        getQuestions()

    }

    private fun setQuestion() {

        checkOption = false;
        answered = false
        defaultOptionTextView()

        mQuestionListAnswers.clear()

        for (wrongAnswer in mQuestionList[mCurrentPosition - 1].incorrectAnswers) {

            mQuestionListAnswers.add(wrongAnswer);
        }

        mQuestionListAnswers.add(mQuestionList[mCurrentPosition - 1].correctAnswer)

        mQuestionListAnswers = mQuestionListAnswers.shuffled() as ArrayList<String>


        var currentQuestion: Question? = mQuestionList[mCurrentPosition - 1]
        binding.tvQuestion.text = currentQuestion?.question
        binding.optionOne.text = mQuestionListAnswers[0]
        binding.optionTwo.text = mQuestionListAnswers[1]
        binding.optionThree.text = mQuestionListAnswers[2]
        binding.optionFour.text = mQuestionListAnswers[3]
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgressBar.text = "$mCurrentPosition / ${mQuestionList.size}"

    }

    //resetea los estilos a todos los botones
    private fun defaultOptionTextView() {

        var options: ArrayList<TextView> = ArrayList()

        options.add(binding.optionOne)
        options.add(binding.optionTwo)
        options.add(binding.optionThree)
        options.add(binding.optionFour)

        // verifica que no haya seleccionado una opciona antes
        if (!answered) {
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

            R.id.optionOne -> selectedOptionView(
                binding.optionOne,
                binding.optionOne.text.toString()
            )
            R.id.optionTwo -> selectedOptionView(
                binding.optionTwo,
                binding.optionTwo.text.toString()
            )
            R.id.optionThree -> selectedOptionView(
                binding.optionThree,
                binding.optionThree.text.toString()
            )
            R.id.optionFour -> selectedOptionView(
                binding.optionFour,
                binding.optionFour.text.toString()
            )
            R.id.btnSubmit -> submit()
        }

    }

    //cambia el estilo a la opcion que es seleccionada
    private fun selectedOptionView(textView: TextView, selectedOption: String) {

        if (!answered) {

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
        answered = true
        when (answer) {

            binding.optionOne.text -> binding.optionOne.background =
                ContextCompat.getDrawable(this, background)
            binding.optionTwo.text -> binding.optionTwo.background =
                ContextCompat.getDrawable(this, background)
            binding.optionThree.text -> binding.optionThree.background =
                ContextCompat.getDrawable(this, background)
            binding.optionFour.text -> binding.optionFour.background =
                ContextCompat.getDrawable(this, background)
        }

    }

    private fun submit() {

        // primera pregunta

        if (mSelectedOption == "") {

            //cambia la pregunta
            if (checkOption) {
                mCurrentPosition++
                answered = true
            } else {

                Toast.makeText(this, "Please choose an answer", Toast.LENGTH_SHORT).show()
            }

            when {
                //hay preguntas disponibles
                mCurrentPosition <= mQuestionList.size -> {

                    if (checkOption) {
                        setQuestion()
                    }


                }
                else -> {

                    intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(USER_NAME, username)
                    Log.e("puta madre", intent.getStringExtra(USER_NAME)!! )
                    intent.putExtra(Constants.CORRECT_ANSWERS, numberCorrectAnswers.toString())
                    intent.putExtra(TOTAL_QUESTIONS, mQuestionList?.size.toString())
                    startActivity(intent)
                    finish()
                }

            }
        } else {

            val question = mQuestionList?.get(mCurrentPosition - 1)

            if (question!!.correctAnswer != mSelectedOption) {

                answer(mSelectedOption, R.drawable.wrong_option_border)
            } else {

                numberCorrectAnswers++
            }
            answer(question!!.correctAnswer, R.drawable.correct_option_border)


            //decide que valor darle al boton dependiendo si quedan respuestas
            if (mCurrentPosition == mQuestionList.size) {

                binding.btnSubmit.text = "Finish"

            } else {

                binding.btnSubmit.text = "Next Question"
            }

            mSelectedOption = "";

        }
    }

    fun getQuestions() {

        viewModel.getQuestions(
            intent.getStringExtra(CATEGORIES)!!, intent.getIntExtra(
                TOTAL_QUESTIONS, 10
            ), intent.getStringExtra(DIFFICULTY)!!
        )


    }
}
