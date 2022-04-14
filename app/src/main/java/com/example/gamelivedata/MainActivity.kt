package com.example.gamelivedata

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        initView()
    }
    private fun initView() {
        val countText = findViewById<TextView>(R.id.textView2)
        val nextButton = findViewById<Button>(R.id.button)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val questionText = findViewById<TextView>(R.id.textView3)
        val resetButton = findViewById<Button>(R.id.button2)
        val backButton = findViewById<Button>(R.id.button3)
        val hintText = findViewById<TextView>(R.id.textView4)
        val answerText = findViewById<EditText>(R.id.ed_answer)
        val scoreText = findViewById<TextView>(R.id.tv_score)

        progressBar.progressDrawable.setColorFilter(
            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        progressBar.max = viewModel.questionCount

        viewModel.init()

        nextButton.setOnClickListener {
            viewModel.nextClicked()
            viewModel.checkCorrectly(answerText.text.toString())
            answerText.text = null
        }

        resetButton.setOnClickListener {
            viewModel.reset()
        }

        backButton.setOnClickListener {
            viewModel.back()
            answerText.text = null
        }

        val numberObserver = Observer<Int>{
           number ->
            countText.text = number.toString()
            progressBar.progress = number
        }
        val nextButtonEnableObserver = Observer<Boolean>{
           enabled ->
            nextButton.isEnabled = enabled
        }
        val backButtonEnableObserver = Observer<Boolean>{
           enabled ->
            backButton.isEnabled = enabled
        }
        val questionTextObserver = Observer<String> { question ->
            questionText.text = question
        }
        val answerTextObserver = Observer<Int> { answer ->
            scoreText.text = answer.toString()
        }
        viewModel.hintLiveData.observe(this) {
            hintText.text = it
        }
        viewModel.ScoreAnalyzeLiveData.observe(this) {
            scoreText.setTextColor(Color.parseColor(it))
        }

        viewModel.numberLiveData.observe(this, numberObserver)
        viewModel.nextEnableLiveData.observe(this, nextButtonEnableObserver)
        viewModel.backEnableLiveData.observe(this, backButtonEnableObserver)
        viewModel.questionTextLiveData.observe(this, questionTextObserver)
        viewModel.scoreLiveData.observe(this, answerTextObserver)
    }
}