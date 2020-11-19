package com.example.nycschoolsmvp.model

import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nycschoolsmvp.R
import com.example.nycschoolsmvp.view.SchoolsApplication
import org.json.JSONArray

class SchoolsNetwork {
    private var enqueue = Volley.newRequestQueue(
        SchoolsApplication.schoolsContext)

    fun initVolley(callBackError: (String)->Unit,
        callBackSuccess: (JSONArray)-> Unit){
        val jsonArrayListSchools = JsonArrayRequest(
            SchoolsApplication
                .schoolsContext.getString(R.string.url_schools),
            Response.Listener {
                callBackSuccess.invoke(it)
            },
            Response.ErrorListener {
                callBackError.invoke(it.message ?: "Error Connection")
            }
        )
        val jsonArraySatSchools = JsonArrayRequest(
            SchoolsApplication
                .schoolsContext.getString(R.string.url_sat),
            Response.Listener {
                //todo insert in Database
            },
            Response.ErrorListener {
                callBackError.invoke(it.message ?: "Error Connection")
            }
        )
        enqueue.add(jsonArrayListSchools)
        enqueue.add(jsonArraySatSchools)
    }
}