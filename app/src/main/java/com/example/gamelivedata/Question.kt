package com.example.gamelivedata

import androidx.room.*

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true) val id : Int,
)

@Dao
interface QuestionDao {
    @Query("SELECT * FROM Question")
    fun getAll(): List<Question>

    @Insert
    fun insertAll(vararg questions: Question)

    @Delete
    fun delete(question : Question)
}