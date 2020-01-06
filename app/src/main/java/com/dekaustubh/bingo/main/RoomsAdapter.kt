package com.dekaustubh.bingo.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.models.Room
import timber.log.Timber
import javax.inject.Inject


class RoomsAdapter @Inject constructor() : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    private val list = mutableListOf<Room>()

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.room_name)
        lateinit var roomNameTextView: TextView

        @BindView(R.id.start_match)
        lateinit var startMatchButton: Button

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_room, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.roomNameTextView.text = list[position].name
    }

    fun setRooms(rooms: List<Room>) {
        list.clear()
        rooms.forEach {
            Timber.d("RoomsAdapter#Room ==> ${it.name}, ${it.createdBy}")
        }
        list.addAll(rooms)
        notifyDataSetChanged()
    }
}