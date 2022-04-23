package com.example.gamelivedata

import android.content.Context
import kotlin.random.Random

object QuestionRepository {
    //   val questionList = arrayListOf(
//        "result of : 25 + 67 ",
//        "result of : 15 - 2 ",
//        "result of : 77 * 55 ",
//        "result of : 48 + 17 ",
//        "result of : 51 - 42 ",
//        "result of : 98 * 89 ",
//        "result of : 12 + 44 ",
//        "result of : 199 - 58",
//        "result of : 32 * 13 ",
//        "result of : 153 + 17 ",
//        "result of : 352 - 42 ",
//        "result of : 98 * 9 ",
//    )
//    val answerList = arrayListOf(
//        "92",
//        "13",
//        "132",
//        "65",
//        "9",
//        "8722",
//        "56",
//        "141",
//        "45",
//        "170",
//        "314",
//        "882",
//    )
    private var db: AppDatabase? = null
    private var questionDao: QuestionDao? = null

    fun initDB(context: Context) {
        db = AppDatabase.getAppDataBase(context)

        questionDao = db?.questionDao()

        addTestData()
    }

    private fun addTestData() {
        questionDao?.insertAll(
            Question(1, "q1", 1),
            Question(2, "q2", 2)
        )
    }

    fun getQuestions(): List<Question> {
        return db!!.questionDao().getAll()
    }

}