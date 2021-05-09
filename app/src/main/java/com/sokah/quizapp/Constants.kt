package com.sokah.quizapp

object Constants {

    fun getQuestions():ArrayList<Question>{

        val questionsList = ArrayList<Question>()
        val q1 = Question(1,"what country does this flag belong to",R.drawable.argentina_flag,
        "argentina","colombia","chile","peru",1)

        questionsList.add(q1)

        val q2 = Question(2,"what country does this flag belong to",R.drawable.colombia_flag,
            "colombia","argentina","chile","peru",1)

        questionsList.add(q2)

        val q3 = Question(3,"what country does this flag belong to",R.drawable.peru_flag,
            "colombia","argentina","chile","peru",4)

        questionsList.add(q3)
        return questionsList
    }

}