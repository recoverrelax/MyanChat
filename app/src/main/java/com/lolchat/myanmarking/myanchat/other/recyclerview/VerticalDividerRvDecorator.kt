package com.lolchat.myanmarking.myanchat.other.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.dip


@Suppress("JoinDeclarationAndAssignment")
class VerticalDividerRvDecorator(context: Context): RecyclerView.ItemDecoration() {

    private val paint: Paint
    private val dip1: Int

    /**
     * Default divider will be used
     */
    init {
        paint = Paint()
        paint.color = Color.WHITE
        paint.isAntiAlias = true
        dip1 = context.dip(1)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams
            val bottom = child.bottom + params.bottomMargin

//            divider.setBounds(left, bottom, right, bottom)
            c.drawLine(left.toFloat(), (bottom - dip1).toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}