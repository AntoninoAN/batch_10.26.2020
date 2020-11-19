package com.example.nycschoolsmvp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschoolsmvp.R
import com.example.nycschoolsmvp.model.ListDataType
import java.lang.Exception

class SchoolsAdapter(val dataset: ListDataType,
        val callback: (String)->Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private enum class SCHOOL_TYPE_VIEWS {
        SchoolType, SatType
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataset) {
            is ListDataType.SCHOOLTYPE -> SCHOOL_TYPE_VIEWS.SchoolType.ordinal
            is ListDataType.SATTYPE -> SCHOOL_TYPE_VIEWS.SatType.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return when (viewType) {
            SCHOOL_TYPE_VIEWS.SchoolType.ordinal -> {
                ListSchoolViewHolder(LayoutInflater.from(parent.context).inflate(
                        R.layout.item_layout_list_schools,
                        parent,
                        false
                ))
            }
            SCHOOL_TYPE_VIEWS.SatType.ordinal -> {
                ListSatViewHolder(LayoutInflater.from(parent.context).inflate(
                        R.layout.item_layout_list_sat,
                        parent,
                        false
                ))
            }
            else -> throw Exception("Undefined type")
        }
    }

    override fun getItemCount(): Int {
        return when (dataset) {
            is ListDataType.SCHOOLTYPE -> dataset.data.size
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,
                                  position: Int) {
        when (holder) {
            is ListSchoolViewHolder -> {
                holder.onBind(
                        (dataset as ListDataType.SCHOOLTYPE).data[position],
                        callback)
            }
            is ListSatViewHolder -> {
                holder.onBind(
                        (dataset as ListDataType.SATTYPE).data
                )
            }
        }
    }
}

