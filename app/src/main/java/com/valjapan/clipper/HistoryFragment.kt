package com.valjapan.clipper

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

        getAllHistoryData(view)

        return view
    }

    private fun getAllHistoryData(view: View) {
        val database = getDatabase(view.context)
        val repository = HistoryRepository(database.historyDao())
        historyRecyclerViewAdapter = HistoryRecyclerViewAdapter(view)
        historyRecyclerView.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        Completable.fromAction {
            historyRecyclerView.adapter = historyRecyclerViewAdapter
            historyRecyclerViewAdapter.setHistoryList(repository.getHistoryList())
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}