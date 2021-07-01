package com.example.quiz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quiz.models.Data
import com.example.quiz.models.Question
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity() {

    companion object {
        const val TOTAL_SCORES = "TOTAL_SCORES"
        const val TOTAL_QUESTIONS = "TOTAL_QUESTIONS"
    }

    private var questions: ArrayList<Question>? = Data.getQuestions()
    private var questionId = 1
    private var selectedOption = 1

    private var totalScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        // TODO: first question
        var question = questions!![questionId - 1]
        setQuestion(question)
        setOptions() // default options style

        val e = View.OnClickListener { v ->
            if (v.id != btnSubmit.id) {
                when (v.id) {
                    txtOption1.id -> selectedOption = 1
                    txtOption2.id -> selectedOption = 2
                    txtOption3.id -> selectedOption = 3
                    txtOption4.id -> selectedOption = 4
                }
                setOptions(v, R.drawable.selected_option_border_bg)

                return@OnClickListener
            }

            if (questionId <= questions!!.size) {
                // TODO: check answer
                var drawable = R.drawable.wrong_option_border_bg

                if (selectedOption == question.answer) {
                    drawable = R.drawable.correct_option_border_bg
                    totalScore++
                }

                when (selectedOption) {
                    1 -> setOptions(txtOption1, drawable)
                    2 -> setOptions(txtOption2, drawable)
                    3 -> setOptions(txtOption3, drawable)
                    4 -> setOptions(txtOption4, drawable)
                }

                disableOptionsAndButton()

                if (questionId == questions!!.size) {
                    AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("All questions completed.")
                        .setMessage("Do you want to see result, ${intent.getStringExtra(MainActivity.NAME)}?")
                        .setPositiveButton("Yes") { _, _ ->
                            val i = Intent(this, ResultActivity::class.java)
                            i.putExtra(TOTAL_SCORES, totalScore)
                            i.putExtra(TOTAL_QUESTIONS, questions!!.size)
                            i.putExtra(MainActivity.NAME, intent.getStringExtra(MainActivity.NAME))

                            startActivity(i)
                            finish()
                        }
                        .setNegativeButton("No") { _, _ ->
                            finish()
                        }
                        .setNeutralButton("Restart") { _, _ ->
                            questionId = 1
                            question = questions!![questionId - 1]
                            setQuestion(question)
                            setOptions() // default options style
                        }
                        .create().show()
                } else {
                    // TODO: next question
                    Handler().postDelayed({
                        questionId++
                        question = questions!![questionId - 1]
                        setQuestion(question)
                        setOptions() // default options style
                    }, 500)
                }
            }
        }
        txtOption1.setOnClickListener(e)
        txtOption2.setOnClickListener(e)
        txtOption3.setOnClickListener(e)
        txtOption4.setOnClickListener(e)
        btnSubmit.setOnClickListener(e)
    }

    private fun disableOptionsAndButton() {
        btnSubmit.isEnabled = false
        arrayListOf(txtOption1, txtOption2, txtOption3, txtOption4).forEach { txt ->
            txt.isEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion(question: Question) {
        txtQuestion.text = question.question
        imgQuestion.setImageResource(question.image)

        progressBar.progress = questionId
        txtProgress.text = "$questionId/${progressBar.max}"

        txtOption1.text = question.option1
        txtOption2.text = question.option2
        txtOption3.text = question.option3
        txtOption4.text = question.option4

        btnSubmit.isEnabled = true
    }

    private fun setOptions(v: View? = null, drawable: Int? = null) {
        arrayListOf(txtOption1, txtOption2, txtOption3, txtOption4).forEach { txt ->
            txt.isEnabled = true
            txt.setTextColor(Color.parseColor("#7A8089"))
            txt.typeface = Typeface.DEFAULT
            txt.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

        if (v != null && drawable != null) {
            v as TextView // only need to cast 1 time

            v.isEnabled = true
            v.setTextColor(Color.parseColor("#363A43"))
            v.setTypeface(v.typeface, Typeface.BOLD_ITALIC)
            v.background = ContextCompat.getDrawable(this, drawable)
        }
    }
}
