package com.itsrdb.lcchallenger

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.itsrdb.lcchallenger.databinding.FragmentSecondBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext


class SecondFragment:Fragment(R.layout.fragment_second){

    //private lateinit var context: Context

    private lateinit var binding: FragmentSecondBinding
    private lateinit var pieChart: PieChart
    lateinit var adapter: RecentSubmissionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSubmissions()
        getRecentSubmissions()
    }

    fun getRecentSubmissions() {
        val subs = APIService.apiInstance.getRecentSubmissions("itsrdb")
        subs.enqueue(object : Callback<OuterRecentSubmissions>{
            override fun onFailure(call: Call<OuterRecentSubmissions>, t: Throwable) {
                Log.d("oopsie", "Error in Fetching data")
            }

            override fun onResponse(
                call: Call<OuterRecentSubmissions>,
                response: Response<OuterRecentSubmissions>)
            {
                val subs = response.body()
                val rv = binding.rvSubmissions

                //subs!!.data
                adapter = RecentSubmissionsAdapter(context!!, subs!!.data)
                rv.adapter = adapter
                rv.layoutManager = LinearLayoutManager(context!!)
//                if(subs!=null){
//                    Log.d("Success", subs.toString())
//                }

            }
        })
    }

    fun getSubmissions() {
        val subs = APIService.apiInstance.getSubmissions("itsrdb")
        subs.enqueue(object : Callback<OuterSubmissions> {
            override fun onFailure(call: Call<OuterSubmissions>, t: Throwable) {
                Log.d("oopsie", "Error in Fetching data")
            }

            override fun onResponse(
                call: Call<OuterSubmissions>,
                response: Response<OuterSubmissions>
            )
            {
                val subs = response.body()
                if(subs!=null){
                    val subArray : ArrayList<InsideSubmission> = subs.data!!.acSubmissionNum
                    val easy = subArray[1].count
                    val medium = subArray[2].count
                    val hard = subArray[3].count
                    Log.d("1", easy.toString())
                    Log.d("2", medium.toString())
                    Log.d("3", hard.toString())
                    animateTextView(0, subArray[1].count!!, binding.tvEasy)
                    animateTextView(0, subArray[2].count!!, binding.tvMedium)
                    animateTextView(0, subArray[3].count!!, binding.tvHard)

                    basicSettings(binding.pieChart)
                    setDataToPieChart(binding.pieChart, easy!!, medium!!, hard!!)
                }
            }
        })
    }

    private fun setDataToPieChart(pieChart: PieChart, easy: Int, medium: Int, hard: Int) {
        pieChart.setUsePercentValues(false)
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(easy.toFloat(), "Easy"))
        dataEntries.add(PieEntry(medium.toFloat(), "Medium"))
        dataEntries.add(PieEntry(hard.toFloat(), "Hard"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#23ad43"))
        colors.add(Color.parseColor("#fc9a44"))
        colors.add(Color.parseColor("#ff0a0a"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)
        val vf: ValueFormatter = object : ValueFormatter() {
            //value format here, here is the overridden method
            override fun getFormattedValue(value: Float): String {
                return "" + value.toInt()
            }
        }
        data.setValueFormatter(vf)

        // In Percentage
        //data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Successful Submissions"

        pieChart.invalidate()

    }

    private fun basicSettings(pieChart : PieChart){
        pieChart.setUsePercentValues(true)
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
    }

    private fun animateTextView(initialValue: Int, finalValue: Int, textview: TextView) {
        val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
        valueAnimator.duration = 750
        valueAnimator.addUpdateListener { valueAnimator ->
            textview.text = valueAnimator.animatedValue.toString()
        }
        valueAnimator.start()
    }

}