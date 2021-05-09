package com.sokah.quizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sokah.quizapp.databinding.ActivityQuizQuestionBinding

class QuizQuestionActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding

    private var mCurrentPosition : Int = 1
    private var mQuestionList : ArrayList<Question> ? = null
    private var mSelectedOption : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQuizQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mQuestionList = Constants.getQuestions()
        setQuestion()

        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)


    }

    private fun setQuestion(){

        defaultOptionTextView()
        mCurrentPosition = 1
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

    private fun defaultOptionTextView (){

        var options :ArrayList<TextView> = ArrayList()

        options.add(0,binding.optionOne)
        options.add(1,binding.optionTwo)
        options.add(2,binding.optionThree)
        options.add(3,binding.optionFour)

        for(option in options){

            option.setTextColor(Color.parseColor("#7A8089"))
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border)
            option.typeface= Typeface.DEFAULT
        }
    }

    override fun onClick(v: View?) {

    when(v?.id){

        R.id.optionOne -> selectedOptionView(binding.optionOne,1)
        R.id.optionTwo -> selectedOptionView(binding.optionTwo,2)
        R.id.optionThree -> selectedOptionView(binding.optionThree,3)
        R.id.optionFour -> selectedOptionView(binding.optionFour,4)
    }

    }

    private fun selectedOptionView(textView: TextView, selectedOption: Int){

        defaultOptionTextView()
        mSelectedOption = selectedOption
        textView.setTextColor(Color.parseColor("#363A43"))
        textView.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border)
        textView.setTypeface(textView.typeface,Typeface.BOLD)
    }
}