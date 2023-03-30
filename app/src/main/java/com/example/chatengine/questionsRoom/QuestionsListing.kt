package com.example.chatengine.questionsRoom

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")


fun QuestionsListing(questionViewModel: QuestionsViewModel) {
    val questions = listOf(
        Questions(
            id = 1,
            title = "Nye Prepaid Card",
            subQuestions = listOf(
                SubQuestion(
                    id = 1,
                    question = "Check Balance",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Cards",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on view balance to check your balance",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 2,
                    question = "Change PIN",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Cards",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on change pin",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 4,
                            question = "Enter new PIN",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 5,
                            question = "Enter the OTP for completing the process.",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 3,
                    question = "ISSUE A NEW CARD",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Cards",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on request new card",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 4,
                    question = " BLOCK CARD",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Cards",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on block card and enter the reason",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                )

            )
        ),

        Questions(
            id = 2,
            title = "OPEN ACCOUNT",
            subQuestions = listOf(
                SubQuestion(
                    id = 1,
                    question = "Salary Account",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Accounts",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Apply for Salary Account",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 4,
                            question = "You will get a ticket for your request",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 2,
                    question = "Fixed Deposit",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Accounts",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on My Deposits",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 4,
                            question = "Click on create new Deposit",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 3,
                    question = "Recurring Deposit",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to My Accounts",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on My Deposits",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 4,
                            question = "Click on create a recurring deposit",
                            subQuestions = null // No sub-sub-questions for this question
                        )


                    )
                ),
                SubQuestion(
                    id = 4,
                    question = "Joint Account",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Select apply for new Account",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Select Joint Account from the options and follow",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                )

            )
        ),
        Questions(
            id = 3,
            title = "Rapi Money",
            subQuestions = listOf(
                SubQuestion(
                    id = 1,
                    question = "Pick from the wide options of mutual funds",
                    subQuestions = null
                ),
                SubQuestion(
                    id = 2,
                    question = "Get maximum profit by investing into suggested mutual funds",
                    subQuestions = null
                ),
            )
        ),
        Questions(
            id = 4,
            title = "UPI Payments",
            subQuestions = listOf(
                SubQuestion(
                    id = 1,
                    question = "Add Bank Account",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to UPI",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Select Add Bank Account",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 4,
                            question = "You will recieve a message on success",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 2,
                    question = "Change UPI pin",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to UPI",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on change UPI",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 4,
                            question = "Enter the new PIN",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 5,
                            question = "Enter the OTP for completing the process",
                            subQuestions = null // No sub-sub-questions for this question
                        )

                    )
                ),
                SubQuestion(
                    id = 3,
                    question = "Link your number with UPI ID",
                    subQuestions = listOf(
                        SubQuestion(
                            id = 1,
                            question = "Login to NYE Banking App",
                            subQuestions = null// No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 2,
                            question = "Go to UPI",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                        SubQuestion(
                            id = 3,
                            question = "Click on link number",
                            subQuestions = null // No sub-sub-questions for this question
                        ),
                    )
                ),
            )

        )
    )

    val convertedQuestions = questions.map { question ->
        Questions(
            id = question.id,
            title = question.title,
            subQuestions = question.subQuestions.map { subQuestion ->
                SubQuestion(
                    id = subQuestion.id,
                    question = subQuestion.question,
                    subQuestions = subQuestion.subQuestions
                )
            }
        )
    }

    questionViewModel.insertQuestions(convertedQuestions)
//    LaunchedEffect(Unit) {
//    }

}