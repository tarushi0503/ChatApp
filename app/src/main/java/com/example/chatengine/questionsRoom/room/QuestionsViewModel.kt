package com.example.chatengine.questionsRoom.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatengine.questionsRoom.Questions
import com.example.chatengine.questionsRoom.QuestionsDataBase
import com.example.chatengine.questionsRoom.SubQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//View model is interacting with DAO to perform operations on room database
// Question view model manage the data of questions
class QuestionsViewModel(application: Application) : AndroidViewModel(application) {

    //instance of the QuestionsDataBase class that provides access to the questions data stored in a local database.
    private val questionDao = QuestionsDataBase.getInstance(application).questionDao()

    //holds the list of questions which is updated when new questions are loaded.
    private val _questions = MutableLiveData<List<Questions>>()
    val questions: LiveData<List<Questions>>
        get() = _questions

    // load questions from the database using coroutines
    fun loadQuestions() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val questions = questionDao.getAllQuestions()
                val nestedQuestions = loadNestedQuestions(questions)
                _questions.postValue(nestedQuestions)
            }
        }
    }

    //recursively load nested sub-questions
    private fun loadNestedQuestions(questions: List<Questions>): List<Questions> {
        return questions.map { question ->
            val subQuestions = question.subQuestions.map { subQuestion ->
                val nestedSubQuestions = subQuestion.subQuestions?.let { loadNestedSubQuestions(it) }
                subQuestion.copy(subQuestions = nestedSubQuestions ?: emptyList())
            }
            question.copy(subQuestions = subQuestions)
        }
    }

    //recursively load nested sub-questions
    private fun loadNestedSubQuestions(subQuestions: List<SubQuestion>): List<SubQuestion> {
        return subQuestions.map { subQuestion ->
            val nestedSubQuestions = subQuestion.subQuestions?.let { loadNestedSubQuestions(it) }
            subQuestion.copy(subQuestions = nestedSubQuestions ?: emptyList())
        }
    }

    //insert a list of questions into the database.
    fun insertQuestions(questions: List<Questions>) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertAll(questions)
        }
    }
}