package com.example.chatengine.questionsRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsViewModel(application: Application) : AndroidViewModel(application) {

    private val questionDao = QuestionsDataBase.getInstance(application).questionDao()

    private val _questions = MutableLiveData<List<Questions>>()
    val questions: LiveData<List<Questions>>
        get() = _questions

    fun loadQuestions() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val questions = questionDao.getAllQuestions()
                val nestedQuestions = loadNestedQuestions(questions)
                _questions.postValue(nestedQuestions)
            }
        }
    }

    private fun loadNestedQuestions(questions: List<Questions>): List<Questions> {
        return questions.map { question ->
            val subQuestions = question.subQuestions.map { subQuestion ->
                val nestedSubQuestions = subQuestion.subQuestions?.let { loadNestedSubQuestions(it) }
                subQuestion.copy(subQuestions = nestedSubQuestions ?: emptyList())
            }
            question.copy(subQuestions = subQuestions)
        }
    }

    private fun loadNestedSubQuestions(subQuestions: List<SubQuestion>): List<SubQuestion> {
        return subQuestions.map { subQuestion ->
            val nestedSubQuestions = subQuestion.subQuestions?.let { loadNestedSubQuestions(it) }
            subQuestion.copy(subQuestions = nestedSubQuestions ?: emptyList())
        }
    }

    fun insertQuestions(questions: List<Questions>) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertAll(questions)
        }
    }
}