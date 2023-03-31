package com.example.chatengine.questionsRoom


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//@Entity annotation specifies that this data class represents a table in a Room database
@Entity(tableName = "questions")
@TypeConverters(SubQuestionConverter::class)

/*
* Questions data class has a nested data class SubQuestion.
* Questions has three properties - an id, a title, and a list of SubQuestions.
* SubQuestion has four properties - an id, a question, and two optional lists of SubQuestions.
*/

data class Questions(
    @PrimaryKey val id: Int,
    val title: String,
    val subQuestions: List<SubQuestion>
)

data class SubQuestion(
    val id: Int,
    val question: String,
    val subQuestions: List<SubQuestion>? = null,// Add a list of sub-sub-questions
    val nestedSubQuestions: List<SubQuestion>? = null
)


/*
@TypeConverters annotation specifies that the SubQuestionConverter class should
be used to convert the subQuestions property of the Questions data class to and
from a string when saving it to or retrieving it from the database.
*/
class SubQuestionConverter {
    @TypeConverter
    fun fromSubQuestionList(subQuestions: List<SubQuestion>): String {
        val gson = Gson()
        return gson.toJson(subQuestions)
    }

    @TypeConverter
    fun toSubQuestionList(subQuestionsString: String): List<SubQuestion> {
        val gson = Gson()
        val type = object : TypeToken<List<SubQuestion>>() {}.type
        return gson.fromJson(subQuestionsString, type)
    }
}