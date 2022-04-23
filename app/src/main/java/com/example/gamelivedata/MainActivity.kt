package com.example.gamelivedata

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    private fun initView() {
        val countText = findViewById<TextView>(R.id.tv_count)
        val nextButton = findViewById<Button>(R.id.btn_next)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val questionText = findViewById<TextView>(R.id.tv_question)
        val resetButton = findViewById<Button>(R.id.btn_reset)
        val backButton = findViewById<Button>(R.id.btn_back)
        val hintText = findViewById<TextView>(R.id.textView4)
        val answerText = findViewById<EditText>(R.id.ed_answer)
        val scoreText = findViewById<TextView>(R.id.tv_score)

        progressBar.progressDrawable.setColorFilter(
            Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

       // progressBar.max = viewModel.questionCount

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
//        viewModel.hintLiveData.observe(this) {
//            hintText.text = it
//        }
//        viewModel.ScoreAnalyzeLiveData.observe(this) {
//            scoreText.setTextColor(Color.parseColor(it))
//        }

        viewModel.numberLiveData.observe(this, numberObserver)
        viewModel.nextEnableLiveData.observe(this, nextButtonEnableObserver)
        viewModel.backEnableLiveData.observe(this, backButtonEnableObserver)
        viewModel.questionTextLiveData.observe(this, questionTextObserver)
        viewModel.scoreLiveData.observe(this, answerTextObserver)
    }
}