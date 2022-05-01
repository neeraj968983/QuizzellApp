package com.example.quizellapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class NewQuizzes : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_quizzes)

        var drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        var navView: NavigationView = findViewById(R.id.nav_view)


        toggle = ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.toolbar), R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)


        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.FinishedQuizzes -> {
                    var intent: Intent = Intent(this, FinishedQuizzes::class.java)
                    startActivity(intent)
                }
                R.id.back -> {
                    onBackPressed()
                }
            }
            true
        }
    }
}