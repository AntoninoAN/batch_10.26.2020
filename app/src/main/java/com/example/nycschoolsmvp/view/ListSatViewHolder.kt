package com.example.nycschoolsmvp.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschoolsmvp.R
import com.example.nycschoolsmvp.model.NYCListSatResponse

class ListSatViewHolder(satView: View): RecyclerView.ViewHolder(satView) {

    private val tvSatName: TextView = satView.findViewById(R.id.tv_item_sat_name)
    private val tvSatTestTakers: TextView = satView.findViewById(R.id.tv_item_sat_test_takers)
    private val tvSatCritical: TextView = satView.findViewById(R.id.tv_item_sat_critical_reading)
    private val tvSatMath: TextView = satView.findViewById(R.id.tv_item_sat_math_avg)
    private val tvSatWriting: TextView = satView.findViewById(R.id.tv_item_sat_writing_avg)

    fun onBind(dataItem: NYCListSatResponse){
        tvSatName.text = dataItem.school_name
        tvSatTestTakers.text = dataItem.num_of_sat_test_takers
        tvSatCritical.text = dataItem.sat_critical_reading_avg_score
        tvSatMath.text = dataItem.sat_math_avg_score
        tvSatWriting.text = dataItem.sat_writing_avg_score
    }
}