package com.dekaustubh.bingo.rooms.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.match.Match
import com.dekaustubh.bingo.models.Room
import timber.log.Timber
import javax.inject.Inject


class RoomsDetailsAdapter @Inject constructor() : RecyclerView.Adapter<RoomsDetailsAdapter.MatchViewHolder>() {

    private val list = mutableListOf<Match>()

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.match_name)
        lateinit var roomNameTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_match, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.roomNameTextView.text = list[position].status
    }

    fun setMatches(rooms: List<Match>) {
        list.clear()
        rooms.forEach {
            Timber.d("RoomsDetailsAdapter#Match ==> ${it.id}, ${it.createdBy}")
        }
        list.addAll(rooms)
        notifyDataSetChanged()
    }
}