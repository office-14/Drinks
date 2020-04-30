package com.office14.coffeedose.ui.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coffeedose.R
import com.coffeedose.databinding.ViewOrderDetailsItemBinding
import com.office14.coffeedose.domain.OrderDetailFull

class OrderDetailsAdapter() : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailViewHolder>(){

    var items : List<OrderDetailFull> = listOf()

    fun setSource(source : List<OrderDetailFull>){

        val diffCallback = object:  DiffUtil.Callback(){
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == source[newPos].id
            override fun getOldListSize(): Int  = items.size
            override fun getNewListSize(): Int  = source.size
            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].hashCode() == source[newPos].hashCode()
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = source
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OrderDetailViewHolder.from(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class OrderDetailViewHolder private constructor (val binding : ViewOrderDetailsItemBinding) : RecyclerView.ViewHolder(binding.root) , ItemTouchViewHolder {
        override fun onItemSelected() {
            itemView.background = ContextCompat.getDrawable(itemView.context,R.drawable.bg_swipe_to_delete_selected)
        }

        override fun onItemCleared() {
            itemView.background = ContextCompat.getDrawable(itemView.context,R.drawable.bg_round_border_with_solid)
        }

        fun bind(item: OrderDetailFull) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) :  OrderDetailViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ViewOrderDetailsItemBinding.inflate(inflater,parent,false)
                return OrderDetailViewHolder (binding)
            }

        }
    }

}

class SwipeListener(private val listener: (orderDetails : OrderDetailFull) -> Unit){
    fun onSwipe(item:OrderDetailFull) = listener.invoke(item)
}