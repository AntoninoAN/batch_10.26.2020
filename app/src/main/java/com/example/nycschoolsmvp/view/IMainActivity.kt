package com.example.nycschoolsmvp.view

import com.example.nycschoolsmvp.model.ListDataType

interface IMainActivity {
    fun requestData()
    fun displayData(dataSet: ListDataType.SCHOOLTYPE)
    fun bindPresenter()
    fun storeLastNetworkCall(date: String)
    fun readLastNetworkCall(): String
}