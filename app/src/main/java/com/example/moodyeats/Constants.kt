package com.example.moodyeats

import com.example.moodyeats.R
import com.example.moodyeats.Question

object Constants {

    // TODO (STEP 1: Create a constant variables which we required in the result screen.)
    // START
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS_SCORE: String = "correct_answers"
    // END

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // 1
        val que1 = Question(
            1, "How hungary are you?",
            "Starving", "Not extremely hungry",
            "Just regular hungary", "Haven't eaten anything till yet", 1
        )

        questionsList.add(que1)

        // 2
        val que2 = Question(
            2, "Are you looking for a variety?",
            "Yes! please", "I am good with the regular food",
            "I can try", "I can eat anything", 3
        )

        questionsList.add(que2)

        // 3
        val que3 = Question(
            3, "Did you eat a meal recently?",
            "Yeah just ate moments ago", "It's been a short while",
            "It's been a long while", "Nope! nothing till yet", 4
        )

        questionsList.add(que3)

        // 4
        val que4 = Question(
            4, "Do you have a sweet tooth?",
            "Yes I do", "Sometimes but not right now",
            "Not at all", "Nope! not that type of a person", 2
        )

        questionsList.add(que4)

        // 5
        val que5 = Question(
            5, "What time of the day it is?",
            "Night", "Morning",
            "Afternoon", "Midnight", 3
        )

        questionsList.add(que5)

        // 6
        val que6 = Question(
            6, "What are you doing right now?",
            "Sitting at home trying to figure out what to eat", "Starving for food right now",
            "Looking at takeout menues", "Taking this quiz", 1
        )

        questionsList.add(que6)

        // 7
        val que7 = Question(
            7, "What type of city do you live in?",
            "Small", "Big",
            "Medium-sized", "Not In a city", 3
        )

        questionsList.add(que7)

        // 8
        val que8 = Question(
            8, "Do you eat pretty healthy most of the time?",
            "Mostly", "I try to",
            "Once in a while", "Not at all", 4
        )

        questionsList.add(que8)

        // 9
        val que9 = Question(
            9, "Are you a picky eater?",
            "Very", "Kind of",
            "Sometimes", "Not at all", 2
        )

        questionsList.add(que9)

        // 10
        val que10 = Question(
            10, "Are you someone who likes traditional meals?",
            "YUp! that's me", "I like to try new things",
            "It depends", "Not at all", 1
        )

        questionsList.add(que10)

        return questionsList
    }
}