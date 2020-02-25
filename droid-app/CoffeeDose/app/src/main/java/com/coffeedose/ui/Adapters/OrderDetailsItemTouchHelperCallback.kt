package com.coffeedose.ui.Adapters

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.coffeedose.R


class OrderDetailsItemTouchHelperCallback(context: Context, private val adapter : OrderDetailsAdapter, private val swipeListener: SwipeListener) : ItemTouchHelper.Callback() {

    //private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24dp)
    //private val intrinsicWidth = deleteIcon.intrinsicWidth
    //private val intrinsicHeight = deleteIcon.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }



    /*private val bgRect = RectF()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private  val iconBounds = Rect()*/

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder is ItemTouchViewHolder)
            makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START)
        else
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.onSwipe(adapter.items[viewHolder.adapterPosition])
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder){
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemTouchViewHolder) viewHolder.onItemCleared()
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        /*if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            val itemView = viewHolder.itemView
            drawBackground(canvas, itemView, dX)
            drawIcon(canvas, itemView, dX)
        }*/



        val itemView = viewHolder.itemView

        val deleteIcon = itemView.resources.getDrawable(R.drawable.ic_delete_black_24dp, itemView.context.theme)

        val intrinsicWidth = deleteIcon.intrinsicWidth
        val intrinsicHeight = deleteIcon.intrinsicHeight

        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }

   /* private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {
        val icon = itemView.resources.getDrawable(R.drawable.ic_delete_black_24dp, itemView.context.theme)
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)
        val margin = (itemView.bottom - itemView.top - iconSize)/2

        with(iconBounds){
            left = itemView.right + dX.toInt() + space
            top = itemView.top + margin
            right = itemView.right + dX.toInt() + iconSize + space
            bottom = itemView.bottom  - margin
        }
        icon.bounds = iconBounds
        icon.draw(canvas)
    }

    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        with (bgRect){
            left = itemView.left.toFloat() + dX
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }

        with(bgPaint){
            color = itemView.resources.getColor(R.color.design_default_color_background,itemView.context.theme)
        }

        canvas.drawRect(bgRect,bgPaint)
    }*/

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}

interface ItemTouchViewHolder {
    fun onItemSelected()
    fun onItemCleared()
}