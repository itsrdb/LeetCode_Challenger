package com.itsrdb.lcchallenger

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.itsrdb.lcchallenger.databinding.FragmentSecondBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SecondFragment:Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSubmissions()
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
                    Log.d("1", subArray[1].count.toString())
                    Log.d("2", subArray[2].count.toString())
                    Log.d("3", subArray[3].count.toString())
                    animateTextView(0, subArray[1].count!!, binding.tvEasy)
                    animateTextView(0, subArray[2].count!!, binding.tvMedium)
                    animateTextView(0, subArray[3].count!!, binding.tvHard)
                    //binding.tvEasy.text = subArray[1].count.toString()
//                    binding.tvMedium.text = subArray[2].count.toString()
//                    binding.tvHard.text = subArray[3].count.toString()

//                    for(i in subArray.indices)
//                    {
//                        //for(j in subArray[i].indices)
//                        Log.d("success", subArray[i].difficulty.toString())
//                        Log.d("success", subArray[i].count.toString())
//                        Log.d("success", subArray[i].submissions.toString())
//                        //print(subArray[1])
//                    }
                    //Log.d("success", subs.data.acSubmissionNum)
                    //Log.d("Success", subs.toString())
                }
            }
        })
    }

    fun animateTextView(initialValue: Int, finalValue: Int, textview: TextView) {
        val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
        valueAnimator.duration = 750
        valueAnimator.addUpdateListener { valueAnimator ->
            textview.text = valueAnimator.animatedValue.toString()
        }
        valueAnimator.start()
    }

}