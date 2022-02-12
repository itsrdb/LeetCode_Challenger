package com.itsrdb.lcchallenger

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentSubmissionsAdapter(val context: Context, val recentArray: ArrayList<RecentSubmissions>)
    : RecyclerView.Adapter<RecentSubmissionsAdapter.SubmissionsViewHolder>(){

    private val limit = 3

    class SubmissionsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val qName = itemView.findViewById<TextView>(R.id.question_name)
        val qVerdict = itemView.findViewById<TextView>(R.id.question_verdict)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.submission_item_list, parent, false)
        return SubmissionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubmissionsViewHolder, position: Int) {
        //Data binding
        val subArray = recentArray[position]
        holder.qName.text = subArray.title
        holder.qVerdict.text = "WA"
        holder.qVerdict.setTextColor(Color.parseColor("#ff0a0a"))
        if(subArray.statusDisplay == "Accepted"){
            holder.qVerdict.text = "AC"
            holder.qVerdict.setTextColor(Color.parseColor("#23ad43"))//green
        }else if(subArray.statusDisplay == "Time Limit Exceeded"){
            holder.qVerdict.text = "TLE"
        }else{
            holder.qVerdict.text = "WA"
        }
    }

    override fun getItemCount(): Int {
        if(recentArray.size > limit)
            return limit

        return recentArray.size
    }

}