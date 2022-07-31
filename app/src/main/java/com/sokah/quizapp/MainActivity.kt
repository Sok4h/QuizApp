package com.sokah.quizapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
/*
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
*/
        //actionBar?.hide()

        binding.btnStart.setOnClickListener {


            if (binding.inputName.text.isEmpty()) {

                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }else{

                var intent = Intent(this,QuizSetupActivity::class.java)
                name = binding.inputName.text.toString()

                intent.putExtra(Constants.USER_NAME,name)
                Log.e("MAIN", intent.getStringExtra(Constants.USER_NAME)!! )
                startActivity(intent)
                finish()
            }
        }
    }
}