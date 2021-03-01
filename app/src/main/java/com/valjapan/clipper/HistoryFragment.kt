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
    private lateinit var historyFragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        historyFragmentView = inflater.inflate(R.layout.fragment_history, container, false)

        historyRecyclerView = historyFragmentView.findViewById(R.id.history_recycler_view)
        historyRecyclerView.layoutManager = LinearLayoutManager(historyFragmentView.context)

        return historyFragmentView
    }

    private fun getAllHistoryData(view: View) {
        val database = getDatabase(view.context)
        val repository = HistoryRepository(database.historyDao())
        historyRecyclerViewAdapter = HistoryRecyclerViewAdapter(view)
        historyRecyclerView.layoutAnimation =
            AnimationUtils.loadLayoutAnimation(view.context, R.anim.layout_animation_fall_down)
        historyRecyclerView.adapter = historyRecyclerViewAdapter
        Completable.fromAction {
            historyRecyclerViewAdapter.setHistoryList(repository.getHistoryList())
        }.subscribeOn(Schedulers.io()).subscribe()
        historyRecyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        getAllHistoryData(historyFragmentView)
    }
}