package com.coffeedose.extensions

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.coffeedose.domain.Coffee


/**
 * set spinner entries
 */
fun Spinner.setSpinnerEntries(entries: List<Coffee>?) {
    if (entries != null) {
        val arrayAdapter =
            ArrayAdapter(context, R.layout.simple_spinner_item, entries.map { it.name })
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
    }
}

/**
 * set spinner onItemSelectedListener listener
 */
fun Spinner.setSpinnerItemSelectedListener(listener: ItemSelectedListener?) {
    onItemSelectedListener = if (listener == null) {
        null
    } else {
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                /*if (tag != position) {
                    listener.onItemSelected(parent.getItemAtPosition(position))
                }*/
                listener.onItemSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}

/**
 * set spinner value
 */
fun Spinner.setSpinnerValue(value: Any?) {
    if (adapter != null ) {
        val position = (adapter as ArrayAdapter<Any>).getPosition(value)
        setSelection(position, false)
        tag = position
    }
}

/**
 * get spinner value
 */
fun Spinner.getSpinnerValue(): Any? {
    return selectedItem
}

interface ItemSelectedListener {
    fun onItemSelected(index: Int)
}