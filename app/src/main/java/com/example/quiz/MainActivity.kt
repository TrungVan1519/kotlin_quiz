package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val NAME = "NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btnStart.setOnClickListener {
            if (txtName.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please and enter your name", Toast.LENGTH_LONG).show()
            } else {
                val i = Intent(this, QuizQuestionsActivity::class.java)
                i.putExtra(NAME, txtName.text.toString())
                startActivity(i)
            }
        }
    }
}
