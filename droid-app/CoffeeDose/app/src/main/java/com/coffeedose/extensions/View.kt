package com.coffeedose.extensions

import android.view.View

/*
    goneHidding = true -> View.GONE
    goneHidding = false -> View.INVISIBLE
 */
 fun View.setBooleanVisibility(isVisible : Boolean, goneHidding: Boolean = true) {
    if (isVisible)
        this.visibility = View.VISIBLE
    else
        this.visibility = if (goneHidding) View.GONE else View.INVISIBLE
}