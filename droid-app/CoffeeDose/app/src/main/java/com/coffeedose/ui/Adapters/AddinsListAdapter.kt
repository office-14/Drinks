package com.coffeedose.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.coffeedose.R
import com.coffeedose.domain.Addin
import kotlinx.android.synthetic.main.fragment_drinks.*

class AddinsListAdapter(context : Context,val listener : AddinCheckListener) : BaseAdapter() {

    private val _inflater: LayoutInflater = LayoutInflater.from(context)

    private var items : List<Addin> = listOf()

    fun setItems(source: List<Addin>){
        items = source
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: AddinViewHolder
        if (convertView == null) {
            view = _inflater.inflate(R.layout.view_addin_list_item, parent, false)
            vh = AddinViewHolder(view,listener)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as AddinViewHolder
        }

        vh.rebindViewHolder(getItem(position))

        return view
    }

    override fun getItem(position: Int) = items[position]

    override fun getItemId(position: Int) = items[position].id.toLong()

    override fun getCount() = items.size

    private class AddinViewHolder(view: View,val listener : AddinCheckListener){
        private val icon = view.findViewById<ImageView>(R.id.iv_addin_icon)
        private val name = view.findViewById<TextView>(R.id.tv_addin_name)
        private val description = view.findViewById<TextView>(R.id.tv_addin_desc)
        private val price = view.findViewById<TextView>(R.id.tv_addin_price)
        private val checkBox = view.findViewById<CheckBox>(R.id.cb_addin_select)

        fun rebindViewHolder(item : Addin){

            Glide.with(icon.context)
                .load(item.photoUrl)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(icon)

            name.text = item.name
            description.text = item.description
            price.text = "${item.price} Р."

            checkBox.setOnCheckedChangeListener {
                    buttonView, isChecked -> listener.onClick(item,isChecked)
            }

        }
    }
}

class AddinCheckListener(val clickListener: (addin : Addin, isChecked: Boolean) -> Unit) {
    fun onClick(addin : Addin, isChecked: Boolean) = clickListener(addin,isChecked)
}