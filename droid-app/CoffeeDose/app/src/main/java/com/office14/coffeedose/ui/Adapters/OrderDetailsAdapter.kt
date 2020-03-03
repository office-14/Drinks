package com.office14.coffeedose.ui.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coffeedose.R
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
        return OrderDetailViewHolder(inflater.inflate(R.layout.view_order_details_item,parent,false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.rebindViewHolder(items[position])
    }


    class OrderDetailViewHolder (convertView : View) : RecyclerView.ViewHolder(convertView) ,ItemTouchViewHolder {
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }

        val headerTv = convertView.findViewById<TextView>(R.id.tv_drink_title)
        val addinsTv = convertView.findViewById<TextView>(R.id.tv_add_ins)
        val priceTv = convertView.findViewById<TextView>(R.id.tv_price)


        fun rebindViewHolder(item : OrderDetailFull){
            headerTv.text = "${item.drink.name} (${item.size.name},${item.size.volume}) x ${item.count}"
            addinsTv.text = if (item.addIns.size != 0) item.addIns.joinToString { it.name } else "Без добавок"
            priceTv.text = "${item.price} Р."
        }
    }

}

class SwipeListener(private val listener: (orderDetails : OrderDetailFull) -> Unit){
    fun onSwipe(item:OrderDetailFull) = listener.invoke(item)
}