package com.itsrdb.lcchallenger

data class Submissions(
    var acSubmissionNum    : ArrayList<InsideSubmission> = arrayListOf(),
    var totalSubmissionNum : ArrayList<InsideSubmission> = arrayListOf()
)
