package com.dekaustubh.bingo.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.databinding.ItemUserBinding
import javax.inject.Inject

class UserAdapter @Inject constructor() : RecyclerView.Adapter<UserAdapter.NumberViewHolder>() {

    private val items = mutableListOf<String>()

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemUserBinding = ItemUserBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_number, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.binding.name.text = items[position]
    }

    fun addUser(name: String) {
        items.add(name)
        notifyDataSetChanged()
    }

    fun removeUser(name: String) {
        items.remove(name)
    }
}