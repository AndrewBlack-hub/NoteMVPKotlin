package com.example.notemvpkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupNavigationController(navController)
    }

    private fun setupNavigationController(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
}