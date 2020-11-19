package com.example.nycschoolsmvp.model

sealed class ListDataType{
    data class SCHOOLTYPE(val data: List<NYCListSchoolsResponse>)
        : ListDataType()

    data class SATTYPE(val data: NYCListSatResponse)
        : ListDataType()
}