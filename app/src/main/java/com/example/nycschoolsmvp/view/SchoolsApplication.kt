package com.example.nycschoolsmvp.view

import android.app.Application
import android.content.Context

class SchoolsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        schoolsContext = applicationContext
    }
    companion object{
        lateinit var schoolsContext: Context
    }
}