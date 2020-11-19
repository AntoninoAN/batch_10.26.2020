package com.example.nycschoolsmvp.model

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray


class SchoolsLocal {
    private val readDatabase: SQLiteDatabase
    private val writeDatabase: SQLiteDatabase
    private val schoolsDBHelper: SchoolsDatabase

    init {
        schoolsDBHelper = SchoolsDatabase()
        readDatabase = schoolsDBHelper.readableDatabase
        writeDatabase = schoolsDBHelper.writableDatabase
    }

    fun insertListSchools(stringResponse: JSONArray){
        val gson = Gson()
        val dataSet: List<NYCListSchoolsResponse> =
            gson.fromJson(stringResponse.toString(),
                object: TypeToken<List<NYCListSchoolsResponse>>() {}.type)

        for(school in dataSet) {
            val contentValues = ContentValues()
            contentValues.put(TABLE_SCHOOLS.COLUMN_DBN, school.dbn)
            contentValues.put(TABLE_SCHOOLS.COLUMN_LOCATION, school.location)
            contentValues.put(TABLE_SCHOOLS.COLUMN_PHONE_NUMBER, school.phone_number)
            contentValues.put(TABLE_SCHOOLS.COLUMN_SCHOOL_NAME, school.school_name)
            writeDatabase.insert(
                TABLE_SCHOOLS::class.simpleName,
                null,
                contentValues)
        }
    }

    fun insertListSat(dataSet: List<NYCListSatResponse>){
        val contentWrapperValues = ContentValues()
        for(satSchool in dataSet){
            val contentValue = ContentValues()
            contentValue.put(TABLE_SAT.COLUMN_DBN, satSchool.dbn)
            contentValue.put(TABLE_SAT.COLUMN_SAT_TEST_NUM, satSchool.num_of_sat_test_takers)
            contentValue.put(TABLE_SAT.COLUMN_SAT_CRIT_READ, satSchool.sat_critical_reading_avg_score)
            contentValue.put(TABLE_SAT.COLUMN_SAT_MATH_AVG, satSchool.sat_math_avg_score)
            contentValue.put(TABLE_SAT.COLUMN_SAT_WRITING_AVG, satSchool.sat_writing_avg_score)
            contentWrapperValues.putAll(contentValue)
        }
        writeDatabase.insert(TABLE_SAT::class.simpleName,
        null,
        contentWrapperValues)
    }

    fun readListSchools(callbackSchoolList: (List<NYCListSchoolsResponse>)->Unit){
        val projection = arrayOf(TABLE_SCHOOLS.COLUMN_DBN,
        TABLE_SCHOOLS.COLUMN_SCHOOL_NAME,
        TABLE_SCHOOLS.COLUMN_LOCATION,
        TABLE_SCHOOLS.COLUMN_PHONE_NUMBER)

        val orderBy = "${TABLE_SCHOOLS.COLUMN_DBN} DESC"

        val schoolsCursor =
        readDatabase.query(TABLE_SCHOOLS::class.simpleName,
        projection,
        null,
        null,
        null,
        null,
        orderBy)

        schoolsCursor.use {

            val listOfSchools = mutableListOf<NYCListSchoolsResponse>()

            while (schoolsCursor.moveToNext()) {
                val itemSchool = NYCListSchoolsResponse(
                    schoolsCursor
                        .getString(
                            schoolsCursor
                                .getColumnIndex(TABLE_SCHOOLS.COLUMN_DBN)
                        ),
                    schoolsCursor
                        .getString(
                            schoolsCursor
                                .getColumnIndex(TABLE_SCHOOLS.COLUMN_SCHOOL_NAME)
                        ),
                    schoolsCursor
                        .getString(
                            schoolsCursor
                                .getColumnIndex(TABLE_SCHOOLS.COLUMN_LOCATION)
                        ),
                    schoolsCursor
                        .getString(
                            schoolsCursor
                                .getColumnIndex(TABLE_SCHOOLS.COLUMN_PHONE_NUMBER)
                        )
                )
                listOfSchools.add(itemSchool)
            }
            //schoolsCursor.close()
            callbackSchoolList.invoke(listOfSchools)
        }
    }

    fun readSatBySchool(dbn: String, callbackSchoolSat: (NYCListSatResponse)-> Unit){
// SELECT NAME, DBN,
// from sat
// join school on sat
// where SAT.DBN == dbn
        val selectQuery = "SELECT ${TABLE_SAT::class.simpleName}.${TABLE_SAT.COLUMN_SAT_TEST_NUM}, " +
                "${TABLE_SAT::class.simpleName}.${TABLE_SAT.COLUMN_SAT_CRIT_READ}, " +
                "${TABLE_SAT::class.simpleName}.${TABLE_SAT.COLUMN_SAT_MATH_AVG}, " +
                "${TABLE_SAT::class.simpleName}.${TABLE_SAT.COLUMN_SAT_WRITING_AVG}, "+
                "${TABLE_SCHOOLS::class.simpleName}.${TABLE_SCHOOLS.COLUMN_SCHOOL_NAME} " +
                "FROM ${TABLE_SCHOOLS::class.simpleName} JOIN ${TABLE_SAT::class.simpleName} " +
                "ON ${TABLE_SCHOOLS::class.simpleName}.${TABLE_SCHOOLS.COLUMN_DBN} " +
                "= ${TABLE_SAT::class.simpleName}.${TABLE_SAT.COLUMN_DBN}" +
                "WHERE ${TABLE_SAT.COLUMN_DBN}=$dbn"

        val listSat = mutableListOf<NYCListSatResponse>()

        readDatabase.rawQuery(selectQuery, null).use {cursor ->
            while(cursor.moveToNext()){
                val item = NYCListSatResponse(
                    cursor.getString(cursor.getColumnIndex(TABLE_SAT.COLUMN_DBN)),
                    cursor.getString(cursor.getColumnIndex(TABLE_SAT.COLUMN_SAT_TEST_NUM)),
                    cursor.getString(cursor.getColumnIndex(TABLE_SAT.COLUMN_SAT_CRIT_READ)),
                    cursor.getString(cursor.getColumnIndex(TABLE_SAT.COLUMN_SAT_MATH_AVG)),
                    cursor.getString(cursor.getColumnIndex(TABLE_SAT.COLUMN_SAT_WRITING_AVG)),
                    cursor.getString(cursor.getColumnIndex(TABLE_SCHOOLS.COLUMN_SCHOOL_NAME))
                )
                listSat.add(item)
            }
        }
        callbackSchoolSat.invoke(listSat[0])
    }
}






