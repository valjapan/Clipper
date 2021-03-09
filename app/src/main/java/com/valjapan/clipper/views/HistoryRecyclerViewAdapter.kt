package com.valjapan.clipper.views

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.valjapan.clipper.R
import com.valjapan.clipper.datas.History
import com.valjapan.clipper.datas.HistoryDatabase
import com.valjapan.clipper.viewmodels.HistoryViewModel
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*


class HistoryRecyclerViewAdapter(
    private val view: View,
    private val viewModel: HistoryViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    private var historyList: MutableList<History> = mutableListOf()
    private var check: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(view.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]

        viewModel.run {
            fetchHistory(history.id).observe(viewLifecycleOwner, { history ->
                if (history != null) {
                    id = history.id
                }
            })
        }

        holder.historyText.text = history.historyText

        val now = LocalDateTime.now()
        val bookLocalDateTime =
            LocalDateTime.ofInstant(history.historyDate.toInstant(), ZoneId.systemDefault())
        val minute: Long = ChronoUnit.MINUTES.between(bookLocalDateTime, now)
        val hour: Long = ChronoUnit.HOURS.between(bookLocalDateTime, now)
        val timeText: String = when {
            hour in 1..23 -> {
                "${hour}時間前"
            }
            minute < 60 -> {
                "${minute}分前"
            }
            else -> {
                dateToString(history.historyDate)
            }
        }
        holder.historyDate.text = timeText


//        val swipeDismissBehavior = SwipeDismissBehavior<View>()
//        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
//
//        val coordinatorParams = holder.cellView.layoutParams as CoordinatorLayout.LayoutParams
//
//        coordinatorParams.behavior = swipeDismissBehavior

        holder.cellView.setOnLongClickListener {

            AlertDialog.Builder(view.context)
                .setTitle("削除")
                .setMessage("このクリップボードを削除しますか？")
                .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                    Completable.fromAction {
                        val database = HistoryDatabase.getDatabase(view.context)
                        val repository = (database.historyDao())
                        repository.deleteHistoryData(history)
                    }
                        .subscribeOn(Schedulers.io())
                        .doFinally {
                            Snackbar.make(view, "この項目を削除しました", Snackbar.LENGTH_SHORT).show()
                        }
                        .subscribe()
                })
                .setNegativeButton("Cancel", null)
                .show()
            true
        }

//        swipeDismissBehavior.listener = object : SwipeDismissBehavior.OnDismissListener {
//            override fun onDismiss(v: View) {
//
//                    .setAction("元に戻す") { _ ->
//                        check = true
//                    }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
//                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
//                            super.onDismissed(transientBottomBar, event)
//                            if (check) {
//                                resetCard(holder.cellView)
//                            } else {
//                                viewModel.delete()
//                                check = false
//                            }
//                        }
//                    }).show()
//            }
//
//            override fun onDragStateChanged(state: Int) {
//                onDragStateChanged(
//                    state,
//                    holder.cellView
//                )
//            }
//        }

        holder.cellView.setOnClickListener() {
            val clipboardManager: ClipboardManager =
                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Cripper", history.historyText))
            Toast.makeText(view.context, "クリップボードに貼り付けました", Toast.LENGTH_LONG).show()
        }
    }

    fun setHistoryList(historyList: List<History>) {
        this.historyList.clear()
        this.historyList.addAll(historyList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = historyList.size

//    private fun onDragStateChanged(state: Int, cardContentLayout: MaterialCardView) {
//        when (state) {
//            SwipeDismissBehavior.STATE_DRAGGING, SwipeDismissBehavior.STATE_SETTLING -> cardContentLayout.isDragged =
//                true
//            SwipeDismissBehavior.STATE_IDLE -> cardContentLayout.isDragged = false
//        }
//    }
//
//    private fun resetCard(cardContentLayout: MaterialCardView) {
//        val params = cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
//        params.setMargins(0, 0, 0, 0)
//        cardContentLayout.alpha = 1.0f
//        cardContentLayout.requestLayout()
//    }

    private fun dateToString(historyDate: Date): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        return dateFormat.format(historyDate)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cellView: MaterialCardView = view.findViewById(R.id.card_content_layout)
        var historyText: TextView = view.findViewById(R.id.history_text)
        var historyDate: TextView = view.findViewById(R.id.history_date)
    }
}
