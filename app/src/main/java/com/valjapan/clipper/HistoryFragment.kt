package com.valjapan.clipper

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valjapan.clipper.HistoryDatabase.Companion.getDatabase
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class HistoryFragment : Fragment() {
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyRecyclerViewAdapter: HistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecyclerView = view.findViewById(R.id.history_recycler_view)
        historyRecyclerView.layoutManager = LinearLayoutManager(view.context)

        getAllHistoryData(view, view.context)
        return view
    }

    private fun getAllHistoryData(view: View, context: Context) {
        val database = getDatabase(context)
        val repository = HistoryRepository(database.historyDao())
        historyRecyclerViewAdapter = HistoryRecyclerViewAdapter(view, context)
        historyRecyclerView.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        historyRecyclerView.adapter = historyRecyclerViewAdapter
        Completable.fromAction {
            historyRecyclerViewAdapter.setHistoryList(repository.getHistoryList())
        }.subscribeOn(Schedulers.io()).subscribe()
        historyRecyclerView.setHasFixedSize(true)

    }


    override fun onResume() {
        super.onResume()
        historyRecyclerView.adapter?.notifyDataSetChanged()
    }
}