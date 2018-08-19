package com.sid.demoapp.kotllin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by rgaina on 10/03/2018.
 */
class KotlinRecycleViewAdapter(private val dataSet: ArrayList<String>) : RecyclerView.Adapter<KotlinRecycleViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layout = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount(): Int = dataSet.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}