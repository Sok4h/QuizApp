package com.sokah.quizapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sokah.quizapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var name :String
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        //actionBar?.hide()

        binding.btnStart.setOnClickListener {

            // verifica si el usuario está vacio
            if (binding.inputName.text.isEmpty()) {

                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }else{
            //lleva a la actividad de preguntas
                var intent = Intent(this,QuizQuestionActivity::class.java)
                name = binding.inputName.text.toString()
                intent.putExtra(Constants.USER_NAME,name)
                startActivity(intent)
                finish()
            }
        }
    }
}