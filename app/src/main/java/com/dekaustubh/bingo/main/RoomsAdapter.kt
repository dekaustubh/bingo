package com.dekaustubh.bingo.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.match.create.CreateMatchActivity
import com.dekaustubh.bingo.models.Room
import com.dekaustubh.bingo.rooms.details.RoomDetailsActivity
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

        holder.itemView.setOnClickListener {
            with(holder.itemView.context) {
                startActivity(
                    Intent(this, RoomDetailsActivity::class.java)
                        .putExtra(RoomDetailsActivity.EXTRA_ROOM, list[position])
                )
            }
        }

        holder.startMatchButton.setOnClickListener {
            with(holder.startMatchButton.context) {
                startActivity(
                    Intent(this, CreateMatchActivity::class.java)
                        .putExtra(CreateMatchActivity.EXTRA_ROOM, list[position])
                )
            }
        }
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