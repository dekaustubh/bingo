package com.dekaustubh.bingo.rooms.details

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.databinding.ItemMatchBinding
import com.dekaustubh.bingo.main.listeners.OnMatchSelectedListener
import com.dekaustubh.bingo.main.listeners.OnStartNewMatchListener
import com.dekaustubh.bingo.match.Match
import com.dekaustubh.bingo.match.join.MatchFragment
import timber.log.Timber
import javax.inject.Inject


class RoomsDetailsAdapter @Inject constructor() : RecyclerView.Adapter<RoomsDetailsAdapter.MatchViewHolder>() {

    private val list = mutableListOf<Match>()
    private var onMatchSelectedListener: OnMatchSelectedListener? = null
    private var onStartNewMatchListener: OnStartNewMatchListener? = null

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
        holder.binding.matchName.text = list[position].status

        holder.itemView.setOnClickListener {
            with(holder.itemView.context) {
                startActivity(
                    Intent(this, MatchFragment::class.java)
                        .putExtra(MatchFragment.EXTRA_MATCH, list[position])
                )
            }
        }
    }

    fun setOnMatchSelectedListener(onMatchSelectedListener: OnMatchSelectedListener) {
        this.onMatchSelectedListener = onMatchSelectedListener
    }
    
    fun setOnStartNewMatchListener(onStartNewMatchListener: OnStartNewMatchListener) {
        this.onStartNewMatchListener = onStartNewMatchListener
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