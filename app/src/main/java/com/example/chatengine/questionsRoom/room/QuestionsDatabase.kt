package com.example.chatengine.questionsRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatengine.questionsRoom.room.QuestionsDao

@Database(entities = [Questions::class], version = 21)
abstract class QuestionsDataBase : RoomDatabase() {

    abstract fun questionDao(): QuestionsDao

    companion object {
        @Volatile
        private var instance: QuestionsDataBase? = null

        fun getInstance(context: Context): QuestionsDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuestionsDataBase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}