package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // TODO: Hide status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val name = intent.getStringExtra(MainActivity.NAME)
        val totalQuestions = intent.getIntExtra(QuizQuestionsActivity.TOTAL_QUESTIONS, 0)
        val totalScores = intent.getIntExtra(QuizQuestionsActivity.TOTAL_SCORES, 0)

        txtName.text = name
        txtScore.text = "Your score is $totalScores/$totalQuestions"

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
