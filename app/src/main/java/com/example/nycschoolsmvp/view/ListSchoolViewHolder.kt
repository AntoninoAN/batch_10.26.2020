package com.example.nycschoolsmvp.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschoolsmvp.R
import com.example.nycschoolsmvp.model.NYCListSchoolsResponse

class ListSchoolViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val tvSchoolName: TextView = itemView.findViewById(R.id.tv_item_school_name)
    private val tvSchoolAdd: TextView = itemView.findViewById(R.id.tv_item_school_address)
    private val tvSchoolPhone: TextView = itemView.findViewById(R.id.tv_item_school_phone)

    fun onBind(dataItem: NYCListSchoolsResponse,
               onClickCallback: (String)-> Unit){
        tvSchoolName.text = dataItem.school_name
        tvSchoolAdd.text = dataItem.location
        tvSchoolPhone.text = dataItem.phone_number
        itemView.setOnClickListener { onClickCallback.invoke(dataItem.dbn) }
    }
}