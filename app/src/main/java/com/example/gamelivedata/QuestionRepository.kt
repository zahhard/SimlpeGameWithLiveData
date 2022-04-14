package com.example.gamelivedata

import android.content.Context
import kotlin.random.Random

object QuestionRepository {
   val questionList = arrayListOf(
        "result of : 25 + 67 ",
        "result of : 15 - 2 ",
        "result of : 77 * 55 ",
        "result of : 48 + 17 ",
        "result of : 51 - 42 ",
        "result of : 98 * 89 ",
        "result of : 12 + 44 ",
        "result of : 199 - 58",
        "result of : 32 * 13 ",
        "result of : 153 + 17 ",
        "result of : 352 - 42 ",
        "result of : 98 * 9 ",
    )
    val answerList = arrayListOf(
        "92",
        "13",
        "132",
        "65",
        "9",
        "8722",
        "56",
        "141",
        "45",
        "170",
        "314",
        "882",
    )

    var id = 2

    var db : AppDatabase? = null
    var questionDao : QuestionDao? = null
    fun initDB(context: Context){
        db = AppDatabase.getDatabase(context)
        questionDao = db?.questionDao()
        addTestData()
    }

    private fun addTestData() {
        questionDao?.insertAll(
            Question(0, "result of : 25 + 67 ", 92),
            Question(1,  "result of : 15 - 2 ", 13),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion(),
            newRandomQuestion()
        )

    }

    private fun newRandomQuestion(): Question{
        id ++
        var firstNum = Random.nextInt()
        var secondNum = Random.nextInt()
        var answer = 0
        var desc = ""
         if (firstNum <= secondNum) {
            answer = secondNum - firstNum
             desc = "result of : $secondNum - $firstNum"
        } else {
           answer = firstNum - secondNum
             desc = "result of : $firstNum - $secondNum"
        }

        return Question(id, desc, answer)
    }

    fun addNewQuestion(){
        db!!.questionDao().insertAll(
            newRandomQuestion()
        )
    }
}