package com.example.notemvpkotlin.data

import android.app.Application
import androidx.room.Room

class App: Application() {

    companion object {
        private const val DB_NAME: String = "notes.db"
        var database: NotesDatabase? = null
        private var instance: App? = null

        fun getInstance(): App? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(applicationContext, NotesDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getDatabase(): NotesDatabase? {
        return database
    }
}