package com.office14.coffeedose.ui.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coffeedose.R
import com.coffeedose.databinding.ViewOrderAwaitingHeaderBinding
import com.coffeedose.databinding.ViewOrderAwaitingListItemBinding
import com.office14.coffeedose.domain.OrderDetailFull
import com.office14.coffeedose.domain.OrderInfo
import com.office14.coffeedose.domain.OrderStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class OrderAwaitingAdapter() : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(data : OrderInfo, orderStatus:String? = null) {
        adapterScope.launch {

            val items = (if (orderStatus == null) listOf(DataItem.Header(data)) else listOf(DataItem.Header(data.copy(statusCode = orderStatus)))) + data.drinks.map { DataItem.OrderDetailsItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.OrderDetailsItem-> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> OrderAwaitingHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> OrderAwaitingViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    //override fun getItemCount() = items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OrderAwaitingViewHolder -> {
                val item = getItem(position) as DataItem.OrderDetailsItem
                holder.bind(item.item)
            }
            is OrderAwaitingHeaderViewHolder -> {
                val item = getItem(position) as DataItem.Header
                holder.bind(item.item)
            }
        }
    }


    class OrderAwaitingViewHolder constructor (private val binding: ViewOrderAwaitingListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: OrderDetailFull) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OrderAwaitingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewOrderAwaitingListItemBinding.inflate(layoutInflater, parent, false)

                return OrderAwaitingViewHolder(binding)
            }
        }
    }

    class OrderAwaitingHeaderViewHolder private constructor(private val binding : ViewOrderAwaitingHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderInfo) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OrderAwaitingHeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewOrderAwaitingHeaderBinding.inflate(layoutInflater, parent, false)

                return OrderAwaitingHeaderViewHolder(binding)
            }
        }
    }

}


class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    data class OrderDetailsItem(val item: OrderDetailFull): DataItem() {
        override val id = item.id
    }

    data class Header(val item : OrderInfo): DataItem() {
        override val id = Int.MIN_VALUE
    }

    abstract val id: Int
}

