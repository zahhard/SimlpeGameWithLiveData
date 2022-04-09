package com.example.gamelivedata
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel(){

    val questionCount = QuestionRepository.questionList.size
    val numberLiveData = MutableLiveData<Int>(1)
    val nextEnableLiveData = MutableLiveData<Boolean>(true)
    val backEnableLiveData = MutableLiveData<Boolean>(false)
    val questionTextLiveData = MutableLiveData<String>()
    val answerTextLiveData = MutableLiveData<String>()
    val scoreLiveData = MutableLiveData<Int>(0)

    fun init(){
        numberLiveData.value?.let {
            questionTextLiveData.value = QuestionRepository.questionList[it - 1]
        }
    }

    fun nextClicked() {
        numberLiveData.value = numberLiveData.value?.plus(1)
        numberLiveData.value?.let {
            questionTextLiveData.value = QuestionRepository.questionList[it - 1]
            answerTextLiveData.value = QuestionRepository.answerList[it - 2]
        }
        checkEnabled()
    }

    private fun checkEnabled() {
        nextEnableLiveData.value = numberLiveData.value != questionCount
        backEnableLiveData.value = numberLiveData.value != 1
    }

    fun reset() {
        numberLiveData.value = 1
        scoreLiveData.value = 0
        init()
    }

    fun back() {
        numberLiveData.value = numberLiveData.value?.minus(1)
        numberLiveData.value?.let {
            questionTextLiveData.value = QuestionRepository.questionList[it-1]
            answerTextLiveData.value = QuestionRepository.answerList[it-2]
        }
        checkEnabled()
    }

    fun checkCorrectly(text: String) {
        if (answerTextLiveData.value.toString() == text){
            scoreLiveData.value = scoreLiveData.value?.plus(3)
        }
        else
            scoreLiveData.value = scoreLiveData.value?.minus(1)
        scoreLiveData
    }

    val hintLiveData = Transformations.map( numberLiveData ) {
        if (it <= questionCount / 2)
            "Hurry up"
        else
            "You have completed half of the questions"
    }

    val ScoreAnalyzeLiveData = Transformations.map( scoreLiveData ) {
        if (it <= questionCount / 4)
            "#f4171f"
        else if (it > questionCount / 4 && it <= questionCount / 2)
            "#ff7f27"
        else
            "#0ed145"

    }
}