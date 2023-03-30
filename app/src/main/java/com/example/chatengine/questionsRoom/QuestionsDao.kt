package com.example.chatengine.questionsRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao

interface QuestionsDao {

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<Questions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Questions>)

    @Query("SELECT * FROM questions")
    fun getAllQuestionsFlow(): Flow<List<Questions>>
}