package com.sokah.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import com.google.gson.Gson
import com.sokah.quizapp.Constants.CATEGORIES
import com.sokah.quizapp.Constants.TOTAL_QUESTIONS
import com.sokah.quizapp.Constants.USER_NAME
import com.sokah.quizapp.databinding.ActivityQuizSetupBinding

class QuizSetupActivity : AppCompatActivity() {


    lateinit var binding: ActivityQuizSetupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var categoriesCheck = ""


        var listcheckbox: ArrayList<CheckBox> =
            arrayListOf(binding.cbFood, binding.cbHistory, binding.cbMovies, binding.cbSports)


        binding.startQuiz.setOnClickListener {

            if (binding.textInputLayout.editText!!.text.isEmpty() || binding.textInputLayout.editText!!.text.toString()
                    .toInt() !in 1..20
            ) {

                Toast.makeText(this, "Please select a number of questions", Toast.LENGTH_SHORT)
                    .show()

            } else {


                for (checkbox in listcheckbox) {

                    if (checkbox.isChecked) {

                        when (checkbox.id) {

                            R.id.cbFood -> categoriesCheck = "$categoriesCheck,food_and_drink"

                            R.id.cbHistory -> categoriesCheck = "$categoriesCheck,history"

                            R.id.cbMovies -> categoriesCheck = "$categoriesCheck,film_and_tv"

                            R.id.cbSports -> categoriesCheck = "$categoriesCheck,sport"
                        }

                    }
                }


                if (categoriesCheck.isNotEmpty()) {


                    val intent = Intent(this, QuizQuestionActivity::class.java)
                    intent.putExtra(Constants.DIFFICULTY, binding.spinner.selectedItem.toString())
                    intent.putExtra(
                        TOTAL_QUESTIONS,
                        binding.textInputLayout.editText!!.text.toString().toInt()
                    )
                    intent.putExtra(CATEGORIES, categoriesCheck)
                    intent.putExtra(USER_NAME, getIntent().getStringExtra(USER_NAME).toString())
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(this, "Pleas choose a category", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }
}