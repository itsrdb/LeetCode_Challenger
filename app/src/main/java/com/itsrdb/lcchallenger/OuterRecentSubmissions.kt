package com.itsrdb.lcchallenger

data class OuterRecentSubmissions (
    val message : String? = null,
    val data    : ArrayList<RecentSubmissions> = arrayListOf()
)