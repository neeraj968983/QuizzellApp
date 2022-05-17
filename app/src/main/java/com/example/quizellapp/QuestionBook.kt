package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class QuestionBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_book)

        var questionBookDatabase = QuestionBookDatabase(this)
        var questionDatabase: QuestionDatabase = QuestionDatabase(this)

        var questionLeft: TextView = findViewById(R.id.Left)
        var category: Spinner = findViewById(R.id.CategorySpinner)
        var search: Button = findViewById(R.id.SearchButton)
        val listView: ListView = findViewById(R.id.QuestionBookList)
        val cancelButton: Button = findViewById(R.id.Cancel)
        var finish: Button = findViewById(R.id.Finish)

        var i = intent

        var count = i.getIntExtra("Count", 0)
        count = count - 1
        var totalCount = i.getIntExtra("totalQuestion", 0)
        var quizname = i.getStringExtra("QuizName")

        questionLeft.setText("" + (totalCount - count))


        ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_spinner_dropdown_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            category.adapter = adapter
            category.setSelection(0, false)
        }

        var dataOfQuestions = questionBookDatabase.getAllQuestion()

        var mentorNames = arrayListOf<String>()
        var quiznames = arrayListOf<String>()
        var categories = arrayListOf<String>()
        var questions = arrayListOf<String>()
        var optionsA = arrayListOf<String>()
        var optionsB = arrayListOf<String>()
        var optionsC = arrayListOf<String>()
        var optionsD = arrayListOf<String>()
        var correctoptions = arrayListOf<String>()

        for (j in 0..(dataOfQuestions.size - 1)) {
            mentorNames.add((dataOfQuestions[j].mentorname).toString())
            quiznames.add((dataOfQuestions[j].quizname).toString())
            categories.add((dataOfQuestions[j].category).toString())
            questions.add((dataOfQuestions[j].question).toString())
            optionsA.add((dataOfQuestions[j].optionA).toString())
            optionsB.add((dataOfQuestions[j].optionB).toString())
            optionsC.add((dataOfQuestions[j].optionC).toString())
            optionsD.add((dataOfQuestions[j].optionD).toString())
            correctoptions.add((dataOfQuestions[j].CorrectOption).toString())
        }

        var myAdaptor = CustomAdaptorQuestionBook(this, mentorNames, questions, optionsA, optionsB, optionsC, optionsD, correctoptions)
        listView.adapter = myAdaptor

        search.setOnClickListener {
            var dataOfQuestionsByCategory = questionBookDatabase.getQuestionsByCategory(category.selectedItem.toString())

            var mentorNamesbyCategory = arrayListOf<String>()
            var quiznamesbyCategory = arrayListOf<String>()
            var categoriesbyCategory = arrayListOf<String>()
            var questionsbyCategory = arrayListOf<String>()
            var optionsAbyCategory = arrayListOf<String>()
            var optionsBbyCategory = arrayListOf<String>()
            var optionsCbyCategory = arrayListOf<String>()
            var optionsDbyCategory = arrayListOf<String>()
            var correctoptionsbyCategory = arrayListOf<String>()


            for (j in 0..(dataOfQuestionsByCategory.size - 1)) {
                mentorNamesbyCategory.add((dataOfQuestionsByCategory[j].mentorname).toString())
                quiznamesbyCategory.add((dataOfQuestionsByCategory[j].quizname).toString())
                categoriesbyCategory.add((dataOfQuestionsByCategory[j].category).toString())
                questionsbyCategory.add((dataOfQuestionsByCategory[j].question).toString())
                optionsAbyCategory.add((dataOfQuestionsByCategory[j].optionA).toString())
                optionsBbyCategory.add((dataOfQuestionsByCategory[j].optionB).toString())
                optionsCbyCategory.add((dataOfQuestionsByCategory[j].optionC).toString())
                optionsDbyCategory.add((dataOfQuestionsByCategory[j].optionD).toString())
                correctoptionsbyCategory.add((dataOfQuestionsByCategory[j].CorrectOption).toString())


            }
            myAdaptor = CustomAdaptorQuestionBook(this, mentorNamesbyCategory, questionsbyCategory, optionsAbyCategory, optionsBbyCategory, optionsCbyCategory, optionsDbyCategory, correctoptionsbyCategory)
            listView.adapter = myAdaptor
        }


            listView.setOnItemClickListener { parent, view, position, id ->

                var questionDetail = questionBookDatabase.getQuestionDetail(parent.getItemAtPosition(position).toString())
                var question: Question = Question(quizname, questionDetail.ques, questionDetail.option1, questionDetail.option2, questionDetail.option3, questionDetail.option4, questionDetail.correctOption)
                System.out.println("QuizName : ${questionDetail.quizname}")
                System.out.println("Question : ${questionDetail.ques}")
                System.out.println("option A : ${questionDetail.option1}")
                System.out.println("option B : ${questionDetail.option2}")
                System.out.println("Option C : ${questionDetail.option3}")
                System.out.println("option D : ${questionDetail.option4}")
                System.out.println("correct option : ${questionDetail.correctOption}")
                questionDatabase.addQuestion(question)
                count++
                questionLeft.setText("" + (totalCount - count))
                if (totalCount == count) {
                    var intent = Intent(this, MentorDashboard::class.java)
                    intent.putExtra("usernameFromLogin", i.getStringExtra("usernameFromLogin"))
                    startActivity(intent)
                }
                Toast.makeText(this, "+ Question Added", Toast.LENGTH_SHORT).show()

            }

            cancelButton.setOnClickListener {
                onBackPressed()
            }

            finish.setOnClickListener {
                if (totalCount == count) {
                    var intent = Intent(this, MentorDashboard::class.java)
                    intent.putExtra("usernameFromLogin", i.getStringExtra("usernameFromLogin"))
                    startActivity(intent)
                } else {
                    System.out.println("Count QuestionBook: ${count + 1}")
                    var intent = Intent(this, QuizQuestionCreation::class.java)
                    intent.putExtra("Count", (count + 1))
                    intent.putExtra("usernameFromLogin", i.getStringExtra("usernameFromLogin"))
                    intent.putExtra("TotalQuestion", totalCount)
                    intent.putExtra("Category", i.getStringExtra("Category"))
                    intent.putExtra("QuizName", i.getStringExtra("QuizName"))
                    startActivity(intent)
                }
            }


    }
}