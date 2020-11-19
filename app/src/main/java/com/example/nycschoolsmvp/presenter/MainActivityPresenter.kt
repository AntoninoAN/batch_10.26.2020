package com.example.nycschoolsmvp.presenter

import android.util.Log
import com.example.nycschoolsmvp.model.ListDataType
import com.example.nycschoolsmvp.model.NYCListSchoolsResponse
import com.example.nycschoolsmvp.model.SchoolsLocal
import com.example.nycschoolsmvp.model.SchoolsNetwork
import com.example.nycschoolsmvp.view.IMainActivity

private const val TAG = "MainActivityPresenter"

class MainActivityPresenter {

    private var view: IMainActivity? = null
    private lateinit var schoolsNetwork: SchoolsNetwork
    private lateinit var schoolsLocal: SchoolsLocal

    fun bindView(view: IMainActivity){
        this.view = view
    }

    fun requestData() {
        if (view?.readLastNetworkCall().equals("N/A")){
            schoolsNetwork.initVolley({
                //todo inform about errors on the Network Call
                Log.d(TAG, "requestData: network error $it")
            },{
                schoolsLocal.insertListSchools(it)
            })
        }else{
            schoolsLocal.readListSchools {
                updateViewData(it)
            }
        }
    }
    //check if a file exists
    //if doesnt do the network call
    // update the database from the response.

    //check if database version...

    // preferences file

    // a flag to pull either from one or the other.

    private fun updateViewData(dataSet: List<NYCListSchoolsResponse>){
       val schoolType: ListDataType.SCHOOLTYPE =
           ListDataType.SCHOOLTYPE(dataSet)
        view?.displayData(schoolType)
    }

    fun unBind(){
        view = null
    }
}