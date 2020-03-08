package com.office14.coffeedose.ui.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coffeedose.databinding.ViewDrinkListItemBinding
import com.office14.coffeedose.domain.Coffee
import com.office14.coffeedose.ui.CoffeeItemClickListener

class DrinksListAdapter (private val clickListener: CoffeeItemClickListener) : ListAdapter<Coffee,
        DrinksListAdapter.DrinksViewHolder>(CoffeeItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        return DrinksViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position))
    }

    class DrinksViewHolder private constructor(val binding: ViewDrinkListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(ciClickListener: CoffeeItemClickListener, item: Coffee) {
            binding.item = item
            binding.clickListener = ciClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DrinksViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewDrinkListItemBinding.inflate(layoutInflater, parent, false)

                return DrinksViewHolder(binding)
            }
        }
    }
}

class CoffeeItemDiffCallback : DiffUtil.ItemCallback<Coffee>() {
    override fun areItemsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coffee, newItem: Coffee): Boolean {
        return oldItem == newItem
    }
}


