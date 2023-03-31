package com.example.chatengine.questionsRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatengine.questionsRoom.room.QuestionsDao


//@Database specifies that it is a Room database
@Database(entities = [Questions::class], version = 21)
abstract class QuestionsDataBase : RoomDatabase() {
    //RoomDatabase is a class provided by the Room library.


    //abstract function which returns a QuestionsDao object
    abstract fun questionDao(): QuestionsDao

    companion object {

        // This variable is used to store the singleton instance of the database.
        @Volatile
        private var instance: QuestionsDataBase? = null

        //gets instance of the database and ensure that only one instance of the database is created..
        fun getInstance(context: Context): QuestionsDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuestionsDataBase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build().also { instance = it }
                //if a migration from the current version of the database to the new version is not possible, the existing database will be deleted and a new one will be created.
            }
        }
    }
}