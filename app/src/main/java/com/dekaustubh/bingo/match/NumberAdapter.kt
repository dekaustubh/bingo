package com.dekaustubh.bingo.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dekaustubh.bingo.R
import com.dekaustubh.bingo.databinding.ItemNumberBinding
import javax.inject.Inject

class NumberAdapter @Inject constructor() : RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

    private val items = mutableListOf<Int>()

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemNumberBinding = ItemNumberBinding.bind(itemView)
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
        holder.binding.number.text = items[position].toString()
    }

    fun setNumbers(items: List<Int>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}