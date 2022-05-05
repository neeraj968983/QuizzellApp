package com.example.quizellapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner

class RankingCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking_category)

        var i = intent

        val categorySpinner:Spinner = findViewById(R.id.CategorySpinner)
        val searchButton:Button = findViewById(R.id.SearchButton)
        val quizList:ListView = findViewById(R.id.QuizNameListByCategory)
        val back:Button = findViewById(R.id.backButton)

        var quizInfoDatabase = QuizInfoDatabase(this)

        ArrayAdapter.createFromResource(this,R.array.Category, android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySpinner.adapter=adapter
            categorySpinner.setSelection(0,false)
        }


        var quizNames = quizInfoDatabase.quizNameList()

        var quizName= ""

        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, quizNames)
        quizList.setBackgroundColor(Color.parseColor("#00000000"))
        quizList.adapter = arrayAdapter
        quizList.setOnItemClickListener { parent, view, position, id ->
            quizName = parent.getItemAtPosition(position).toString()
            if(quizName != ""){
                var intent = Intent(this,RankingPage::class.java)
                intent.putExtra("QuizName",quizName)
                intent.putExtra("username",i.getStringExtra("username"))
                startActivity(intent)
            }
        }

        searchButton.setOnClickListener{
            var searchCategoryItem = categorySpinner.selectedItem.toString()
            var quizNameList:List<String> = quizInfoDatabase.getQuizNameByCategory(searchCategoryItem)
            val arrayAdapter2:ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, quizNameList)
            System.out.println("category = $searchCategoryItem \n Quiz names: ${quizNameList}")
               quizList.setBackgroundColor(Color.parseColor("#00000000"))
            quizList.adapter = arrayAdapter2
            quizList.setOnItemClickListener { parent, view, position, id ->
                quizName = parent.getItemAtPosition(position).toString()
                if(quizName != ""){
                    var intent = Intent(this,RankingPage::class.java)
                    intent.putExtra("QuizName",quizName)
                    intent.putExtra("username",i.getStringExtra("username"))
                    startActivity(intent)
                }
            }
        }
        back.setOnClickListener {
            onBackPressed()
        }


    }
}