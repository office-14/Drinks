package com.office14.coffeedose.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view: View = currentFocus ?: View(this)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen() : Boolean {
    val display = windowManager.defaultDisplay
    val point = Point()
    display.getSize(point) // point.x - full height

    val rootView = window.decorView.rootView

    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)

    return rect.height() < point.y
}
fun Activity.isKeyboardClosed() : Boolean {
    return !isKeyboardOpen()
}