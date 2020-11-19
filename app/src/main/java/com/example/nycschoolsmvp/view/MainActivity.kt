package com.example.nycschoolsmvp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschoolsmvp.R
import com.example.nycschoolsmvp.databinding.ActivityMainBinding
import com.example.nycschoolsmvp.model.ListDataType
import com.example.nycschoolsmvp.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var presenter: MainActivityPresenter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        bindPresenter()
        requestData()
    }

    override fun requestData() {
        presenter.requestData()
    }

    override fun displayData(dataSet: ListDataType.SCHOOLTYPE) {
        binding.recyclerViewSchools.layoutManager =
            LinearLayoutManager(this)
        binding.recyclerViewSchools.adapter = SchoolsAdapter(dataSet){

        }
    }

    override fun bindPresenter() {
        presenter = MainActivityPresenter()
        presenter.bindView(this)
    }

    override fun storeLastNetworkCall(date: String){
        val sp = getSharedPreferences("network_history",
            Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("LAST_NETWORK", date)
        editor.apply()
    }

    override fun readLastNetworkCall(): String{
        val sp = getSharedPreferences("network_history",
            Context.MODE_PRIVATE)

        return sp.getString("LAST_NETWORK", null) ?: "N/A"
    }
}


