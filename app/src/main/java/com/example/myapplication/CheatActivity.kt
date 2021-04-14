package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"


private const val KEY_INDEXES = "indexes"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var textViewId: TextView
    private var saveOrientation = 0
    private var count = 0
    private var answerIsTrue = false
    var hints = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        hints = intent.getIntExtra("Hint", 0)
        count = intent.getIntExtra("Counter", 0)
        saveOrientation = savedInstanceState?.getInt(KEY_INDEXES, 0) ?: 0
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        textViewId = findViewById(R.id.text_view_id)
        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            textViewId.setText("$hints")
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
        cheatClickLimit()
    }
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK , data)
    }
    private fun cheatClickLimit(){
        if (count>3){
            showAnswerButton.isEnabled = false
            showAnswerButton.isClickable = false
        }
    }
    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean,count: Int,hint: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra("Counter", count)
                putExtra("Hint", hint)
            }
        }
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEXES, saveOrientation)
    }
}
