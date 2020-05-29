package com.dekaustubh.bingo.rooms.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.databinding.ItemMatchBinding
import com.dekaustubh.bingo.main.listeners.OnMatchSelectedListener
import com.dekaustubh.bingo.models.Match
import timber.log.Timber
import javax.inject.Inject


class RoomsDetailsAdapter @Inject constructor() : RecyclerView.Adapter<RoomsDetailsAdapter.MatchViewHolder>() {

    private val list = mutableListOf<Match>()
    private var onMatchSelectedListener: OnMatchSelectedListener? = null

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMatchBinding = ItemMatchBinding.bind(itemView)
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
        holder.binding.matchName.text = list[position].status.name.toLowerCase()

        holder.itemView.setOnClickListener {
            onMatchSelectedListener?.onMatchSelected(list[position])
        }
    }

    fun setOnMatchSelectedListener(onMatchSelectedListener: OnMatchSelectedListener) {
        this.onMatchSelectedListener = onMatchSelectedListener
    }

    fun addMatch(match: Match) {
        list.add(match)
        notifyDataSetChanged()
    }

    fun setMatches(matches: List<Match>) {
        list.clear()
        matches.forEach {
            Timber.d("RoomsDetailsAdapter#Match ==> ${it.id}, ${it.createdBy}")
        }
        list.addAll(matches)
        notifyDataSetChanged()
    }
}