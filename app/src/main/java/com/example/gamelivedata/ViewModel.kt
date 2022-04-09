package com.example.gamelivedata
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel(){

    val questionCount = QuestionRepository.questionList.size
    val numberLiveData = MutableLiveData<Int>(0)
    val nextEnableLiveData = MutableLiveData<Boolean>(true)
    val backEnableLiveData = MutableLiveData<Boolean>(false)
    val questionTextLiveData = MutableLiveData<String>()

    fun nextClicked() {
        numberLiveData.value = numberLiveData.value?.plus(1)
        numberLiveData.value?.let {
            questionTextLiveData.value = QuestionRepository.questionList[it-1]
        }
        checkEnabled()
    }

    private fun checkEnabled() {
        nextEnableLiveData.value = numberLiveData.value != questionCount
        backEnableLiveData.value = numberLiveData.value != 1
    }

    fun reset() {
        numberLiveData.value = 0
        nextClicked()
    }

    fun back() {
        numberLiveData.value = numberLiveData.value?.minus(1)
        numberLiveData.value?.let {
            questionTextLiveData.value = QuestionRepository.questionList[it-1]
        }
        checkEnabled()
    }

    val hintLiveData = Transformations.map( numberLiveData) {
        if (it <= questionCount / 2)
            "Hurry up"
        else
            "good"
    }
}