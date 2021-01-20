package com.valjapan.clipper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class HistoryRecyclerViewAdapter(
    private val view: View,
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    private var historyList: List<History?>? = null

    fun setHistoryList(historyList: List<History?>?) {
        this.historyList = historyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(view.context).inflate(R.layout.item_history, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList?.get(position)
        holder.historyDate.text = history?.historyDate
        holder.historyText.text = history?.historyText
    }

    override fun getItemCount(): Int {
        return historyList?.size ?: 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cellView: MaterialCardView = view.findViewById(R.id.card_content_layout)
        var historyText: TextView = view.findViewById(R.id.history_text)
        var historyDate: TextView = view.findViewById(R.id.history_date)
    }

}