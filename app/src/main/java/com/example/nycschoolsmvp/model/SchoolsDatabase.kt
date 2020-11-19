package com.example.nycschoolsmvp.model

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.nycschoolsmvp.view.SchoolsApplication

const val DB_NAME = "nyc_database"
const val DB_VERSION = 1

class SchoolsDatabase()
    : SQLiteOpenHelper(SchoolsApplication.schoolsContext,
        DB_NAME,
        null,
        DB_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE ${TABLE_SCHOOLS::class.simpleName}" +
                " (${TABLE_SCHOOLS.COLUMN_DBN} PRIMARY KEY VARCHAR(255), " +
                "${TABLE_SCHOOLS.COLUMN_SCHOOL_NAME} VARCHAR(255), " +
                "${TABLE_SCHOOLS.COLUMN_LOCATION} VARCHAR(255), " +
                "${TABLE_SCHOOLS.COLUMN_PHONE_NUMBER} VARCHAR(255))")
        p0?.execSQL("CREATE TABLE ${TABLE_SAT::class.simpleName}" +
                " (${TABLE_SAT.COLUMN_DBN} PRIMARY KEY VARCHAR(255), " +
                "${TABLE_SAT.COLUMN_SAT_CRIT_READ} VARCHAR(255), " +
                "${TABLE_SAT.COLUMN_SAT_MATH_AVG} VARCHAR(255), " +
                "${TABLE_SAT.COLUMN_SAT_TEST_NUM} VARCHAR(255), " +
                "${TABLE_SAT.COLUMN_SAT_WRITING_AVG} VARCHAR(255))")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}


object TABLE_SCHOOLS{
    val COLUMN_DBN: String = "dbn"
    val COLUMN_SCHOOL_NAME = "name"
    val COLUMN_LOCATION = "location"
    val COLUMN_PHONE_NUMBER = "phone_number"
}
object TABLE_SAT{
    val COLUMN_DBN = "dbn"
    val COLUMN_SAT_TEST_NUM = "sat_test_takers"
    val COLUMN_SAT_CRIT_READ = "sat_crit_read"
    val COLUMN_SAT_MATH_AVG = "sat_math_avg"
    val COLUMN_SAT_WRITING_AVG = "sat_writing_avg"
}


