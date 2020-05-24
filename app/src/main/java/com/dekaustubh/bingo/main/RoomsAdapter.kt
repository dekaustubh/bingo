package com.dekaustubh.bingo.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.databinding.ItemRoomBinding
import com.dekaustubh.bingo.models.Room
import timber.log.Timber
import javax.inject.Inject


class RoomsAdapter @Inject constructor() : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    private val list = mutableListOf<Room>()
    private var onRoomSelectListener: OnRoomSelectListener? = null
    private var onStartNewMatchListener: OnStartNewMatchListener? = null

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemRoomBinding = ItemRoomBinding.bind(itemView)
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
        holder.binding.roomNameText.text = list[position].name

        holder.itemView.setOnClickListener {
            onRoomSelectListener?.onRoomSelected(list[position])
        }

        holder.binding.startMatch.setOnClickListener {
            with (holder.binding.startMatch.context) {
                onStartNewMatchListener?.onNewMatchStarted(list[position])
            }
        }
    }

    fun setOnRoomSelectedListener(onRoomSelectListener: OnRoomSelectListener) {
        this.onRoomSelectListener = onRoomSelectListener
    }

    fun setOnStartNewMatchListener(onStartNewMatchListener: OnStartNewMatchListener) {
        this.onStartNewMatchListener = onStartNewMatchListener
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