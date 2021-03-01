package com.valjapan.clipper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class HistoryRecyclerViewAdapter(
    private val view: View
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

        holder.historyDate.text = stringToDate(history.historyDate)
        holder.historyText.text = history.historyText

        val swipeDismissBehavior = SwipeDismissBehavior<View>()
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)

        val coordinatorParams = holder.cellView.layoutParams as CoordinatorLayout.LayoutParams

        coordinatorParams.behavior = swipeDismissBehavior

        swipeDismissBehavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(v: View) {
                Snackbar.make(view, "この項目を削除しました", Snackbar.LENGTH_SHORT)
                    .setAction("元に戻す") { _ ->
                        check = true
                    }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (check) {
                                resetCard(holder.cellView)
                            } else {
                                val database = HistoryDatabase.getDatabase(view.context)
                                val repository = HistoryRepository(database.historyDao())
                                (historyList as MutableList).remove(history)
                                notifyItemRemoved(position)
                                Completable.fromAction {
                                    repository.deleteHistoryTask(
                                        view.context,
                                        history
                                    )
                                }.subscribeOn(Schedulers.io()).subscribe()
                            }
                            check = false
                        }
                    }).show()
            }

            override fun onDragStateChanged(state: Int) {
                onDragStateChanged(
                    state,
                    holder.cellView
                )
            }
        }

        holder.cellView.setOnClickListener() {
            val clipboardManager: ClipboardManager =
                view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Cripper", history?.historyText))
            Toast.makeText(view.context, "クリップボードに貼り付けました", Toast.LENGTH_LONG).show()
        }
    }

    fun setHistoryList(historyList: List<History>) {
        for (i in historyList) {
            this.historyList.add(i)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (historyList.isEmpty()) {
            return 0
        }
        return historyList.size
    }

    private fun onDragStateChanged(state: Int, cardContentLayout: MaterialCardView) {
        when (state) {
            SwipeDismissBehavior.STATE_DRAGGING, SwipeDismissBehavior.STATE_SETTLING -> cardContentLayout.isDragged =
                true
            SwipeDismissBehavior.STATE_IDLE -> cardContentLayout.isDragged = false
        }
    }

    private fun resetCard(cardContentLayout: MaterialCardView) {
        val params = cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, 0)
        cardContentLayout.alpha = 1.0f
        cardContentLayout.requestLayout()
    }

    private fun stringToDate(historyDate: String?): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val date = Date()
        if (historyDate != null) {
            date.time = historyDate.toLong()
        }
        return dateFormat.format(date)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cellView: MaterialCardView = view.findViewById(R.id.card_content_layout)
        var historyText: TextView = view.findViewById(R.id.history_text)
        var historyDate: TextView = view.findViewById(R.id.history_date)
    }
}
