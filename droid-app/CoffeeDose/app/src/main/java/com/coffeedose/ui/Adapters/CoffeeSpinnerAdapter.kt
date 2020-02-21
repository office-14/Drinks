package com.coffeedose.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.coffeedose.R
import com.coffeedose.domain.Coffee


class CoffeeSpinnerAdapter(private val context : Context) : BaseAdapter() {

    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    private var items: List<Coffee> = listOf()

    fun setItems(source: List<Coffee> ){
        items = source
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = _inflater.inflate(R.layout.view_coffe_frop_down_item, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        vh.rebindViewHolder(getItem(position))

        return view
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = items[position].id.toLong()

    override fun getCount() = items.size

    private class ItemRowHolder(row: View) {
        private val name: TextView = row.findViewById<TextView>(R.id.tv_coffee_name)

        fun rebindViewHolder(item: Coffee){
            name.text = item.name
        }
    }
}