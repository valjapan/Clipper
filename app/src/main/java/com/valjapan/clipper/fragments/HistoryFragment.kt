package com.valjapan.clipper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valjapan.clipper.R
import com.valjapan.clipper.viewmodels.HistoryViewModel
import com.valjapan.clipper.views.HistoryRecyclerViewAdapter

class HistoryFragment : Fragment() {
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyFragmentView: View
    private val viewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyFragmentView = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecyclerView = historyFragmentView.findViewById(R.id.history_recycler_view)
        historyRecyclerView.layoutManager = LinearLayoutManager(historyFragmentView.context)
        val adapter = HistoryRecyclerViewAdapter(historyFragmentView, viewModel, viewLifecycleOwner)
        historyRecyclerView.adapter = adapter

        val emptyStateTextView: TextView =
            historyFragmentView.findViewById(R.id.empty_state_text_view)
        viewModel.historyList.observe(viewLifecycleOwner, Observer {
            adapter.setHistoryList(it)
            val isEmpty = it.isEmpty()
            emptyStateTextView.visibility = if (isEmpty) View.VISIBLE else View.INVISIBLE
        })

        return historyFragmentView
    }
}